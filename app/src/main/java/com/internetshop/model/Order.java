package com.internetshop.model;

import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private int id;
    private String date;
    private int payStatus;
    private String comment;
    private Status status;
    private PaymentType paymentType;
    private DeliveryMethod deliveryMethod;
    private Set<CartItem> cartItems = new HashSet<>();
    private Client client;


    public Order(){

    }

    public Order(int id, String date, int payStatus, String comment, Status status, PaymentType paymentType, DeliveryMethod deliveryMethod, Set<CartItem> cartItems, Client client) {
        this.id = id;
        this.date = date;
        this.payStatus = payStatus;
        this.comment = comment;
        this.status = status;
        this.paymentType = paymentType;
        this.deliveryMethod = deliveryMethod;
        this.cartItems = cartItems;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItem) {
        this.cartItems = cartItem;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", payStatus=" + payStatus +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", paymentType=" + paymentType +
                ", deliveryMethod=" + deliveryMethod +
                ", cartItems=" + cartItems +
                ", client=" + client +
                '}';
    }
}
