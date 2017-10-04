package com.internetshop.model;

public class Goods {
    private int id;
    private String name;
    private float price;
    private int numberOfPlayaers;
    private float duration;
    private String rules;
    private int amount;
    private int visible;
    private String description;
    private int id_category;

    public Goods() {
    }

    public Goods(int id, String name, float price, int numberOfPlayaers, float duration, String rules, int amount, int visible, String description, int id_category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfPlayaers = numberOfPlayaers;
        this.duration = duration;
        this.rules = rules;
        this.amount = amount;
        this.visible = visible;
        this.description = description;
        this.id_category = id_category;
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

    public int getNumberOfPlayaers() {
        return numberOfPlayaers;
    }

    public void setNuberOfPlayaers(int nuberOfPlayaers) {
        this.numberOfPlayaers = nuberOfPlayaers;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
