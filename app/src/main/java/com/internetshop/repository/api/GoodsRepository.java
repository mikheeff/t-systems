package com.internetshop.repository.api;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.*;
import com.internetshop.model.CatalogQuery;
import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.PaymentType;

import java.util.List;

public interface GoodsRepository {
    List<GoodsEntity> getAll();
    List<GoodsEntity> getAll(int firstId, int maxResults);
    List<GoodsEntity> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    List<GoodsEntity> getAllGoodsBySearch(String searchStr,int firstId, int maxResults);
    List<GoodsEntity> getRelatedGoodsByCategoryName(int amount,String categoryName);
    List<GoodsEntity> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults);

    int addGoods(GoodsEntity goodsEntity);
    void deleteGoodsById(int id);
    GoodsEntity getGoodsById(int id);
    void updateGoods(GoodsEntity goodsEntity);
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    long getAmountOfGoodsBySearch(String searchStr);
    long getAmountOfGoodsByFilter(CatalogQuery catalogQuery);
    int getRandomGoodsId();
    List<CategoryEntity> getAllCategories();
    int getIdCategoryByName(String name);
    void addCategory(CategoryEntity categoryEntity);
    CategoryEntity getCategoryById(int id);
    void updateCategory(CategoryEntity categoryEntity);
    void deleteCategoryById(int id);
    int getIdRuleByName(String name);
    RuleEntity getRuleByName(String name);
    List<GoodsEntity> getBestSellers(int amountOfBestSellers);

}
