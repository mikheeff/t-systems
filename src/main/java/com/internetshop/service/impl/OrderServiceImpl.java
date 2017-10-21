package com.internetshop.service.impl;

import com.internetshop.entities.*;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.repository.api.OrderRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final ClientRepository clientRepository;
    private final GoodsService goodsService;
    private final ClientService clientService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, GoodsRepository goodsRepository, ClientRepository clientRepository, GoodsService goodsService, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.clientRepository = clientRepository;
        this.goodsService = goodsService;
        this.clientService = clientService;
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
    public List<Status> getAllStatuses() {
        List<Status> statuses = new ArrayList<>();
        for (StatusEntity statusEntity : orderRepository.getAllStatuses()) {
            Status status = new Status(statusEntity.getId(), statusEntity.getName());
            statuses.add(status);
        }
        return statuses;
    }

    @Override
    public int addOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        orderEntity.setDate(date.toString());
        orderEntity.setPayStatus(0);
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
//            itemEntity.setGoodsEntity(convertGoodsToDAO(item.getGoods()));
            itemEntity.setGoodsEntity(goodsRepository.getGoodsById(item.getGoods().getId()));
            itemEntity.setOrderEntity(orderEntity);
            cartItemEntitySet.add(itemEntity);
        }
        orderEntity.setClientEntity(convertClientToDAO(order.getClient(),order.getClient().getRole().getId()));

//        orderEntity.setCartItemEntities();
        return orderRepository.addOrder(orderEntity,cartItemEntitySet);

    }

    @Override
    public List<Order> getAllOrdersByClientId(int id) {
        List<Order> orderList = new ArrayList<>();
        for(OrderEntity orderEntity : orderRepository.getAllOrdersByClientId(id)){
            orderList.add(convertOrderToDTO(orderEntity));
        }
        return orderList;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        for(OrderEntity orderEntity : orderRepository.getAllOrders()){
            orderList.add(convertOrderToDTO(orderEntity));
        }
        return orderList;
    }

    @Override
    public void updateOrderStatus(Order order) {
        OrderEntity orderEntity = orderRepository.getOrderById(order.getId());

        PaymentTypeEntity paymentTypeEntity = orderRepository.getPaymentTypeByName(order.getPaymentType().getName());
        orderEntity.setPaymentType(paymentTypeEntity);

        DeliveryMethodEntity deliveryMethodEntity = orderRepository.getDeliveryMethodByName(order.getDeliveryMethod().getName());
        orderEntity.setDeliveryMethod(deliveryMethodEntity);

        StatusEntity statusEntity = orderRepository.getStatusByName(order.getStatus().getName());
        orderEntity.setStatus(statusEntity);

        orderEntity.setPayStatus(order.getPayStatus());

        orderRepository.updateOrder(orderEntity);


    }

    @Override
    public List<CartItem> getAllCartItemsFromOrderByOrderId(int id) {
        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItemEntity itemEntity : orderRepository.getAllCartItemsFromOrderByOrderId(id)){
            CartItem item = new CartItem();
            item.setQuantity(itemEntity.getQuantity());
            item.setGoods(goodsService.convertGoodsToDTO(itemEntity.getGoodsEntity()));
            item.setOrder(getOrderById(id));
            cartItemList.add(item);
        }
        return cartItemList;
    }

    @Override
    public Order getOrderById(int id) {
        OrderEntity orderEntity = orderRepository.getOrderById(id);
        return convertOrderToDTO(orderEntity);
    }

    public Order convertOrderToDTO(OrderEntity orderEntity){
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDate(orderEntity.getDate());
        order.setPayStatus(orderEntity.getPayStatus());
        order.setComment(orderEntity.getComment());

        PaymentType paymentType = new PaymentType();
        paymentType.setId(orderEntity.getPaymentType().getId());
        paymentType.setName(orderEntity.getPaymentType().getName());
        order.setPaymentType(paymentType);

        DeliveryMethod deliveryMethod = new DeliveryMethod();
        deliveryMethod.setId(orderEntity.getDeliveryMethod().getId());
        deliveryMethod.setName(orderEntity.getDeliveryMethod().getName());
        order.setDeliveryMethod(deliveryMethod);

        Status status = new Status();
        status.setId(orderEntity.getStatus().getId());
        status.setName(orderEntity.getStatus().getName());
        order.setStatus(status);

        Set<CartItem> cartItemSet = new HashSet<>();
        Set<CartItemEntity> cartItemEntitySet = orderEntity.getCartItemEntities();

        for (CartItemEntity itemEntity : cartItemEntitySet) {
            CartItem item = new CartItem();
            item.setQuantity(itemEntity.getQuantity());
            item.setGoods(goodsService.getGoodsById(itemEntity.getGoodsEntity().getId()));
            item.setOrder(order);
            cartItemSet.add(item);
        }
        order.setCartItems(cartItemSet);

        Client client = clientService.getClientById(orderEntity.getClientEntity().getId());
        order.setClient(client);
        return order;
    }

    public ClientEntity convertClientToDAO(Client client, int id) {
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setName(clientRepository.getRoleById(id).getName());
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client.getId());
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setBirthdate(client.getBirthdate());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setOrderCounter(client.getOrderCounter());
        clientEntity.setRoleEntity(role);
        ClientAddressEntity clientAddressEntity = new ClientAddressEntity(
                client.getClientAddress().getCountry(),
                client.getClientAddress().getCity(),
                client.getClientAddress().getPostcode(),
                client.getClientAddress().getStreet(),
                client.getClientAddress().getHouse(),
                client.getClientAddress().getFlat(),
                client.getClientAddress().getAddition());
        clientAddressEntity.setId(client.getClientAddress().getId());
        clientEntity.setClientAddressEntity(clientAddressEntity);
        return clientEntity;
    }


//    public GoodsEntity convertGoodsToDAO(Goods goods) {
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setId(goodsRepository.getIdCategoryByName(goods.getCategory().getName()));
//        categoryEntity.setName(goods.getName());
//
//        RuleEntity ruleEntity = new RuleEntity();
//        ruleEntity.setId(goodsRepository.getIdRuleByName(goods.getRule().getName()));
//        ruleEntity.setName(goods.getName());
//
//        GoodsEntity goodsEntity = new GoodsEntity(
//                goods.getName(),
//                goods.getPrice(),
//                goods.getNumberOfPlayers(),
//                goods.getDuration(),
//                goods.getAmount(),
//                goods.getVisible(),
//                goods.getDescription(),
//                goods.getImg(),
//                categoryEntity,
//                ruleEntity);
//        goodsEntity.setId(goods.getId());
//        return goodsEntity;
//    }



}
