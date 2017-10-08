package com.internetshop.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Order {
    private int id;
    private Date date;
    private int payStatus;
    private String comment;
    private Status status;
    private PaymentType paymentType;
    private DeliveryMethod deliveryMethod;

    Order(){

    }

    public Order(int id, Date date, int payStatus, String comment, Status status, PaymentType paymentType, DeliveryMethod deliveryMethod) {
        this.id = id;
        this.date = date;
        this.payStatus = payStatus;
        this.comment = comment;
        this.status = status;
        this.paymentType = paymentType;
        this.deliveryMethod = deliveryMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
