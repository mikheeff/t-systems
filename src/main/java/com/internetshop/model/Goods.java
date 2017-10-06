package com.internetshop.model;


public class Goods {
    private int id;
    private String name;
    private float price;
    private int numberOfPlayers;
    private float duration;
    private int ruleId;
    private int amount;
    private int visible;
    private String description;
    private int categoryId;

    public Goods() {
    }

    public Goods(int id, String name, float price, int numberOfPlayers, float duration, int ruleId, int amount, int visible, String description, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfPlayers = numberOfPlayers;
        this.duration = duration;
        this.ruleId = ruleId;
        this.amount = amount;
        this.visible = visible;
        this.description = description;
        this.categoryId = categoryId;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
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

}
