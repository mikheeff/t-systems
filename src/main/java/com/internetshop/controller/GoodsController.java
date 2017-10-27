package com.internetshop.controller;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.*;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class.getName());

    private final GoodsService goodsService;
    private final ClientService clientService;
    private final OrderService orderService;

    public final HttpSession session;


    private static final int amountOfGoodsOnPage = 9;
    private static final int amountOfRandomGoodsOnPage = 2;
    private static final int amountOfBestSellers = 3;

    @Autowired
    public GoodsController(GoodsService goodsService, ClientService clientService, OrderService orderService, HttpSession session) {
        this.goodsService = goodsService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.session = session;
    }

    /**
     * Gets goods for first page of goods catalog
     * @return catalog page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllGoods(ModelMap modelMap) {
        logger.info("getAllGoods");
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("cartList",session.getAttribute("cartList"));
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String getAllGoodsBySearch(String searchStr){
        return "redirect:/catalog/search/"+searchStr+"/page/"+1;
    }

    @RequestMapping(value = "/search/{searchStr}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsBySearch(@PathVariable(value = "searchStr")String searchStr,@PathVariable(value = "page") Integer page, ModelMap modelMap){
        modelMap.put("searchStr",searchStr);
        modelMap.put("currentPage",page);
        List<Goods> listGoods = goodsService.getAllGoodsBySearch(searchStr, amountOfGoodsOnPage*(page-1),amountOfGoodsOnPage);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoodsBySearch(searchStr)));
        modelMap.put("listGoods",listGoods);
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("searchFlag",true);
        modelMap.put("categoryFilter",false);
        modelMap.put("cartList",session.getAttribute("cartList"));
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    /**
     * Gets goods for selected page
     * @return selected page of catalog
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String getAllGoods(@PathVariable(value = "page") int page, ModelMap modelMap) {
        logger.info("getAllGoods");
        modelMap.put("currentPage",page);
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods", goodsService.getAllGoods(amountOfGoodsOnPage*(page-1),amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("cartList",session.getAttribute("cartList"));
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    /**
     * Gets goods by selected category
     * @return catalog page
     */
    @RequestMapping(value ="/{category}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsByCategory(@PathVariable("category") String categoryName,@PathVariable(value = "page") int page, ModelMap modelMap) {
        logger.info("getAllGoodsByCategory");
        modelMap.put("currentPage",page);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoodsByCategoryName(categoryName)));
        modelMap.put("listGoods", goodsService.getAllGoodsByCategoryName(amountOfGoodsOnPage*(page-1),amountOfGoodsOnPage,categoryName));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryName",categoryName);
        modelMap.put("categoryFilter",true);
        modelMap.put("cartList",session.getAttribute("cartList"));
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    /**
     * Puts empty objects of goods and category model
     * to form for entering information
     * @return page for adding
     */
    @RequestMapping(value ="/employee/add", method = RequestMethod.GET)
    public String addGoods(ModelMap modelMap) {
        logger.info("addGoods");
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        modelMap.put("listCategory",goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search",searchStr);
        return "add_page";
    }

    /**
     * Sends goods model to service for adding
     * handle validation errors
     * @return page for adding
     */
    @RequestMapping(value ="/employee/add/goods", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "goods") @Valid Goods goods,
                           BindingResult bindingResultGoods,
                           ModelMap modelMap) {
        logger.info("addGoods");
        if(bindingResultGoods.hasErrors()) {
            logger.warn("Goods validation failed");
            modelMap.put("error1", "Invalid params!");
            modelMap.put("goods",goods);
            modelMap.put("category",new Category());
            modelMap.put("listCategory",goodsService.getAllCategories());
            String searchStr = "";
            modelMap.put("search",searchStr);
            return "add_page";
        }

        this.goodsService.addGoods(goods);
        modelMap.put("msgG", "You've been added new goods successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        String searchStr = "";
        modelMap.put("search",searchStr);
        return "add_page";
    }

    /**
     * Sends category model to service for adding
     * handle validation errors
     * @return page for adding
     */
    @RequestMapping(value = "/employee/add/category",method = RequestMethod.POST)
    public String addCategory( @ModelAttribute (value = "category")  @Valid Category category,
                               BindingResult bindingResult,ModelMap modelMap){
        logger.info("addCategory");
        if(bindingResult.hasErrors()) {
            logger.warn("Category validation failed");
            modelMap.put("error2", "Invalid params!");
            modelMap.put("goods",new Goods());
            modelMap.put("category",category);
            modelMap.put("listCategory",goodsService.getAllCategories());
            String searchStr = "";
            modelMap.put("search",searchStr);
            return "add_page";
        }

        this.goodsService.addCategory(category);
        modelMap.put("msgC", "You've been added new category successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        String searchStr = "";
        modelMap.put("search",searchStr);
        return "add_page";
    }

    /**
     * Sends goods id which has to be deleted to service
     * @return catalog page
     */

    @RequestMapping(value ="/employee/delete/{id}", method = RequestMethod.GET)
    public String deleteGoods(@PathVariable(value = "id") int id) {
        logger.info("deleteGoods");
        this.goodsService.deleteGoodsById(id);
        return "redirect:/catalog";
    }

    /**
     * Sends goods model to service for editing
     * handle validation errors
     * @return goods details page
     */
    @RequestMapping(value ="/employee/edit", method = RequestMethod.POST)
    public String editGoods(@ModelAttribute(value = "goods")  @Valid Goods goods, BindingResult bindingResult,
                            ModelMap modelMap) {
        logger.info("editGoods");
        if(bindingResult.hasErrors()) {
            logger.warn("Goods validation failed");
            modelMap.put("error", "Invalid params!");
            modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
            modelMap.put("goods",goods);
            modelMap.put("listCategory",goodsService.getAllCategories());
            String searchStr = "";
            modelMap.put("search",searchStr);
            modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
            List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4,goods);
            modelMap.put("relatedGoodsList",relatedGoodsList);
            return "goods_detail";
        }
        this.goodsService.updateGoods(goods);
        modelMap.put("goods", goodsService.getGoodsById(goods.getId()));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("msg", "You've been edited goods successfully.");
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4,goods);  //If needs 3 goods set 4, because one of goods could be current goods
        modelMap.put("relatedGoodsList",relatedGoodsList);
        return "goods_detail";
    }

    /**
     * Gets goods by selected id
     * @return goods detail page
     */
    @RequestMapping(value ="/goods/{id}", method = RequestMethod.GET)
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("getGoodsById");
        Goods goods = goodsService.getGoodsById(id);
        modelMap.put("goods", goods);
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        modelMap.put("cartItem", cartItem);
        modelMap.put("cartList",session.getAttribute("cartList"));
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        List<Goods> relatedGoodsList = goodsService.getRelatedGoods(4,goods);
        modelMap.put("relatedGoodsList",relatedGoodsList);
        return "goods_detail";
    }

    /**
     * adds goodsItem model to session
     * @return
     */
    @RequestMapping(value = "/goods/cart/{id}/add", method = RequestMethod.POST)
    public String addToCart(@PathVariable(value = "id") int id, CartItem cartItem){
        logger.info("getGoodsById");
        if (session.getAttribute("cartList")==null){
            List<CartItem> cartList = new ArrayList<>();
            session.setAttribute("cartList",cartList);
        }
        List<CartItem> cartList = (ArrayList<CartItem>)session.getAttribute("cartList");
        cartItem.setGoods(goodsService.getGoodsById(id));
        cartList.add(cartItem);
        session.setAttribute("cartList",cartList);
        return "redirect:/catalog/goods/"+id;
    }

    /**
     * Gets items from cart
     * handle errors
     * @return cart page
     */
    @RequestMapping(value = "/goods/cart",method = RequestMethod.GET)
    public String getCartItems(ModelMap modelMap,
                               @RequestParam(value = "error", required = false) String error){
        logger.info("getCartItems");
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        ArrayList<CartItem> cartList = (ArrayList<CartItem>)session.getAttribute("cartList");
        if (cartList!=null) {
            modelMap.put("sum", getSumOfOrder(cartList));
            modelMap.put("cartList", cartList);
        }
        modelMap.put("client",session.getAttribute("client"));
        if (error != null) {
            modelMap.put("error", "To place an order please log in");
        }
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));

        return "cart";
    }

    /**
     * Deletes selected item from cart
     * @return cart page
     */
    @RequestMapping(value = "/goods/cart/delete/item/{index}", method = RequestMethod.GET)
    public String deleteCartItem(@PathVariable(value = "index") int index){
        logger.info("deleteCartItem");
        ArrayList<CartItem> cartList = (ArrayList<CartItem>)session.getAttribute("cartList");
        cartList.remove(index);
        return "redirect:/catalog/goods/cart";
    }

    /**
     * Puts order details to model map
     * @return confirm order page
     */
    @RequestMapping(value = "/profile/goods/cart/continue",method = RequestMethod.GET)
    public String getOrder(ModelMap modelMap){
        logger.info("getOrder");
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("cartList",session.getAttribute("cartList"));
        modelMap.put("sum",getSumOfOrder((ArrayList<CartItem>)session.getAttribute("cartList")));
        modelMap.put("client",session.getAttribute("client"));
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        modelMap.put("date",formatForDateNow.format(date));
        modelMap.put("order", new Order());
        modelMap.put("listDeliveryMethod",orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType",orderService.getAllPaymentTypes());
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "confirm_order";
    }

    /**
     * Puts order details to order model and send it to service
     * for adding
     * @return order success page
     */

    @RequestMapping(value = "/profile/goods/order/confirm",method = RequestMethod.POST)
    public String addOrder(ModelMap modelMap,Order order){
        logger.info("addOrder");
        Set<CartItem> cartItemSet = new HashSet<>((ArrayList<CartItem>)session.getAttribute("cartList"));
        order.setCartItems(cartItemSet);
        Client client = (Client)session.getAttribute("client");
        order.setClient(client);
        int orderId = orderService.addOrder(order);
        modelMap.put("orderId",orderId);
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "order_success";
    }

    /**
     * Gets information for selected object
     * @return order details page
     */
    @RequestMapping(value = "/profile/employee/details/order/{id}", method = RequestMethod.GET)
    public String getOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap,
                                  @RequestParam(value = "msg", required = false) String msg){
        logger.info("getOrderDetails");
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("orderItemsList",orderService.getAllCartItemsFromOrderByOrderId(id));
        modelMap.put("listDeliveryMethod",orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType",orderService.getAllPaymentTypes());
        modelMap.put("listStatus",orderService.getAllStatuses());
        modelMap.put("sum",getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(id)));
        modelMap.put("order", orderService.getOrderById(id));
        String searchStr = "";
        modelMap.put("search",searchStr);
        if (msg!=null){
            modelMap.put("msg","Order has been successfully edited");
        }
        return "order_details";
    }

    /**
     * Edits order payment type, status ad delivery method
     * @return order details page
     */
    @RequestMapping(value = "/profile/employee/details/order/edit/{id}", method = RequestMethod.POST)
    public String updateOrderStatus(ModelMap modelMap, Order order,
                                    @PathVariable(value = "id") int id) {
        logger.info("updateOrderStatus");
        order.setId(id);
        orderService.updateOrderStatus(order);
        return "redirect:/catalog/profile/employee/details/order/"+order.getId()+"?msg";
    }


    /**
     * Gets selected category for editing form
     * @return catalog page
     */
    @RequestMapping(value = "/employee/edit/category/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable(value = "id") int id, ModelMap modelMap){
        logger.info("editCategory");
        boolean editCatFlag = true;
        modelMap.put("editCatFlag",editCatFlag);
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("category",goodsService.getCategoryById(id));
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    /**
     * Edits selected category
     * handle validation errors
     * @return catalog page
     */
    @RequestMapping(value ="/employee/edit/category", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute(value = "category")  @Valid Category category, BindingResult bindingResult,
                            ModelMap modelMap) {
        logger.info("editCategory");

        if(bindingResult.hasErrors()) {
            boolean editCatFlag = true;
            logger.warn("category validation error");
            modelMap.put("editCatFlag",editCatFlag);
            modelMap.put("category",category);
            modelMap.put("currentPage",1);
            modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoods()));
            modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
            modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
            modelMap.put("listCategory",goodsService.getAllCategories());
            modelMap.put("categoryFilter",false);
            String searchStr = "";
            modelMap.put("search",searchStr);
            modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
            return "goods";
        }


        goodsService.updateCategory(category);

        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages(goodsService.getAmountOfGoods()));
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods(amountOfRandomGoodsOnPage));
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("msg", "You've been edited category successfully.");
        String searchStr = "";
        modelMap.put("search",searchStr);
        modelMap.put("bestSellersList",getBestSellers(amountOfBestSellers));
        return "goods";
    }

    /**
     * Deletes selected category
     * @return catalog page
     */
    @RequestMapping(value ="/employee/delete/category/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable(value = "id") int id) {
        logger.info("deleteCategory");
        this.goodsService.deleteCategoryById(id);
        return "redirect:/catalog";
    }



    /**
     * Gets selected amount of different random goods
     * @return goods list
     */

    public List<Goods> getRandomGoods(int amountOfRandomGoodsOnPage){
        logger.info("getRandomGoods");
        Set<Integer> randomGoodsIdSet = new HashSet<>();
        while (randomGoodsIdSet.size()<amountOfRandomGoodsOnPage) {
            randomGoodsIdSet.add(goodsService.getRandomGoodsId());
        }
        List<Goods> goodsList = new ArrayList<>();
        for(int id : randomGoodsIdSet){
            goodsList.add(goodsService.getGoodsById(id));
        }
        return goodsList;
    }

    public List<Goods> getBestSellers(int amountOfBestSellers) {

        return goodsService.getBestSellers(amountOfBestSellers);
    }

    /**
     * Evaluates amount of pages depending on the number of goods
     * @return amount of pages
     */
    public long getAmountOfPages(long amountOfGoods){
        if (amountOfGoods==0)
            return 1;
        if (amountOfGoods% amountOfGoodsOnPage ==0){
            return amountOfGoods/ amountOfGoodsOnPage;
        }
        else {
            return amountOfGoods/ amountOfGoodsOnPage +1;
        }
    }
    /**
     * Evaluates total cost of order
     * @return total cost
     */
    public float getSumOfOrder(List<CartItem> cartList){
        float sum = 0;
        for (int i = 0; i < cartList.size(); i++) {
            sum = sum+cartList.get(i).getQuantity()*cartList.get(i).getGoods().getPrice();
        }
        return sum;
    }
}
