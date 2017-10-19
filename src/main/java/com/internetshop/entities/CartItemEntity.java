package com.internetshop.entities;

import com.internetshop.model.Client;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "delivery_included") //todo в ордер
    private int deliveryIncluded;
    @ManyToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goodsEntity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    public CartItemEntity(){

    }

    public CartItemEntity(int quantity, int deliveryIncluded, GoodsEntity goodsEntity, OrderEntity orderEntity) {
        this.quantity = quantity;
        this.deliveryIncluded = deliveryIncluded;
        this.goodsEntity = goodsEntity;
        this.orderEntity = orderEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDeliveryIncluded() {
        return deliveryIncluded;
    }

    public void setDeliveryIncluded(int deliveryIncluded) {
        this.deliveryIncluded = deliveryIncluded;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
