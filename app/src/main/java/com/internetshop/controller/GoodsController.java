package com.internetshop.controller;

import com.internetshop.model.*;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import com.tsystems.SmallGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class.getName());

    private final GoodsService goodsService;
    private final ClientService clientService;
    private final OrderService orderService;
    public final HttpSession session;


    public static final int amountOfGoodsOnPage = 9;
    public static final int amountOfRandomGoodsOnPage = 2;
    public static final int amountOfBestSellers = 3;

    @Autowired
    public GoodsController(GoodsService goodsService, ClientService clientService, OrderService orderService, HttpSession session) {
        this.goodsService = goodsService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.session = session;
    }

    @RequestMapping(value = "/goodsAll", method = RequestMethod.GET)
    @ResponseBody
    public List<SmallGoods> getAll() {
        return goodsService.getAllSmallGoods();

    }


    /**
     * Gets goods for selected page
     *
     * @return selected page of catalog
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String getAllGoods(@PathVariable(value = "page") int page, ModelMap modelMap) {
        logger.info("getAllGoods");
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods", goodsService.getAllGoods(amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage));
        modelMap.put("categoryFilter", false);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("catalogQuery", new CatalogQuery());
        return "goods";
    }

    /**
     * Gets goods by selected category
     *
     * @return catalog page
     */
    @RequestMapping(value = "/{category}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsByCategory(@PathVariable("category") String categoryName, @PathVariable(value = "page") int page, ModelMap modelMap) {
        logger.info("getAllGoodsByCategory");
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoodsByCategoryName(categoryName)));
        modelMap.put("listGoods", goodsService.getAllGoodsByCategoryName(amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage, categoryName));
        modelMap.put("categoryName", categoryName);
        modelMap.put("categoryFilter", true);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("catalogQuery", new CatalogQuery());
        return "goods";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String getAllGoodsBySearch(String searchStr, @RequestParam(value = "page") int page, ModelMap modelMap) {
        List<Goods> listGoods = goodsService.getAllGoodsBySearch(searchStr, amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage);
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("searchStr", searchStr);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoodsBySearch(searchStr)));
        modelMap.put("listGoods", listGoods);
        modelMap.put("searchFlag", true);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("catalogQuery", new CatalogQuery());
        return "goods";
    }

//    @RequestMapping(value = "/search/{searchStr}/page/{page}", method = RequestMethod.GET)
//    public String getAllGoodsBySearch(@PathVariable(value = "searchStr") String searchStr, @PathVariable(value = "page") Integer page, ModelMap modelMap) {
//
//        return "goods";
//    }

    @RequestMapping(value = "/filter/page/{page}", method = RequestMethod.POST)
    public String getAllGoodsByFilter(@PathVariable(value = "page") int page, @ModelAttribute(value = "catalogQuery") CatalogQuery catalogQuery, ModelMap modelMap) {
        if (catalogQuery.getRules().equals("")) {
            catalogQuery.setRules(null);
        }
        if (catalogQuery.getSort().equals("")) {
            catalogQuery.setSort(null);
        }
        session.setAttribute("catalogQuery", catalogQuery);

        return "redirect:/catalog/filter/page/" + page;
    }

    @RequestMapping(value = "/filter/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsByFilter(@PathVariable(value = "page") int page, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        CatalogQuery catalogQuery = (CatalogQuery) session.getAttribute("catalogQuery");
        if (catalogQuery == null) {
            return "404";
        }
        List<Goods> listGoods = goodsService.getAllGoodsByFilter(catalogQuery, amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoodsByFilter(catalogQuery)));
        modelMap.put("listGoods", listGoods);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("catalogQuery", catalogQuery);
        modelMap.put("isFilter", true);
        return "goods";
    }

    /**
     * Sends goods model to service for adding
     * handle validation errors
     *
     * @return page for adding
     */
    @RequestMapping(value = "/employee/add/goods", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute(value = "goods") @Valid Goods goods,
                           BindingResult bindingResultGoods,
                           ModelMap modelMap) {
        logger.info("addGoods");
        if (bindingResultGoods.hasErrors()) {
            logger.warn("Goods validation failed");
            modelMap.put("error1", "Invalid params!");
            modelMap.put("goods", goods);
            modelMap.put("category", new Category());
            modelMap.put("listCategory", goodsService.getAllCategories());
            return "employee_admin";
        }

        this.goodsService.addGoods(goods);
        modelMap.put("msgG", "You've been added new goods successfully.");

        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "employee_admin";
    }

    /**
     * Sends category model to service for adding
     * handle validation errors
     *
     * @return page for adding
     */
    @RequestMapping(value = "/employee/add/category", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute(value = "category") @Valid Category category,
                              BindingResult bindingResult, ModelMap modelMap) {
        logger.info("addCategory");
        if (bindingResult.hasErrors()) {
            logger.warn("Category validation failed");
            modelMap.put("error2", "Invalid params!");
            modelMap.put("goods", new Goods());
            modelMap.put("category", category);
            modelMap.put("listCategory", goodsService.getAllCategories());
            return "employee_admin";
        }

        this.goodsService.addCategory(category);
        modelMap.put("msgC", "You've been added new category successfully.");
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "employee_admin";
    }

    /**
     * Sends goods id which has to be deleted to service
     *
     * @return catalog page
     */

    @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.GET)
    public String deleteGoods(@PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("deleteGoods");
        goodsService.putDefaultAttributes(modelMap);
        if (orderService.isOrdersContainsGoods(id)) {
            modelMap.put("error", "Can't delete goods, which used in orders!");
            return "message";
        }
        try {
            this.goodsService.deleteGoodsById(id);
        } catch (IllegalArgumentException e) {
            modelMap.put("error", "No goods with such id");
            return "404";
        }
        return "redirect:/catalog/page/1";
    }

    /**
     * Sends goods model to service for editing
     * handle validation errors
     *
     * @return goods details page
     */
    @RequestMapping(value = "/employee/edit", method = RequestMethod.POST)
    public String editGoods(@ModelAttribute(value = "goods") @Valid Goods goods, BindingResult bindingResult,
                            ModelMap modelMap) {
        logger.info("editGoods {1}", goods);
        goodsService.putDefaultAttributes(modelMap);
        List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4, goods);  //If needs 3 goods set 4, because one of goods could be current goods
        modelMap.put("relatedGoodsList", relatedGoodsList);

        if (bindingResult.hasErrors()) {
            logger.warn("Goods validation failed");
            modelMap.put("error", "Invalid params!");
            modelMap.put("goods", goods);
            return "goods_detail";
        }
        try {
            this.goodsService.updateGoods(goods);
        } catch (IllegalThreadStateException e) {
            modelMap.put("error", "Lost connection with MQ Server");
            return "500";
        }
        modelMap.put("goods", goodsService.getGoodsById(goods.getId()));
        modelMap.put("msg", "You've been edited goods successfully.");

        return "goods_detail";
    }

    /**
     * Gets goods by selected id
     *
     * @return goods detail page
     */
    @RequestMapping(value = "/goods/{id}", method = RequestMethod.GET)
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap,
                               @RequestParam(value = "msg_img", required = false) String msg_img) {
        logger.info("getGoodsById {}", id);
        goodsService.putDefaultAttributes(modelMap);
        Goods goods;
        try {
            goods = goodsService.getGoodsById(id);
        } catch (NullPointerException e) {
            modelMap.put("error", "No goods with such id");
            return "404";
        }
        Client client = (Client) session.getAttribute("client");
        if ((client == null || !client.getRole().getName().equals("ROLE_EMPLOYEE")) && (goods.getVisible() == 0)) {
            return "404";
        }
        modelMap.put("goods", goods);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        modelMap.put("cartItem", cartItem);
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        if (cartList != null) {
            modelMap.put("isCartContainsGoods", goodsService.isCartContainsGoods(cartList, id));
        }
        modelMap.put("cartList", cartList);
        List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4, goods);
        modelMap.put("relatedGoodsList", relatedGoodsList);
        modelMap.put("listReviews", goodsService.getAllReviewsByGoodsId(id));
        modelMap.put("goodsPlace", goodsService.getPlaceOfGoods(id));
        if (msg_img != null){
            modelMap.put("msg_img","Image is too large. It must be less then 2MB");
        }
        modelMap.put("imgList",goodsService.getAllImagesByGoodsId(id));
        return "goods_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/goods/review/{id}", method = RequestMethod.POST)
    public Review createReview(Review review, @PathVariable(value = "id") int id, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        Client client = (Client) session.getAttribute("client");
        Goods goods = goodsService.getGoodsById(id);
        review.setClient(clientService.getClientById(client.getId()));
        review.setGoods(goods);
        if (!goodsService.isAvailableToLeaveReview(review)){
            return null;
        }
        goodsService.addReview(review);
        return review;
    }


    /**
     * adds goodsItem model to session
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/goods/cart/{id}/add/{quantity}", method = RequestMethod.GET)
    public String addToCart(@PathVariable(value = "id") int id, @PathVariable(value = "quantity") int quantity) {
        if (session.getAttribute("cartList") == null) {
            List<CartItem> cartList = new ArrayList<>();
            session.setAttribute("cartList", cartList);
        }

        List<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        if (goodsService.isCartContainsGoods(cartList, id)) {
            return "redirect:/catalog/goods/cart";
        }
        CartItem cartItem = new CartItem();
        Goods goods;
        try {
            goods = goodsService.getGoodsById(id);
        } catch (NullPointerException e) {
            return cartList.size() + "";
        }
        cartItem.setGoods(goods);
        cartItem.setQuantity(quantity);
        cartList.add(cartItem);


        return cartList.size() + "";

    }

    /**
     * Gets items from cart
     * handle errors
     *
     * @return cart page
     */
    @RequestMapping(value = "/goods/cart", method = RequestMethod.GET)
    public String getCartItems(ModelMap modelMap,
                               @RequestParam(value = "error", required = false) String error) {
        logger.info("getCartItems");
        goodsService.putDefaultAttributes(modelMap);
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        if (cartList != null) {
            modelMap.put("sum", orderService.getSumOfOrder(cartList));
            modelMap.put("cartList", cartList);
        }
        modelMap.put("client", session.getAttribute("client"));
        if (error != null) {
            modelMap.put("error", "To place an order please log in");
        }

        return "cart";
    }

    /**
     * Deletes selected item from cart
     *
     * @return cart page
     */
    @RequestMapping(value = "/goods/cart/delete/item/{index}", method = RequestMethod.GET)
    public String deleteCartItem(@PathVariable(value = "index") int index, ModelMap modelMap) {
        logger.info("deleteCartItem");
        goodsService.putDefaultAttributes(modelMap);
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        if (cartList == null || cartList.size() - 1 < index) {
            modelMap.put("error", "Invalid params!");
            return "404";
        }
        cartList.remove(index);
        return "redirect:/catalog/goods/cart";
    }


    /**
     * Gets selected category for editing form
     *
     * @return catalog page
     */
    @RequestMapping(value = "/employee/edit/category/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("editCategory");
        boolean editCatFlag = true;
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("editCatFlag", editCatFlag);
        modelMap.put("currentPage", 1);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods", goodsService.getAllGoods(0, amountOfGoodsOnPage));
        modelMap.put("categoryFilter", false);
        try {
            modelMap.put("category", goodsService.getCategoryById(id));
        } catch (NullPointerException e) {
            modelMap.put("error", "No such category");
            return "404";
        }
        return "goods";
    }

    /**
     * Edits selected category
     * handle validation errors
     *
     * @return catalog page
     */
    @RequestMapping(value = "/employee/edit/category", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute(value = "category") @Valid Category category, BindingResult bindingResult,
                               ModelMap modelMap) {
        logger.info("editCategory");
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("currentPage", 1);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("categoryFilter", false);
        modelMap.put("listGoods", goodsService.getAllGoods(0, amountOfGoodsOnPage));
        if (bindingResult.hasErrors()) {
            boolean editCatFlag = true;
            logger.warn("category validation error");
            modelMap.put("editCatFlag", editCatFlag);
            modelMap.put("category", category);
            return "goods";
        }
        goodsService.updateCategory(category);
        modelMap.put("msg", "You've been edited category successfully.");
        return "goods";
    }

    /**
     * Deletes selected category
     *
     * @return catalog page
     */
    @RequestMapping(value = "/employee/delete/category/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("deleteCategory");
        goodsService.putDefaultAttributes(modelMap);
        try {
            this.goodsService.deleteCategoryById(id);
        } catch (IllegalArgumentException e) {
            modelMap.put("error", "No such category");
            return "404";
        }
        return "redirect:/catalog/page/1";
    }

    @RequestMapping(value = "/employee/goods/upload/{id}", method = RequestMethod.POST)
    public String uploadGoodsImage(@PathVariable int id, @RequestParam CommonsMultipartFile[] fileUpload){
        if (fileUpload != null && fileUpload.length > 0) {
            for (CommonsMultipartFile aFile : fileUpload){
                if(aFile.getSize()>2000000){
                    return "redirect:/catalog/goods/"+id+"?msg_img";
                }
                if (aFile.getSize()>0) {
                    GoodsImage goodsImage = new GoodsImage();
                    goodsImage.setGoods(goodsService.getGoodsById(id));
                    goodsImage.setImg(aFile.getBytes());
                    goodsService.addGoodsImage(goodsImage);
                }
            }
        }
        return "redirect:/catalog/goods/"+id;
    }
    @RequestMapping(value = "/employee/goods/image/delete")
    public String deleteGoodsPhoto(@RequestParam (value = "id", required = false) int id,@RequestParam (value = "goodsId", required = false) int goodsId){
        goodsService.deleteImageById(id);
        return "redirect:/catalog/goods/"+goodsId;
    }

    @RequestMapping(value = "/goods/image", method = RequestMethod.GET)
    public void showImage(@RequestParam Map<String,String> requestParams, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        int goodsId = Integer.parseInt(requestParams.get("id"));
        int imgNumber = Integer.parseInt(requestParams.get("number"));
        List<GoodsImage> goodsImageList = goodsService.getAllImagesByGoodsId(goodsId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(goodsImageList.get(imgNumber).getImg());
        response.getOutputStream().close();
    }


    /**
     * Evaluates amount of pages depending on the number of goods
     *
     * @return amount of pages
     */
    public static long getAmountOfPages(long amountOfGoods) {
        if (amountOfGoods == 0)
            return 1;
        if (amountOfGoods % amountOfGoodsOnPage == 0) {
            return amountOfGoods / amountOfGoodsOnPage;
        } else {
            return amountOfGoods / amountOfGoodsOnPage + 1;
        }
    }

}
