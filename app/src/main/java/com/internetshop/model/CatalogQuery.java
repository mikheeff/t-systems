package com.internetshop.model;

public class CatalogQuery {
    private Integer numberOfPlayers;
    private Float duration;
    private Float price;
    private String rules;
    private String categoryName;
    private String sort;
    public CatalogQuery() {

    }
    public CatalogQuery(int numberOfPlayers, float duration, float price) {
        this.numberOfPlayers = numberOfPlayers;
        this.duration = duration;
        this.price = price;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
