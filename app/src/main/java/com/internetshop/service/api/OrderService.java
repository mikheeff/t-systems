package com.internetshop.service.api;

import com.internetshop.entities.OrderEntity;
import com.internetshop.model.*;

import java.util.List;

public interface OrderService {
    List<DeliveryMethod> getAllDeliveryMethods();
    List<PaymentType> getAllPaymentTypes();
    List<Status> getAllStatuses();
    int addOrder(Order order);
    List<Order> getAllOrdersByClientId(int id);
    List<Order> getAllOrders();
    List<CartItem> getAllCartItemsFromOrderByOrderId(int id);
    Order getOrderById(int id);
    void updateOrderStatus(Order order);
    void setPayStatus(int id);
    long getAmountOfClosedOrdersByClientId(int id);
    List<Float> getListOfRevenueForEachDayOfCurrentMonth();


}
