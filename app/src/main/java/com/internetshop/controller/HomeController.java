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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
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

    private static Logger logger = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    public HomeController(GoodsService goodsService, OrderService orderService, ClientService clientService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.clientService = clientService;
    }


    /**
     * Gets goods for homepage
     *
     * @return homepage
     */
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String main(ModelMap modelMap) {
        logger.info("main");
        modelMap.put("randomGoods", goodsService.getRandomGoods(6));
        modelMap.put("listCategory", goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search", searchStr);
        modelMap.put("bestSellersList", goodsService.getBestSellers(8));
        return "index";
    }


    @RequestMapping(value = "employee/administration", method = RequestMethod.GET)
    public String getAdminPage(ModelMap modelMap) {
        logger.info("addGoods");
        modelMap.put("goods", new Goods());
        modelMap.put("category", new Category());
        modelMap.put("listCategory", goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search", searchStr);
        modelMap.put("clientOrdersList", orderService.getAllOrders());
        modelMap.put("bestSellersList", goodsService.getBestSellers(10));
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
        return "employee_admin";
    }

}
