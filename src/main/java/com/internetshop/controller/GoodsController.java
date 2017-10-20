package com.internetshop.controller;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.model.*;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;

    @Autowired
    public HttpSession session;


    private static final int amountOfGoodsOnPage = 9; //при вводе больше 9 едет картинки на первой странице??
    private static final int amountOfRandomGoodsOnPage = 6;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllGoods(ModelMap modelMap) { // TODO: 11.10.2017 HttpSession session
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("cartList",session.getAttribute("cartList"));

        return "goods";
    }
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String getAllGoods(@PathVariable(value = "page") int page, ModelMap modelMap) {
        modelMap.put("currentPage",page);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods", goodsService.getAllGoods(amountOfGoodsOnPage*(page-1),amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("cartList",session.getAttribute("cartList"));
        return "goods";
    }
    @RequestMapping(value ="/{category}/page/{page}", method = RequestMethod.GET)
    public String getAllGoodsByCategory(@PathVariable("category") String categoryName,@PathVariable(value = "page") int page, ModelMap modelMap) {
        modelMap.put("currentPage",page);
        modelMap.put("amountOfPages",getAmountOfPages(categoryName));
        modelMap.put("listGoods", goodsService.getAllGoodsByCategoryName(0,amountOfGoodsOnPage,categoryName));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryName",categoryName);
        modelMap.put("categoryFilter",true);
        modelMap.put("cartList",session.getAttribute("cartList"));
        return "goods";
    }

    @RequestMapping(value ="/employee/add", method = RequestMethod.GET)
    public String addGoods(ModelMap modelMap) {
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        modelMap.put("listCategory",goodsService.getAllCategories());
        return "add_page";
    }
    @RequestMapping(value ="/employee/add/goods", method = RequestMethod.POST)
    public String addGoods(@ModelAttribute (value = "goods") @Valid Goods goods,
                           BindingResult bindingResultGoods,
                           ModelMap modelMap) {

        if(bindingResultGoods.hasErrors()) {
            modelMap.put("error1", "Invalid params!");
            modelMap.put("goods",goods);
            modelMap.put("category",new Category());
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "add_page";
        }

        this.goodsService.addGoods(goods);
        modelMap.put("msgG", "You've been added new goods successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "add_page";
    }
    @RequestMapping(value = "/employee/add/category",method = RequestMethod.POST)
    public String addCategory( @ModelAttribute (value = "category")  @Valid Category category,
                               BindingResult bindingResult,ModelMap modelMap){
        if(bindingResult.hasErrors()) {
            modelMap.put("error2", "Invalid params!");
            modelMap.put("goods",new Goods());
            modelMap.put("category",category);
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "add_page";
        }

        this.goodsService.addCategory(category);
        modelMap.put("msgC", "You've been added new category successfully.");

        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        return "add_page";
    }

    @RequestMapping(value ="/employee/delete/{id}", method = RequestMethod.GET)
    public String deleteGoods(@PathVariable(value = "id") int id) {
        this.goodsService.deleteGoodsById(id);
        return "redirect:/catalog";
    }

    @RequestMapping(value ="/employee/edit", method = RequestMethod.POST)
    public String editGoods(@ModelAttribute(value = "goods")  @Valid Goods goods, BindingResult bindingResult,
                            ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            modelMap.put("error", "Invalid params!");
            modelMap.put("randomGoods",getRandomGoods());
            modelMap.put("goods",goods);
            modelMap.put("listCategory",goodsService.getAllCategories());
            return "goods_detail";
        }
        this.goodsService.updateGoods(goods);
        modelMap.put("goods", goodsService.getGoodsById(goods.getId()));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("msg", "You've been edited goods successfully.");
        return "goods_detail";
    }

    @RequestMapping(value ="/goods/{id}", method = RequestMethod.GET)
    public String getGoodsById(@PathVariable(value = "id") int id, ModelMap modelMap) {
        modelMap.put("goods", goodsService.getGoodsById(id));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        modelMap.put("cartItem", cartItem);
        modelMap.put("cartList",session.getAttribute("cartList"));
        return "goods_detail";
    }
    @RequestMapping(value = "/goods/cart/{id}/add", method = RequestMethod.POST)
    public String addToCart(@PathVariable(value = "id") int id, CartItem cartItem){
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
    @RequestMapping(value = "/goods/cart",method = RequestMethod.GET)
    public String getCartItems(ModelMap modelMap,
                               @RequestParam(value = "error", required = false) String error){
        modelMap.put("randomGoods",getRandomGoods());
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
        return "cart";
    }

    @RequestMapping(value = "/profile/goods/cart/continue",method = RequestMethod.GET)
    public String getOrder(ModelMap modelMap){
        modelMap.put("randomGoods",getRandomGoods());
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
        return "confirm_order";
    }

    @RequestMapping(value = "/profile/goods/order/confirm",method = RequestMethod.POST)
    public String addOrder(ModelMap modelMap,Order order){
        Set<CartItem> cartItemSet = new HashSet<>((ArrayList<CartItem>)session.getAttribute("cartList"));
        order.setCartItems(cartItemSet);
        Client client = (Client)session.getAttribute("client");
        order.setClient(client);
        int orderId = orderService.addOrder(order);
//        client.getOrder().add(order);
        modelMap.put("orderId",orderId);
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        return "order_success";
    }
    @RequestMapping(value = "/profile/employee/details/order/{id}", method = RequestMethod.GET)
    public String getOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap){
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("cartItemsList",orderService.getAllCartItemsFromOrderByOrderId(id));
        return "order_details";
    }


    @RequestMapping(value = "/employee/edit/category/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable(value = "id") int id, ModelMap modelMap){
        boolean editCatFlag = true;
        modelMap.put("editCatFlag",editCatFlag);
        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("category",goodsService.getCategoryById(id));
        return "goods";
    }

    @RequestMapping(value ="/employee/edit/category", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute(value = "category")  @Valid Category category, BindingResult bindingResult,
                            ModelMap modelMap) {
        if(bindingResult.hasErrors()) {
            boolean editCatFlag = true;
            modelMap.put("editCatFlag",editCatFlag);
            modelMap.put("category",category);
            modelMap.put("currentPage",1);
            modelMap.put("amountOfPages",getAmountOfPages());
            modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
            modelMap.put("randomGoods",getRandomGoods());
            modelMap.put("listCategory",goodsService.getAllCategories());
            modelMap.put("categoryFilter",false);
            return "goods";
        }


        goodsService.updateCategory(category);

        modelMap.put("currentPage",1);
        modelMap.put("amountOfPages",getAmountOfPages());
        modelMap.put("listGoods",goodsService.getAllGoods(0,amountOfGoodsOnPage));
        modelMap.put("randomGoods",getRandomGoods());
        modelMap.put("listCategory",goodsService.getAllCategories());
        modelMap.put("categoryFilter",false);
        modelMap.put("msg", "You've been edited category successfully.");
        return "goods";
    }

    @RequestMapping(value ="/employee/delete/category/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable(value = "id") int id) {
        this.goodsService.deleteCategoryById(id);
        return "redirect:/catalog";
    }

    public long getAmountOfPages(){
        if (goodsService.getAmountOfGoods()% amountOfGoodsOnPage ==0){
            return goodsService.getAmountOfGoods()/ amountOfGoodsOnPage;
        }
        else {
            return goodsService.getAmountOfGoods()/ amountOfGoodsOnPage +1;
        }
    }
    public long getAmountOfPages(String categoryName){
        if (goodsService.getAmountOfGoodsByCategoryName(categoryName)% amountOfGoodsOnPage ==0){
            return goodsService.getAmountOfGoodsByCategoryName(categoryName)/ amountOfGoodsOnPage;
        }
        else {
            return goodsService.getAmountOfGoodsByCategoryName(categoryName)/ amountOfGoodsOnPage +1;
        }
    }

    public List<Goods> getRandomGoods(){
        Set<Integer> randomGoodsIdSet = new HashSet<>();
        while (randomGoodsIdSet.size()<amountOfRandomGoodsOnPage) { //todo а если количество товаров будет всегда меньше запрашиваемо числа случайных товаров?
            randomGoodsIdSet.add(goodsService.getRandomGoodsId());
        }
        List<Goods> goodsList = new ArrayList<>();
        for(int id : randomGoodsIdSet){
            goodsList.add(goodsService.getGoodsById(id));
        }
        return goodsList;
    }

    public float getSumOfOrder(List<CartItem> cartList){
        float sum = 0;
        for (int i = 0; i < cartList.size(); i++) {
            sum = sum+cartList.get(i).getQuantity()*cartList.get(i).getGoods().getPrice();
        }
        return sum;
    }
}
