package com.internetshop.service.api;

import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Category;
import com.internetshop.model.Goods;

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
    Goods getRandomGoods();
    int getAmountOfGoods();
    int getAmountOfGoodsByCategoryName(String categoryName);
    List<Category> getAllCategories();
}
