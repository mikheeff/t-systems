package com.internetshop.model;

import com.internetshop.service.api.ClientService;
import com.internetshop.service.impl.ClientServiceImpl;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class GoodsImage {
    private int id;
    private byte[] img;
    private Goods goods;
    private String imgBase64;

    public GoodsImage(){

    }

    public GoodsImage(int id, byte[] img, Goods goods) {
        this.id = id;
        this.img = img;
        this.goods = goods;
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

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getImgBase64() {
        return this.imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}
