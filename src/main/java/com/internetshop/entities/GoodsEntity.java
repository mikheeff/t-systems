package com.internetshop.entities;

import javax.persistence.*;

@Entity
@Table (name = "goods")
public class GoodsEntity {
    @Id
    @Column(name = "idgoods")
    @GeneratedValue
    private int id;
    @Column (name = "name")
    private String name;
    @Column (name = "price")
    private float price;
    @Column (name = "number_of_players")
    private int numberOfPlayers;
    @Column (name = "duration")
    private float duration;
    @Column (name = "rules")
    private String rules;
    @Column (name = "amount")
    private int amount;
    @Column (name = "visible")
    private int visible;
    @Column (name = "description")
    private String description;
    @Column (name = "id_category")
    private int id_category;

    public GoodsEntity() {
    }

    public GoodsEntity(String name, float price, int numberOfPlayers, float duration, String rules, int amount, int visible, String description, int id_category) {
        this.name = name;
        this.price = price;
        this.numberOfPlayers = numberOfPlayers;
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int nuberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
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
