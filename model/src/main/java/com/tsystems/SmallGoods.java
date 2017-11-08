package com.tsystems;

import java.io.Serializable;

public class SmallGoods implements Serializable,Comparable{
    private int id;
    private String name;
    private float price;
    private String img;
    private int salesCounter;

    public SmallGoods(){

    }

    public SmallGoods(int id, String name, float price, String img, int salesCounter) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.salesCounter = salesCounter;
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

    public int getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(int salesCounter) {
        this.salesCounter = salesCounter;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
