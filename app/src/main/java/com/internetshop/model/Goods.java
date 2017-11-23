package com.internetshop.model;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Goods {
    private int id;
    @Size(max = 50,message = "Goods name must be less then 50 character long")
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotNull(message = "Price must not be empty")
    private float price;
    private int numberOfPlayers;
    private float duration;
    @NotNull(message = "Amount must not be empty")
    private int amount;
    private int visible;
    @Size(max = 5000,message = "Description must be less then 1000 character long")
    private String description;
    private String img;
    private int salesCounter;
    private int rating;
    private Category category;
    private Rule rule;


    public Goods() {
    }

    public Goods(int id, String name, float price, int numberOfPlayers, float duration, int amount, int visible, String description, String img, Category category, Rule rule) {
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

    public Goods(int id, String name, float price, int numberOfPlayers, float duration, int amount, int visible, String description, String img, int salesCounter, int rating, Category category, Rule rule) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfPlayers = numberOfPlayers;
        this.duration = duration;
        this.amount = amount;
        this.visible = visible;
        this.description = description;
        this.img = img;
        this.salesCounter = salesCounter;
        this.rating = rating;
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

    public int getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(int salesCounter) {
        this.salesCounter = salesCounter;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
