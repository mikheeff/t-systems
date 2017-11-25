package com.internetshop.model;

import java.io.Serializable;

public class Review implements Serializable {
    private int id;
    private String content;
    private int rating;
    private Goods goods;
    private Client client;

    public Review(){

    }

    public Review(int id, String content, int rating, Goods goods, Client client) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.goods = goods;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
