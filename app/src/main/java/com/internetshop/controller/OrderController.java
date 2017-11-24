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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("")
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
    @RequestMapping(value = "/order/continue", method = RequestMethod.GET)
    public String getOrder(ModelMap modelMap) {
        logger.info("getOrder");
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("cartList", cartList);
        modelMap.put("sum", orderService.getSumOfOrder(cartList));
        modelMap.put("client", session.getAttribute("client"));
        if (cartList == null) {
            modelMap.put("error", "No products in cart");
            return "404";
        }
        if (!orderService.isPossibleToCreateOrder(cartList)) {
            modelMap.put("error", "The requested number of products is not available");
            return "cart";
        }

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        modelMap.put("date", formatForDateNow.format(date));
        modelMap.put("order", new Order());
        modelMap.put("listDeliveryMethod", orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType", orderService.getAllPaymentTypes());
        return "confirm_order";
    }

    /**
     * Puts order details to order model and send it to service
     * for adding
     *
     * @return order success page
     */

    @RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
    public String addOrder(Order order, ModelMap modelMap) {
        logger.info("addOrder");
        goodsService.putDefaultAttributes(modelMap);
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) session.getAttribute("cartList");
        Client client = (Client) session.getAttribute("client");
        if (!orderService.isPossibleToCreateOrder(cartList)) {
            modelMap.put("cartList", cartList);
            modelMap.put("sum", orderService.getSumOfOrder(cartList));
            modelMap.put("error", "The requested number of products is not available");
            modelMap.put("client", client);
            return "cart";
        }
        Set<CartItem> cartItemSet = new HashSet<>(cartList);
        order.setCartItems(cartItemSet);
        order.setClient(client);
        int orderId = orderService.addOrder(order);
        orderService.sendNewOrderMail(orderId);
        return "redirect:/order/message?id=" + orderId;
    }

    @RequestMapping(value = "/order/message", method = RequestMethod.GET)
    public String showOrderMessage(@RequestParam(value = "id", required = false) String id, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("msg", "Dear customer, your order with ID: " + id + " is accepted for processing. In the near future an operator will contact you.");
        return "message";
    }

    /**
     * Gets information for selected object
     *
     * @return order details page
     */
    @RequestMapping(value = "/employee/order/details/{id}", method = RequestMethod.GET)
    public String getOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap,
                                  @RequestParam(value = "msg", required = false) String msg) {
        logger.info("getOrderDetails");
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("orderItemsList", orderService.getAllCartItemsFromOrderByOrderId(id));
        modelMap.put("listDeliveryMethod", orderService.getAllDeliveryMethods());
        modelMap.put("listPaymentType", orderService.getAllPaymentTypes());
        modelMap.put("listStatus", orderService.getAllStatuses());
        modelMap.put("sum", orderService.getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(id)));
        modelMap.put("order", orderService.getOrderById(id));
        if (msg != null) {
            modelMap.put("msg", "Order has been successfully edited");
        }
        return "order_details";
    }

    @RequestMapping(value = "/order/details/{id}", method = RequestMethod.GET)
    public String getClientOrderDetails(@PathVariable(value = "id") int id, ModelMap modelMap,
                                        @RequestParam(value = "msg", required = false) String msg, HttpServletRequest httpServletRequest) {
        if (session.getAttribute("client") == null) {
            session.setAttribute("client", clientService.getUserByEmail(httpServletRequest.getUserPrincipal().getName()));
        }
        goodsService.putDefaultAttributes(modelMap);
        modelMap.put("orderItemsList", orderService.getAllCartItemsFromOrderByOrderId(id));
        modelMap.put("sum", orderService.getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(id)));
        Client client = (Client) session.getAttribute("client");
        modelMap.put("client", client);
        Order order;
        try {
            order = orderService.getOrderById(id);
        } catch (NullPointerException e) {
            return "404";
        }
        if (order.getClient().getId() != client.getId()) {
            return "404";
        }
        modelMap.put("order", order);
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
    @RequestMapping(value = "/employee/order/edit/{id}", method = RequestMethod.POST)
    public String updateOrderStatus(Order order, @PathVariable(value = "id") int id, ModelMap modelMap) {
        logger.info("updateOrderStatus");
        order.setId(id);
        try {
            orderService.updateOrderStatus(order);
        } catch (IllegalThreadStateException e) {
            goodsService.putDefaultAttributes(modelMap);
            modelMap.put("error", "Lost connection with MQ Server");
            return "500";
        }
        return "redirect:/employee/order/details/" + order.getId() + "?msg";
    }

    @RequestMapping(value = "/order/pay/{id}", method = RequestMethod.GET)
    public String payOrder(@PathVariable(value = "id") int orderId, ModelMap modelMap) {
        goodsService.putDefaultAttributes(modelMap);
        if (orderService.getOrderById(orderId).getPayStatus() == 1) {
            return "redirect:/order/details/" + orderId + "?msg";
        }
        modelMap.put("sum", orderService.getSumOfOrder(orderService.getAllCartItemsFromOrderByOrderId(orderId)));
        modelMap.put("id", orderId);
        return "pay_page";
    }

    @RequestMapping(value = "/order/pay/{id}", method = RequestMethod.POST)
    public String payOrder(@PathVariable(value = "id") int orderId) {
        orderService.setPayStatus(orderId);
        return "redirect:/order/details/" + orderId + "?msg";
    }

    /**
     * Evaluates total cost of order
     *
     * @return total cost
     */


}
