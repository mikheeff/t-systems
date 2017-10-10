package com.internetshop.model;


public class Goods {
    private int id;
    private String name;
    private float price;
    private String numberOfPlayers;
    private float duration;
    private int amount;
    private int visible;
    private String description;
    private String img;
    private Category category;
    private Rule rule;

    public Goods() {
    }

    public Goods(int id, String name, float price, String numberOfPlayers, float duration, int amount, int visible, String description, String img, Category category, Rule rule) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfPlayers = numberOfPlayers;
        this.duration = duration;
        this.amount = amount;
        this.visible = visible;
        this.description = description;
        this.img = img;
        this.category = category;
        this.rule = rule;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
