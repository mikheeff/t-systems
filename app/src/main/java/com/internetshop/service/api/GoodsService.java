package com.internetshop.service.api;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.entities.ReviewEntity;
import com.internetshop.model.*;
import com.tsystems.SmallGoods;
import org.springframework.ui.ModelMap;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();
    List<SmallGoods> getAllSmallGoods();
    List<Goods> getAllGoods(int firstId,int maxResults);
    List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName);
    List<Goods> getAllGoodsBySearch(String searchStr,int firstId, int maxResults);
    List<Goods> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults);
    List<Goods> getBestSellers(int amountOfBestSellers);
    List<Goods> getRelatedGoods(int amount,Goods goods);
    List<Goods> getRandomGoods(int amountOfRandomGoodsOnPage);
    List<Goods> getNewGoods(int amountOfNewGoodsOnPage);
    List<Goods> getBestSellersByCategory(Category category, int amount);
    List<Review> getAllReviewsByGoodsId(int id);
    List<GoodsImage> getAllImagesByGoodsId(int id);

    void addGoods(Goods goods);
    void addReview(Review review);
    void deleteGoodsById(int id);
    Goods getGoodsById(int id);
    void updateGoods(Goods goods);
    GoodsEntity convertGoodsToDAO(Goods goods);
    Goods convertGoodsToDTO(GoodsEntity goodsEntity);
    long getAmountOfGoods();
    long getAmountOfGoodsByCategoryName(String categoryName);
    long getAmountOfGoodsBySearch(String searchStr);
    long getAmountOfGoodsByFilter(CatalogQuery catalogQuery);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void updateCategory(Category category);
    void addCategory(Category category);
    void deleteCategoryById(int id);
    void createUpdateMessage(GoodsEntity goodsEntity);
    boolean isCartContainsGoods(List<CartItem> cartList, int id);
    void putDefaultAttributes(ModelMap modelMap);
    void updateGoodsRating(ReviewEntity reviewEntity);
    long getPlaceOfGoods(int id);
    boolean isAvailableToLeaveReview(Review review);
    void addGoodsImage(GoodsImage goodsImage);
    void deleteImageById(int id);



}
