package com.internetshop.entities;

import com.internetshop.model.CartItem;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goods")
public class GoodsEntity {
    @Id
    @Column(name = "idgoods")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "number_of_players")
    private int numberOfPlayers;
    @Column(name = "duration")
    private float duration;;
    @Column(name = "amount")
    private int amount;
    @Column(name = "visible")
    private int visible;
    @Column(name = "description")
    private String description;
    @Column (name = "img")
    private String img;
    @Column (name = "sales_counter")
    private int salesCounter;
    @Column (name = "rating")
    private double rating;
    @Column (name = "add_time")
    private String date;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "rule_id")
    private RuleEntity rule;



    public GoodsEntity() {
    }

    public GoodsEntity(String name, float price, int numberOfPlayers, float duration, int amount, int visible, String description, String img, CategoryEntity category, RuleEntity rule) {
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

    public GoodsEntity(String name, float price, int numberOfPlayers, float duration, int amount, int visible, String description, String img, int salesCounter, double rating, String date, CategoryEntity category, RuleEntity rule) {
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
        this.date = date;
        this.category = category;
        this.rule = rule;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
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

    public RuleEntity getRule() {
        return rule;
    }

    public void setRule(RuleEntity rule) {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
