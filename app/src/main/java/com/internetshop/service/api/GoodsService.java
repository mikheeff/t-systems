package com.internetshop.service.api;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.model.*;
import com.tsystems.SmallGoods;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();
    List<SmallGoods> getAllSmallGoods();
    List<Goods> getAllGoods(int firstId,int maxResults);
    List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    List<Goods> getAllGoodsBySearch(String searchStr,int firstId, int maxResults);
    List<Goods> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults);
    void addGoods(Goods goods);
    void deleteGoodsById(int id);
    Goods getGoodsById(int id);
    void updateGoods(Goods goods);
    GoodsEntity convertGoodsToDAO(Goods goods);
    Goods convertGoodsToDTO(GoodsEntity goodsEntity);
    int getRandomGoodsId();
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    long getAmountOfGoodsBySearch(String searchStr);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void updateCategory(Category category);
    void addCategory(Category category);
    void deleteCategoryById(int id);
    List<Goods> getBestSellers(int amountOfBestSellers);
    List<Goods> getRelatedGoods(int amount,Goods goods);
    void createUpdateMessage(GoodsEntity goodsEntity);
    List<Goods> getRandomGoods(int amountOfRandomGoodsOnPage);


}
