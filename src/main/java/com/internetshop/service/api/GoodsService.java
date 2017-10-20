package com.internetshop.service.api;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.model.Category;
import com.internetshop.model.DeliveryMethod;
import com.internetshop.model.Goods;
import com.internetshop.model.PaymentType;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();
    List<Goods> getAllGoods(int firstId,int maxResults);
    List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    void addGoods(Goods goods);
    void deleteGoodsById(int id);
    Goods getGoodsById(int id);
    void updateGoods(Goods goods);
    GoodsEntity convertGoodsToDAO(Goods goods);
    Goods convertGoodsToDTO(GoodsEntity goodsEntity);
    int getRandomGoodsId();
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void updateCategory(Category category);
    void addCategory(Category category);
    void deleteCategoryById(int id);
    OrderEntity getOrderById(int id);


}
