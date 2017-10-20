package com.internetshop.service.impl;

import com.internetshop.entities.*;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.repository.api.OrderRepository;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GoodsService goodsService;
    private final ClientRepository clientRepository;

//    @Autowired
//    public OrderServiceImpl(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }


    public OrderServiceImpl(OrderRepository orderRepository, GoodsService goodsService, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.goodsService = goodsService;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<DeliveryMethod> getAllDeliveryMethods() {
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        for (DeliveryMethodEntity deliveryMethodEntity : orderRepository.getAllDeliveryMethods()) {
            DeliveryMethod deliveryMethod = new DeliveryMethod(deliveryMethodEntity.getId(), deliveryMethodEntity.getName());
            deliveryMethods.add(deliveryMethod);
        }
        return deliveryMethods;
    }

    @Override
    public List<PaymentType> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        for (PaymentTypeEntity paymentTypeEntity : orderRepository.getAllPaymentTypes()) {
            PaymentType paymentType = new PaymentType(paymentTypeEntity.getId(), paymentTypeEntity.getName());
            paymentTypes.add(paymentType);
        }
        return paymentTypes;
    }

    @Override
    public void addOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        orderEntity.setDate(date);
        orderEntity.setPayStatus(1);
        orderEntity.setComment(order.getComment());

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(1); //статус по умолчанию
        statusEntity.setName(orderRepository.getStatusById(1).getName());
        orderEntity.setStatus(statusEntity);

        PaymentTypeEntity paymentTypeEntity = new PaymentTypeEntity();
        paymentTypeEntity.setId(orderRepository.getIdPaymentTypeByName(order.getPaymentType().getName()));
        paymentTypeEntity.setName(order.getPaymentType().getName());

        orderEntity.setPaymentType(paymentTypeEntity);

        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();
        deliveryMethodEntity.setId(orderRepository.getIdDeliveryMethodByName(order.getDeliveryMethod().getName()));
        deliveryMethodEntity.setName(order.getDeliveryMethod().getName());

        orderEntity.setDeliveryMethod(deliveryMethodEntity);

        Set<CartItem> cartItemSet = order.getCartItems();
        Set<CartItemEntity> cartItemEntitySet = new HashSet<>();

        for (CartItem item : cartItemSet) {
            CartItemEntity itemEntity = new CartItemEntity();
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setGoodsEntity(goodsService.convertGoodsToDAO(item.getGoods()));
            itemEntity.setOrderEntity(orderEntity);
        }
        orderEntity.setClientEntity(convertClientToDAO(order.getClient(),order.getClient().getRole().getId()));

        orderEntity.setCartItemEntities(cartItemEntitySet);
        orderRepository.addOrder(orderEntity);

    }

    public ClientEntity convertClientToDAO(Client client,int id) {
        RoleEntity role = new RoleEntity();
        role.setId(id);                                  // по умолчанию ставим роль юзера - 3(client)
        role.setName(clientRepository.getRoleById(id).getName());
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setBirthdate(client.getBirthdate());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setOrderCounter(client.getOrderCounter());
        clientEntity.setRoleEntity(role);
        clientEntity.setClientAddressEntity(clientRepository.getAddressById(client.getClientAddress().getId()));
        return clientEntity;
    }



}
