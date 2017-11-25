package com.internetshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class ReviewEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "rating")
    private int rating;
    @ManyToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goodsEntity;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    public ReviewEntity(){

    }

    public ReviewEntity(String content, int rating, GoodsEntity goodsEntity, ClientEntity clientEntity) {
        this.content = content;
        this.rating = rating;
        this.goodsEntity = goodsEntity;
        this.clientEntity = clientEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }
}
