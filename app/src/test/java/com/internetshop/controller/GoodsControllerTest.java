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
        HttpSession session = mock(HttpSession.class);
        OrderController orderController = mock(OrderController.class);
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



}
