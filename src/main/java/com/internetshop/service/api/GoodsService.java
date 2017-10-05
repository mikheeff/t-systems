package com.internetshop.service.api;

import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Goods;

import java.util.List;

public interface GoodsService {
    List<GoodsEntity> getAllGoods();
    void addGoods(Goods goods);
}
