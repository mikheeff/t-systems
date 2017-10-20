package com.internetshop.model;


public class CartItem {
    private int id;
    private Goods goods;
    private int quantity;
    private Order order;

    public CartItem() {
    }

    public CartItem(int id, Goods goods, int quantity, Order order) {
        this.id = id;
        this.goods = goods;
        this.quantity = quantity;
        this.order = order;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
