package com.internetshop.repository.api;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.DeliveryMethodEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.PaymentTypeEntity;
import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.PaymentType;

import java.util.List;

public interface GoodsRepository {
    List<GoodsEntity> getAll();
    List<GoodsEntity> getAll(int firstId, int maxResults);
    List<GoodsEntity> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    void addGoods(GoodsEntity goodsEntity);
    void deleteGoodsById(int id);
    GoodsEntity getGoodsById(int id);
    void updateGoods(GoodsEntity goodsEntity);
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    int getRandomGoodsId();
    List<CategoryEntity> getAllCategories();
    int getIdCategoryByName(String name);
    void addCategory(CategoryEntity categoryEntity);
    CategoryEntity getCategoryById(int id);
    void updateCategory(CategoryEntity categoryEntity);
    void deleteCategoryById(int id);
    int getIdRuleByName(String name);



}
