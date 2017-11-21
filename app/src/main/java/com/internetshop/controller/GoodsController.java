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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods", goodsService.getAllGoods(amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage));
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("categoryFilter", false);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
        modelMap.put("catalogQuery",new CatalogQuery());
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
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoodsByCategoryName(categoryName)));
        modelMap.put("listGoods", goodsService.getAllGoodsByCategoryName(amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage, categoryName));
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("categoryName", categoryName);
        modelMap.put("categoryFilter", true);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
        return "goods";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String getAllGoodsBySearch(String searchStr) {
        return "redirect:/catalog/search/" + searchStr + "/page/" + 1;
    }

    @RequestMapping(value = "/search/{searchStr}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsBySearch(@PathVariable(value = "searchStr") String searchStr, @PathVariable(value = "page") Integer page, ModelMap modelMap) {
        List<Goods> listGoods = goodsService.getAllGoodsBySearch(searchStr, amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoodsBySearch(searchStr)));
        modelMap.put("listGoods", listGoods);
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("searchFlag", true);
        modelMap.put("categoryFilter", false);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
        return "goods";
    }
    @RequestMapping(value = "/filter/page/{page}", method = RequestMethod.POST)
    public String getAllGoodsByFilter(@PathVariable(value = "page") int page, ModelMap modelMap, @ModelAttribute(value = "catalogQuery") CatalogQuery catalogQuery){
        if (catalogQuery.getRules().equals("")){
            catalogQuery.setRules(null);
        }
        if (catalogQuery.getSort().equals("")) {
            catalogQuery.setSort(null);
        }
        List<Goods> listGoods = goodsService.getAllGoodsByFilter(catalogQuery, amountOfGoodsOnPage * (page - 1), amountOfGoodsOnPage);
        modelMap.put("currentPage", page);
        modelMap.put("amountOfPages", getAmountOfPages(listGoods.size())); //todo все товары?
        modelMap.put("listGoods",listGoods);
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("catalogQuery",catalogQuery);
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
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
    public String deleteGoods(@PathVariable(value = "id") int id) {
        logger.info("deleteGoods");
        this.goodsService.deleteGoodsById(id);
        return "redirect:/catalog";
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
        logger.info("editGoods");

        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
        modelMap.put("listCategory", goodsService.getAllCategories());
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
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("getGoodsById");
        Goods goods = goodsService.getGoodsById(id);
        modelMap.put("goods", goods);
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        modelMap.put("cartItem", cartItem);
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
        List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4, goods);
        modelMap.put("relatedGoodsList", relatedGoodsList);
        return "goods_detail";
    }

    /**
     * adds goodsItem model to session
     *
     * @return
     */
    @RequestMapping(value = "/goods/cart/{id}/add", method = RequestMethod.POST)
    public String addToCart(@PathVariable(value = "id") int id, CartItem cartItem) {
        logger.info("getGoodsById");
        if (session.getAttribute("cartList") == null) {
            List<CartItem> cartList = new ArrayList<>();
            session.setAttribute("cartList", cartList);
        }
        List<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        cartItem.setGoods(goodsService.getGoodsById(id));
        cartList.add(cartItem);
        session.setAttribute("cartList", cartList);
        return "redirect:/catalog/goods/" + id;
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
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        if (cartList != null) {
            modelMap.put("sum", OrderController.getSumOfOrder(cartList));
            modelMap.put("cartList", cartList);
        }
        modelMap.put("client", session.getAttribute("client"));
        if (error != null) {
            modelMap.put("error", "To place an order please log in");
        }
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));

        return "cart";
    }

    /**
     * Deletes selected item from cart
     *
     * @return cart page
     */
    @RequestMapping(value = "/goods/cart/delete/item/{index}", method = RequestMethod.GET)
    public String deleteCartItem(@PathVariable(value = "index") int index) {
        logger.info("deleteCartItem");
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
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
        modelMap.put("editCatFlag", editCatFlag);
        modelMap.put("currentPage", 1);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods", goodsService.getAllGoods(0, amountOfGoodsOnPage));
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("categoryFilter", false);
        modelMap.put("category", goodsService.getCategoryById(id));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
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
        modelMap.put("currentPage", 1);
        modelMap.put("amountOfPages", getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("categoryFilter", false);
        modelMap.put("listGoods", goodsService.getAllGoods(0, amountOfGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("randomGoods", goodsService.getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfBestSellers));
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
    public String deleteCategory(@PathVariable(value = "id") int id) {
        logger.info("deleteCategory");
        this.goodsService.deleteCategoryById(id);
        return "redirect:/catalog";
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
