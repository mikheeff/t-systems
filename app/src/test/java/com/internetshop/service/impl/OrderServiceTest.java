package com.internetshop.service.impl;

import com.internetshop.entities.*;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.repository.api.OrderRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.MailService;
import com.internetshop.service.api.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private GoodsRepository goodsRepository;
    private ClientRepository clientRepository;
    private GoodsService goodsService;
    private ClientService clientService;
    private MailService mailService;
    private OrderEntity orderEntity;
    private Order order;

    @Before
    public void setUp() throws Exception {
        order = getOrder();
        orderEntity = getOrderEntity();
        orderRepository = mock(OrderRepository.class);
        goodsRepository = mock(GoodsRepository.class);
        clientRepository = mock(ClientRepository.class);
        goodsService = mock(GoodsService.class);
        clientService = mock(ClientService.class);
        mailService = mock(MailService.class);
        orderService = new OrderServiceImpl(orderRepository,goodsRepository,clientRepository,goodsService,clientService,mailService);

    }

    @Test
    public void getSumOfOrderEmptyCart(){
        List<CartItem> cartList = new ArrayList<>();
        float sum = orderService.getSumOfOrder(cartList);
        assertEquals(0,sum,0.01);
    }

    @Test
    public void getSumOfOrderTest(){
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();
        Goods goods1 = new Goods();
        Goods goods2 = new Goods();
        goods1.setPrice(1200);
        goods2.setPrice(340);
        item1.setGoods(goods1);
        item1.setQuantity(2);
        item2.setGoods(goods2);
        item2.setQuantity(3);
        List<CartItem> cartList = Arrays.asList(item1,item2);
        float sum = orderService.getSumOfOrder(cartList);
        assertEquals(3420,sum,0.01);
    }
    @Test
    public void getOrderById(){
        Order order = this.order;
        when(orderRepository.getOrderById(anyInt())).thenReturn(orderEntity);
        Assert.assertEquals(orderService.getOrderById(order.getId()).getId(),order.getId());

    }

    public Order getOrder(){
        Client client1 = new Client();
        client1.setId(1);
        client1.setName("Alex");
        client1.setEmail("alex@mail.ru");
        client1.setPassword("123456");
        client1.setPhone("+79818829192");
        Role role = new Role();
        role.setId(3);
        role.setName("ROLE_CLIENT");
        client1.setRole(role);
        ClientAddress clientAddress = new ClientAddress();
        clientAddress.setId(1);
        client1.setClientAddress(clientAddress);

        Order order = new Order();
        order.setClient(client1);
        CartItem cartItem = new CartItem();
        Goods goods = new Goods();
        cartItem.setGoods(goods);
        cartItem.setOrder(order);
        Set<CartItem> set = new HashSet<>();
        set.add(cartItem);
        order.setCartItems(set);
        order.setId(1);
        return order;
    }

    public OrderEntity getOrderEntity(){
        OrderEntity orderEntity = new OrderEntity();
        PaymentTypeEntity paymentTypeEntity = new PaymentTypeEntity();
        paymentTypeEntity.setId(1);
        paymentTypeEntity.setName("card");
        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();
        deliveryMethodEntity.setId(1);
        deliveryMethodEntity.setName("pickup");
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(2);
        statusEntity.setName("closed");
        orderEntity.setId(1);
        orderEntity.setPayStatus(0);
        orderEntity.setDate("2017-11-11 16:04:54");
        orderEntity.setStatus(statusEntity);
        orderEntity.setDeliveryMethod(deliveryMethodEntity);
        orderEntity.setPaymentType(paymentTypeEntity);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setName("Alex");
        clientEntity.setPassword("123456");
        clientEntity.setEmail("alex@mail.ru");
        clientEntity.setPhone("+79818829192");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(3);
        roleEntity.setName("ROLE_CLIENT");
        clientEntity.setRoleEntity(roleEntity);
        ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
        clientAddressEntity.setId(1);
        clientEntity.setClientAddressEntity(clientAddressEntity);

        orderEntity.setClientEntity(clientEntity);

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(1);
        cartItemEntity.setOrderEntity(orderEntity);
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(1);
        goodsEntity.setName("uno");
        cartItemEntity.setGoodsEntity(goodsEntity);
        Set<CartItemEntity> set = new HashSet<>();
        set.add(cartItemEntity);
        orderEntity.setCartItemEntities(set);
        return orderEntity;
    }
}
