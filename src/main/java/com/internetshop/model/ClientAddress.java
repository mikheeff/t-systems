package com.internetshop.model;

public class ClientAddress {
    private int id;
    private String country;
    private String city;
    private int postcode;
    private String street;
    private String house;
    private String flat;
    private String addition;

    public ClientAddress(){

    }
    public ClientAddress(int id, String country, String city, int postcode, String street, String house, String flat, String addition) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.addition = addition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
