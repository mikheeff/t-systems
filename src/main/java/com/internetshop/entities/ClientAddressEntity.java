package com.internetshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "clientAddress")
public class ClientAddressEntity {
    @Id
    @Column (name = "id_client")
    private int id;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "post_code")
    private int postcode;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;
    @Column(name = "flat")
    private String flat;
    @Column(name = "prim")
    private String addition;

    @OneToOne(mappedBy="clientAddressEntity", cascade = CascadeType.ALL,  fetch = FetchType.LAZY, orphanRemoval = true)
    private ClientEntity clientEntity;
    public ClientAddressEntity(){

    }
    public ClientAddressEntity(int id, String country, String city, int postcode, String street, String house, String flat, String addition) {
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

//    public ClientEntity getClientEntity() {
//        return clientEntity;
//    }
//
//    public void setClientEntity(ClientEntity clientEntity) {
//        this.clientEntity = clientEntity;
//    }
}
