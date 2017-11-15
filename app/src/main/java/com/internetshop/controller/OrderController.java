package com.internetshop.controller;

import com.internetshop.model.CartItem;
import com.internetshop.model.Client;
import com.internetshop.model.Order;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class.getName());

    private final GoodsService goodsService;
    private final ClientService clientService;
    private final OrderService orderService;
    public final HttpSession session;

    @Autowired
    public OrderController(GoodsService goodsService, ClientService clientService, OrderService orderService, HttpSession session) {
        this.goodsService = goodsService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.session = session;
    }

    /**
     * Puts order details to model map
     *
     * @return confirm order page
     */
    @RequestMapping(value = "/continue", method = RequestMethod.GET)
    public String getOrder(ModelMap modelMap) {
        logger.info("getOrder");
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("cartList", session.getAttribute("cartList"));
        modelMap.put("sum", getSumOfOrder((ArrayList<CartItem>) session.getAttribute("cartList")));
        modelMap.put("client", session.getAttribute("client"));
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        modelMap.put("date", formatForDateNow.format(date));
        modelMap.put("order", new Order());
        modelMap.put("listDeliveryMethod", orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType", orderService.getAllPaymentTypes());
        String searchStr = "";
        modelMap.put("search", searchStr);
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        return "confirm_order";
    }

    /**
     * Puts order details to order model and send it to service
     * for adding
     *
     * @return order success page
     */

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String addOrder(ModelMap modelMap, Order order) {
        logger.info("addOrder");
        Set<CartItem> cartItemSet = new HashSet<>((ArrayList<CartItem>) session.getAttribute("cartList"));
        order.setCartItems(cartItemSet);
        Client client = (Client) session.getAttribute("client");
        order.setClient(client);
        int orderId = orderService.addOrder(order);
        modelMap.put("orderId", orderId);
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("listCategory", goodsService.getAllCategories());
        String searchStr = "";
        modelMap.put("search", searchStr);
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        return "order_success";
    }

    /**
     * Gets information for selected object
     *
     * @return order details page
     */
    @RequestMapping(value = "/employee/details/{id}", method = RequestMethod.GET)
    public String getOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap,
                                  @RequestParam(value = "msg", required = false) String msg) {
        logger.info("getOrderDetails");
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("orderItemsList", orderService.getAllCartItemsFromOrderByOrderId(id));
        modelMap.put("listDeliveryMethod", orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType", orderService.getAllPaymentTypes());
        modelMap.put("listStatus", orderService.getAllStatuses());
        modelMap.put("sum", getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(id)));
        modelMap.put("order", orderService.getOrderById(id));
        String searchStr = "";
        modelMap.put("search", searchStr);
        if (msg != null) {
            modelMap.put("msg", "Order has been successfully edited");
        }
        return "order_details";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getClientOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap,
                                  @RequestParam(value = "msg", required = false) String msg) {
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("orderItemsList", orderService.getAllCartItemsFromOrderByOrderId(id));
        modelMap.put("sum", getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(id)));
        Client client = (Client) session.getAttribute("client");
        modelMap.put("client", client);
        Order order = orderService.getOrderById(id);
        if (order.getClient().getId()!=client.getId()){
            return "404";
        }
        modelMap.put("order", order);
        String searchStr = "";
        modelMap.put("search", searchStr);
        if (msg != null) {
            modelMap.put("msg", "Order has been successfully paid!");
        }

        return "order_details";
    }

    /**
     * Edits order payment type, status ad delivery method
     *
     * @return order details page
     */
    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.POST)
    public String updateOrderStatus(Order order, @PathVariable(value = "id") int id,ModelMap modelMap) {
        logger.info("updateOrderStatus");
        order.setId(id);
        try {
            orderService.updateOrderStatus(order);
        } catch (IllegalThreadStateException e){
            modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
            modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
            modelMap.put("listCategory", goodsService.getAllCategories());
            modelMap.put("error","Lost connection with MQ Server");
            return "500";
        }
        return "redirect:/order/employee/details/" + order.getId() + "?msg";
    }
    @RequestMapping(value = "/pay/{id}", method = RequestMethod.GET)
    public String payOrder(@PathVariable(value = "id") int orderId, ModelMap modelMap){
        modelMap.put("randomGoods", goodsService.getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", goodsService.getBestSellers(GoodsController.amountOfBestSellers));
        modelMap.put("listCategory", goodsService.getAllCategories());
        modelMap.put("sum", getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(orderId)));
        modelMap.put("id",orderId);
        return "pay_page";
    }
    @RequestMapping(value = "/pay/{id}", method = RequestMethod.POST)
    public String payOrder(@PathVariable(value = "id") int orderId){
        orderService.setPayStatus(orderId);
        return "redirect:/order/details/" + orderId + "?msg";
    }

    /**
     * Evaluates total cost of order
     *
     * @return total cost
     */
    public static float getSumOfOrder(List<CartItem> cartList) {
        float sum = 0;
        for (int i = 0; i < cartList.size(); i++) {
            sum = sum + cartList.get(i).getQuantity() * cartList.get(i).getGoods().getPrice();
        }
        return sum;
    }

}
