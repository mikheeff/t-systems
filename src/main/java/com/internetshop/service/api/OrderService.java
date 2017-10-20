package com.internetshop.service.api;

import com.internetshop.model.CartItem;
import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.Order;
import com.internetshop.model.PaymentType;

import java.util.List;

public interface OrderService {
    List<DeliveryMethod> getAllDeliveryMethods();
    List<PaymentType> getAllPaymentTypes();
    int addOrder(Order order);
    List<Order> getAllOrdersByClientId(int id);
    List<Order> getAllOrders();
    List<CartItem> getAllCartItemsFromOrderByOrderId(int id);

}
