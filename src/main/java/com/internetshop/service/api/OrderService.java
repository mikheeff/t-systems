package com.internetshop.service.api;

import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.Order;
import com.internetshop.model.PaymentType;

import java.util.List;

public interface OrderService {
    List<DeliveryMethod> getAllDeliveryMethods();
    List<PaymentType> getAllPaymentTypes();
    void addOrder(Order order);
}
