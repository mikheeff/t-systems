package com.internetshop.controller;

import com.internetshop.model.Category;
import com.internetshop.model.Client;
import com.internetshop.model.Goods;
import com.internetshop.model.Order;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    private GoodsService goodsService;
    private OrderService orderService;
    private ClientService clientService;
    private HttpSession session;

    public static final int amountOfGoodsOnHomePage = 8;
    public static final int amountOfBestSellersOnPage = 4;


    private static Logger logger = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    public HomeController(GoodsService goodsService, OrderService orderService, ClientService clientService,HttpSession session) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.session = session;
    }


    /**
     * Gets goods for homepage
     *
     * @return homepage
     */
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String main(ModelMap modelMap) {
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("bestSellersList", goodsService.getBestSellers(amountOfGoodsOnHomePage));
        modelMap.put("newGoodsList", goodsService.getNewGoods(amountOfGoodsOnHomePage));
        return "index";
    }
    @RequestMapping(value = "contact",method = RequestMethod.GET)
    public String contactUs(ModelMap modelMap){
        goodsService.putDefaultAttributes(modelMap);
        return "contact";
    }

    @RequestMapping(value = "bestsellers", method = RequestMethod.GET)
    public String getBestSellersPage(ModelMap modelMap){
        List<Category> categoryList = goodsService.getAllCategories();
        modelMap.put("listCategory", categoryList);
        for (Category category : categoryList) {
            modelMap.put("bestSellersOf"+category.getId(),goodsService.getBestSellersByCategory(category,amountOfBestSellersOnPage));
        }
        return "best_sellers";
    }


    @RequestMapping(value = "employee/administration", method = RequestMethod.GET)
    public String getAdminPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "msgC", required = false) String msgC,
                               @RequestParam(value = "error2", required = false) String error2,
                               @RequestParam(value = "msgG", required = false) String msgG,
                               @RequestParam(value = "error1", required = false) String error1,ModelMap modelMap) {
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("clientOrdersList", orderService.getAllOrders());
        modelMap.put("bestSellersList", goodsService.getBestSellers(10));
        modelMap.put("allGoodsList", goodsService.getAllGoods());
        List<Client> bestClientsList = clientService.getBestClientsList(10);
        modelMap.put("bestClientsList", bestClientsList);
        List<Long> amountOfOrders = new ArrayList<>();
        for (Client client : bestClientsList) {
            amountOfOrders.add(orderService.getAmountOfClosedOrdersByClientId(client.getId()));
        }
        modelMap.put("amountOfOrders",amountOfOrders);
        List<Float> revenueList = orderService.getListOfRevenueForEachDayOfCurrentMonth();
        int currentDayOfMonth = revenueList.size();
        modelMap.put("currentDayOfMonth",currentDayOfMonth);
        Float[] dataArray = new Float[currentDayOfMonth];
        dataArray = revenueList.toArray(dataArray);
        modelMap.put("dataArray",dataArray);
        float monthlyRevenue = 0;
        for (float dayMoney : revenueList){
            monthlyRevenue = monthlyRevenue + dayMoney;
        }
        modelMap.put("monthlyRevenue",monthlyRevenue);
        modelMap.put("todayMoney",revenueList.get(revenueList.size()-1));
        if (error != null){
            modelMap.put("error","No order with such id");
        }
        if (msgC != null){
            modelMap.put("msgC","You've been added new category successfully.");
        }
        if (error2 != null){
            modelMap.put("error2", "Invalid params!");
        }
        if (msgG != null){
            modelMap.put("msgG","You've been added new goods successfully.");
        }
        if (error1 != null){
            modelMap.put("goods",session.getAttribute("goodsInv"));
            modelMap.put("error1","Invalid params!");
        }
        return "employee_admin";
    }

}
