package com.internetshop.repository.api;

import com.internetshop.entities.DeliveryMethodEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.entities.PaymentTypeEntity;
import com.internetshop.entities.StatusEntity;

import java.util.List;

public interface OrderRepository {
    List<DeliveryMethodEntity> getAllDeliveryMethods();
    List<PaymentTypeEntity> getAllPaymentTypes();
    StatusEntity getStatusById(int id);
    int getIdPaymentTypeByName(String name);
    int getIdDeliveryMethodByName(String name);
    void addOrder(OrderEntity orderEntity);
}
