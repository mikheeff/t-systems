package com.internetshop.service.impl;

import com.internetshop.model.CartItem;
import com.internetshop.model.Goods;
import com.internetshop.service.api.OrderService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class OrderServiceTest {

    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        this.orderService = mock(OrderService.class);

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
}
