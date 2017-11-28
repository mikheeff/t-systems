package com.internetshop.repository.api;

import com.internetshop.entities.*;
import com.internetshop.model.CatalogQuery;
import com.internetshop.model.GoodsImage;

import java.util.List;

public interface GoodsRepository {
    List<GoodsEntity> getAll();
    List<GoodsEntity> getAll(int firstId, int maxResults);
    List<GoodsEntity> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    List<GoodsEntity> getAllGoodsBySearch(String searchStr,int firstId, int maxResults);
    List<GoodsEntity> getRelatedGoodsByCategoryName(int amount,String categoryName);
    List<GoodsEntity> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults);
    List<GoodsEntity> getBestSellers(int amountOfBestSellers);
    List<GoodsEntity> getRandomGoods(int amountOfRandomGoodsOnPage);
    List<GoodsEntity> getNewGoods(int amountOfNewGoodsOnPage);
    List<GoodsEntity> getBestSellersByCategoryName(String name, int amount);
    List<ReviewEntity> getAllReviewsByGoodsId(int id);
    List<GoodsImageEntity> getAllImagesByGoodsId(int id);
    GoodsImageEntity getImageById(int id);

    int addGoods(GoodsEntity goodsEntity);
    void addReview(ReviewEntity reviewEntity);
    void deleteGoodsById(int id);
    GoodsEntity getGoodsById(int id);
    void updateGoods(GoodsEntity goodsEntity);
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    long getAmountOfGoodsBySearch(String searchStr);
    long getAmountOfGoodsByFilter(CatalogQuery catalogQuery);
    List<CategoryEntity> getAllCategories();
    int getIdCategoryByName(String name);
    CategoryEntity getCategoryByName(String name);
    void addCategory(CategoryEntity categoryEntity);
    CategoryEntity getCategoryById(int id);
    void updateCategory(CategoryEntity categoryEntity);
    void deleteCategoryById(int id);
    int getIdRuleByName(String name);
    RuleEntity getRuleByName(String name);
    double getGoodsRating(int id);
    long getPlaceOfGoods(double rating);
    boolean isAvailableToLeaveReview(int clientId, int goodsId);
    void addGoodsImage(GoodsImageEntity goodsImageEntity);
    void deleteImageById(int id);

}
