package com.tsystems;

import java.io.Serializable;

public class SmallGoods implements Serializable,Comparable{
    private int id;
    private String name;
    private float price;
    private String img;

    public SmallGoods(){

    }

    public SmallGoods(int id, String name, float price, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
