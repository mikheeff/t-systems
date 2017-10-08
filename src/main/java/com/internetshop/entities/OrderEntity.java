package com.internetshop.entities;

import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.PaymentType;
import com.internetshop.model.Status;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "order")
public class OrderEntity {
    @Id
    @Column(name = "idorder")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date date;
    @Column(name = "pay_status")
    private int payStatus;
    @Column(name = "prim")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentTypeEntity paymentType;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethodEntity deliveryMethod;

    OrderEntity(){

    }

    public OrderEntity(Date date, int payStatus, String comment, StatusEntity status, PaymentTypeEntity paymentType, DeliveryMethodEntity deliveryMethod) {
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

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public PaymentTypeEntity getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEntity paymentType) {
        this.paymentType = paymentType;
    }

    public DeliveryMethodEntity getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethodEntity deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
}
