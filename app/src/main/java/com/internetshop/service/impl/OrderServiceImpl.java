package com.internetshop.service.impl;

import com.internetshop.config.AppConfig;
import com.internetshop.controller.GoodsController;
import com.internetshop.controller.OrderController;
import com.internetshop.entities.*;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.repository.api.OrderRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import com.internetshop.service.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final ClientRepository clientRepository;
    private final GoodsService goodsService;
    private final ClientService clientService;
    private final MailService mailService;
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, GoodsRepository goodsRepository, ClientRepository clientRepository, GoodsService goodsService, ClientService clientService, MailService mailService) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.clientRepository = clientRepository;
        this.goodsService = goodsService;
        this.clientService = clientService;
        this.mailService = mailService;
    }

    /**
     * Gets all delivery methods from DB
     *
     * @return List of Delivery methods
     */
    @Transactional(readOnly = true)
    @Override
    public List<DeliveryMethod> getAllDeliveryMethods() {
        logger.info("getAllDeliveryMethods");
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        for (DeliveryMethodEntity deliveryMethodEntity : orderRepository.getAllDeliveryMethods()) {
            DeliveryMethod deliveryMethod = new DeliveryMethod(deliveryMethodEntity.getId(), deliveryMethodEntity.getName());
            deliveryMethods.add(deliveryMethod);
        }
        return deliveryMethods;
    }

    /**
     * Gets all payment types
     */
    @Transactional(readOnly = true)
    @Override
    public List<PaymentType> getAllPaymentTypes() {
        logger.info("getAllPaymentTypes");
        List<PaymentType> paymentTypes = new ArrayList<>();
        for (PaymentTypeEntity paymentTypeEntity : orderRepository.getAllPaymentTypes()) {
            PaymentType paymentType = new PaymentType(paymentTypeEntity.getId(), paymentTypeEntity.getName());
            paymentTypes.add(paymentType);
        }
        return paymentTypes;
    }

    /**
     * Gets all Statuses
     */
    @Transactional(readOnly = true)
    @Override
    public List<Status> getAllStatuses() {
        logger.info("getAllStatuses");

        List<Status> statuses = new ArrayList<>();
        for (StatusEntity statusEntity : orderRepository.getAllStatuses()) {
            Status status = new Status(statusEntity.getId(), statusEntity.getName());
            statuses.add(status);
        }
        return statuses;
    }

    /**
     * Converts order to DAO and send to repository
     *
     * @return id of the added order
     */
    @Transactional
    @Override
    public int addOrder(Order order) {
        logger.info("addOrder");

        OrderEntity orderEntity = new OrderEntity();

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        orderEntity.setDate(format.format(date));
        orderEntity.setPayStatus(0);
        orderEntity.setComment(order.getComment());

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(1); //status by default
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
            itemEntity.setGoodsEntity(goodsRepository.getGoodsById(item.getGoods().getId()));
            itemEntity.setOrderEntity(orderEntity);
            cartItemEntitySet.add(itemEntity);
        }
        orderEntity.setClientEntity(convertClientToDAO(order.getClient(), order.getClient().getRole().getId()));

        return orderRepository.addOrder(orderEntity, cartItemEntitySet);

    }

    /**
     * Gets all orders of selected client
     *
     * @param id client id
     * @return List of orders
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> getAllOrdersByClientId(int id) {
        logger.info("getAllOrdersByClientId");
        List<Order> orderList = new ArrayList<>();
        for (OrderEntity orderEntity : orderRepository.getAllOrdersByClientId(id)) {
            orderList.add(convertOrderToDTO(orderEntity));
        }
        return orderList;
    }

    /**
     * Gets all orders
     *
     * @return List of orders
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> getAllOrders() {
        logger.info("getAllOrders");

        List<Order> orderList = new ArrayList<>();
        for (OrderEntity orderEntity : orderRepository.getAllOrders()) {
            orderList.add(convertOrderToDTO(orderEntity));
        }
        return orderList;
    }

    /**
     * Edits order payment type, status ad delivery method
     */
    @Transactional
    @Override
    public void updateOrderStatus(Order order) {
        logger.info("updateOrderStatus");

        OrderEntity orderEntity = orderRepository.getOrderById(order.getId());

        PaymentTypeEntity paymentTypeEntity = orderRepository.getPaymentTypeByName(order.getPaymentType().getName());
        orderEntity.setPaymentType(paymentTypeEntity);

        DeliveryMethodEntity deliveryMethodEntity = orderRepository.getDeliveryMethodByName(order.getDeliveryMethod().getName());
        orderEntity.setDeliveryMethod(deliveryMethodEntity);

        if (!orderEntity.getStatus().getName().equals(order.getStatus().getName())) {
            if (orderEntity.getStatus().getName().equals("closed")) {
                decreaseSalesCounter(orderEntity);
                ClientEntity clientEntity = orderEntity.getClientEntity();
                clientEntity.setOrderCounter(clientEntity.getOrderCounter() - OrderController.getSumOfOrder(getAllCartItemsFromOrderByOrderId(order.getId())));
                clientRepository.updateUser(clientEntity);
            }
            StatusEntity statusEntity = orderRepository.getStatusByName(order.getStatus().getName());
            orderEntity.setStatus(statusEntity);
            if (order.getStatus().getName().equals("closed")) {
                setPayStatus(order.getId());
                increaseSalesCounter(orderEntity);
                ClientEntity clientEntity = orderEntity.getClientEntity();
                clientEntity.setOrderCounter(clientEntity.getOrderCounter() + OrderController.getSumOfOrder(getAllCartItemsFromOrderByOrderId(order.getId())));
                clientRepository.updateUser(clientEntity);
            }
            if ((orderEntity.getStatus().getName().equals("shipped") && orderEntity.getDeliveryMethod().getName().equals("pickup"))||(orderEntity.getStatus().getName().equals("canceled"))){
                Mail mail = new Mail();
                mail.setMailFrom(AppConfig.MAIL_FROM);
                ClientEntity client = orderEntity.getClientEntity();
                mail.setMailTo(client.getEmail());
                mail.setMailSubject("Dice Games, new order");

                Map< String, Object > model = new HashMap<>();
                model.put("firstName", client.getName());
                model.put("location", AppConfig.MAIL_LOCATION);
                model.put("signature", AppConfig.MAIL_SIGNATURE);
                model.put("msg", "your order status is "+"\""+orderEntity.getStatus().getName()+"\"");
                model.put("order",order);
                model.put("link", AppConfig.HOST_URL+"/order/details/"+orderEntity.getId());
                mail.setModel(model);

                mailService.sendEmail(mail,"order.txt");
                if (client.getPhone()!=null) {
                    mailService.sendSMS("Dear, "+client.getName()+
                            " , your order status is "+
                            orderEntity.getStatus().getName()+". Order ID: "+orderEntity.getId(),
                            client.getPhone());
                }
            }
        }

            orderRepository.updateOrder(orderEntity);


    }
    @Transactional
    public void setPayStatus(int id) {
        OrderEntity orderEntity = orderRepository.getOrderById(id);
        orderEntity.setPayStatus(1);
        orderRepository.updateOrder(orderEntity);
    }
    @Transactional
    public void increaseSalesCounter(OrderEntity orderEntity) {
        for (CartItemEntity item : orderEntity.getCartItemEntities()) {
            GoodsEntity goodsEntity = goodsRepository.getGoodsById(item.getGoodsEntity().getId());
            goodsEntity.setSalesCounter(goodsEntity.getSalesCounter() + item.getQuantity());
            goodsRepository.updateGoods(goodsEntity);
            goodsService.createUpdateMessage(goodsEntity);
        }

    }
    @Transactional
    public void decreaseSalesCounter(OrderEntity orderEntity) {
        for (CartItemEntity item : orderEntity.getCartItemEntities()) {
            GoodsEntity goodsEntity = goodsRepository.getGoodsById(item.getGoodsEntity().getId());
            goodsEntity.setSalesCounter(goodsEntity.getSalesCounter() - item.getQuantity());
            goodsRepository.updateGoods(goodsEntity);
            goodsService.createUpdateMessage(goodsEntity);
        }
    }

    /**
     * Gets all cart items from order by order id
     *
     * @return List of cart items
     */
    @Transactional(readOnly = true)
    @Override
    public List<CartItem> getAllCartItemsFromOrderByOrderId(int id) {
        logger.info("getAllCartItemsFromOrderByOrderId");

        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItemEntity itemEntity : orderRepository.getAllCartItemsFromOrderByOrderId(id)) {
            CartItem item = new CartItem();
            item.setQuantity(itemEntity.getQuantity());
            item.setGoods(goodsService.convertGoodsToDTO(itemEntity.getGoodsEntity()));
            item.setOrder(getOrderById(id));
            cartItemList.add(item);
        }
        return cartItemList;
    }

    /**
     * Gets order by it's id
     *
     * @return Order model
     */
    @Transactional(readOnly = true)
    @Override
    public Order getOrderById(int id) {
        logger.info("getOrderById");

        OrderEntity orderEntity = orderRepository.getOrderById(id);
        return convertOrderToDTO(orderEntity);
    }
    @Transactional(readOnly = true)
    @Override
    public long getAmountOfClosedOrdersByClientId(int id) {
        return orderRepository.getAmountOfClosedOrdersByClientId(id);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Float> getListOfRevenueForEachDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        List<Float> resultList = new ArrayList<>();

        for (int day = 1; day <= currentDay; day++) {
            resultList.add(0.0f);
            for (int id : orderRepository.getAllClosedOrdersIdsByDayOfMonth(day)) {
                for ( CartItemEntity item : orderRepository.getAllCartItemsFromOrderByOrderId(id)){
                    resultList.set(day-1,resultList.get(day-1)+item.getQuantity()*item.getGoodsEntity().getPrice());
                }
            }
        }
        return resultList;
    }
    /**
     * convert Order Entity to Order Model
     */
    public Order convertOrderToDTO(OrderEntity orderEntity) {
        logger.info("convertOrderToDTO");

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



    /**
     * converts client model to entity
     *
     * @return client entity
     */
    public ClientEntity convertClientToDAO(Client client, int id) {
        logger.info("convertClientToDAO");

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
}
