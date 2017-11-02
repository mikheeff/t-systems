package com.internetshop.controller;

import com.internetshop.model.CartItem;
import com.internetshop.model.Goods;
import com.internetshop.model.Order;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.internetshop.service.api.OrderService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.longThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

public class GoodsControllerTest {

    private ClientService clientService;
    private OrderService orderService;
    private GoodsController goodsController;
    private int amountOfGoodsOnPage;
    @Before
    public void setUp() throws Exception {
        GoodsService goodsService = mock(GoodsService.class);
        ClientService clientService = mock(ClientService.class);
        OrderService orderService = mock(OrderService.class);
        HttpSession session = mock(HttpSession.class);
        goodsController = new GoodsController(goodsService,clientService,orderService,session);

        amountOfGoodsOnPage = 9;
    }
    @Test
    public void getAmountOfPagesSetNumber42(){
        assertEquals(5,goodsController.getAmountOfPages(42));
    }
    @Test
    public void getAmountOfPagesSetZero(){
        assertEquals(1,goodsController.getAmountOfPages(0));
    }
    @Test
    public void getAmountOfPagesSetNumber1(){
        assertEquals(1,goodsController.getAmountOfPages(1));
    }
    @Test
    public void getAmountOfPagesSetNumberConst(){
        assertEquals(1,goodsController.getAmountOfPages(amountOfGoodsOnPage));
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
        float sum = goodsController.getSumOfOrder(cartList);
        assertEquals(3420,sum,0.01);
    }
    @Test
    public void getSumOfOrderEmptyCart(){
        List<CartItem> cartList = new ArrayList<>();
        float sum = goodsController.getSumOfOrder(cartList);
        assertEquals(0,sum,0.01);
    }

}
