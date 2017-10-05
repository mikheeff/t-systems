package com.internetshop.model;

import com.internetshop.entities.GoodsEntity;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private int id;
    private String name;
    private Set<Goods> GoodsSet = new HashSet<>();

    public Category(int id, String name, Set<Goods> goodsSet) {
        this.id = id;
        this.name = name;
        GoodsSet = goodsSet;
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

    public Set<Goods> getGoodsSet() {
        return GoodsSet;
    }

    public void setGoodsSet(Set<Goods> goodsSet) {
        GoodsSet = goodsSet;
    }
}
