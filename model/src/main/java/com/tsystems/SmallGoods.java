package com.tsystems;

import java.io.Serializable;

public class SmallGoods implements Serializable,Comparable{
    private int id;
    private String name;
    private float price;
    private int salesCounter;
    private int visible;

    public SmallGoods(){

    }

    public SmallGoods(int id, String name, float price, int salesCounter, int visible) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.salesCounter = salesCounter;
        this.visible = visible;
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

    public int getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(int salesCounter) {
        this.salesCounter = salesCounter;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
