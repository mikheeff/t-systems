package com.internetshop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`order`")
public class OrderEntity {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "pay_status")
    private int payStatus;
    @Column(name = "prim")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private ClientEntity clientEntity;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentTypeEntity paymentType;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethodEntity deliveryMethod;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    public OrderEntity(){

    }

    public OrderEntity(String date, int payStatus, String comment, ClientEntity clientEntity, StatusEntity status, PaymentTypeEntity paymentType, DeliveryMethodEntity deliveryMethod, Set<CartItemEntity> cartItemEntities) {
        this.date = date;
        this.payStatus = payStatus;
        this.comment = comment;
        this.clientEntity = clientEntity;
        this.status = status;
        this.paymentType = paymentType;
        this.deliveryMethod = deliveryMethod;
        this.cartItemEntities = cartItemEntities;
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


    public Set<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }

    public void setCartItemEntities(Set<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }
}
