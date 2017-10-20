package com.internetshop.model;


public class CartItem {
    private int id;
    private Goods goods;
    private int quantity;


    public CartItem() {
    }

    public CartItem(Goods goods, int quantity) {
        this.goods = goods;
        this.quantity = quantity;
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
}
