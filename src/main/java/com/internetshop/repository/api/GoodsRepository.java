package com.internetshop.repository.api;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;

import java.util.List;

public interface GoodsRepository {
    List<GoodsEntity> getAll();
    List<GoodsEntity> getAll(int firstId, int maxResults);
    List<GoodsEntity> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    void addGoods(GoodsEntity goodsEntity);
    void deleteGoodsById(int id);
    GoodsEntity getGoodsById(int id);
    void updateGoods(GoodsEntity goodsEntity);
    int getAmountOfGoods();
    int getAmountOfGoodsByCategoryName(String categoryName);
    List<CategoryEntity> getAllCategories();

}
