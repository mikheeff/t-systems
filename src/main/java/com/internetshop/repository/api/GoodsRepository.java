package com.internetshop.repository.api;

import com.internetshop.entities.GoodsEntity;

import java.util.List;

public interface GoodsRepository {
    List<GoodsEntity> getAll();
}
