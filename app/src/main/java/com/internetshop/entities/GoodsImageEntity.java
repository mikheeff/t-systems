package com.internetshop.entities;

import com.internetshop.service.api.ClientService;

import javax.persistence.*;

@Entity
@Table(name = "goods_img")
public class GoodsImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "img")
    private byte[] img;
    @ManyToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goodsEntity;


    public GoodsImageEntity(){

    }
    public GoodsImageEntity(byte[] img, GoodsEntity goodsEntity) {
        this.img = img;
        this.goodsEntity = goodsEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }

    public String getImgBase64() {
        return ClientService.convertImgToBase64(img);
    }

}
