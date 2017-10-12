package com.internetshop.service.impl;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.RuleEntity;
import com.internetshop.model.Category;
import com.internetshop.model.Goods;
import com.internetshop.model.Rule;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll()) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }
    @Override
    public List<Goods> getAllGoods(int firstId, int maxResults) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll(firstId,maxResults)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    @Override
    public List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsByCategoryName(firstId,maxResults,categoryName)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    @Override
    public void addGoods(Goods goods) {
        this.goodsRepository.addGoods(convertGoodsToDAO(goods));
    }

    @Override
    public void deleteGoodsById(int id) {
        this.goodsRepository.deleteGoodsById(id);
    }

    @Override
    public Goods getGoodsById(int id) {
        GoodsEntity goodsEntity = goodsRepository.getGoodsById(id);

        Category category = new Category();
        category.setId(goodsEntity.getCategory().getId());
        category.setName(goodsEntity.getCategory().getName());

        Rule rule = new Rule();
        rule.setId(goodsEntity.getRule().getId());
        rule.setName(goodsEntity.getRule().getName());

        Goods goods = new Goods(goodsEntity.getId(), goodsEntity.getName(), goodsEntity.getPrice(), goodsEntity.getNumberOfPlayers(), goodsEntity.getDuration(), goodsEntity.getAmount(), goodsEntity.getVisible(), goodsEntity.getDescription(), goodsEntity.getImg(), category, rule);
        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {
        GoodsEntity goodsEntity = this.goodsRepository.getGoodsById(goods.getId());
        goodsEntity.setName(goods.getName());
        goodsEntity.setPrice(goods.getPrice());
        goodsEntity.setNumberOfPlayers(goods.getNumberOfPlayers());
        goodsEntity.setDuration(goods.getDuration());
        goodsEntity.setAmount(goods.getAmount());
        goodsEntity.setVisible(goods.getVisible());
        goodsEntity.setDescription(goods.getDescription());

        RuleEntity ruleEntity = goodsEntity.getRule();
        ruleEntity.setId(goods.getRule().getId());

        CategoryEntity categoryEntity = goodsEntity.getCategory();
        categoryEntity.setId(goods.getCategory().getId());
        this.goodsRepository.updateGoods(goodsEntity);
    }

    @Override
    public Goods getRandomGoods() {
        List<Integer> allIdList = new ArrayList<>();
        for (Goods g : getAllGoods()) {
            allIdList.add(g.getId());
        }
        return getGoodsById(allIdList.get((int) (Math.random() * allIdList.size())));
    }

    @Override
    public int getAmountOfGoods() {
        return goodsRepository.getAmountOfGoods();
    }

    @Override
    public int getAmountOfGoodsByCategoryName(String categoryName) {
        return goodsRepository.getAmountOfGoodsByCategoryName(categoryName);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity  : goodsRepository.getAllCategories()) {
            Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
            categories.add(category);
        }
        return categories;
    }

    @Override
    public GoodsEntity convertGoodsToDAO(Goods goods) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(goods.getCategory().getId());
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setId(goods.getRule().getId());
        GoodsEntity goodsEntity = new GoodsEntity(goods.getName(), goods.getPrice(), goods.getNumberOfPlayers(), goods.getDuration(), goods.getAmount(), goods.getVisible(), goods.getDescription(), goods.getImg(), categoryEntity, ruleEntity);
        return goodsEntity;
    }

    @Override
    public Goods convertGoodsToDTO(GoodsEntity goodsEntity) {
        Category category = new Category(goodsEntity.getCategory().getId(), goodsEntity.getCategory().getName());
        Rule rule = new Rule(goodsEntity.getRule().getId(), goodsEntity.getRule().getName());
        Goods goods = new Goods(goodsEntity.getId(), goodsEntity.getName(), goodsEntity.getPrice(), goodsEntity.getNumberOfPlayers(), goodsEntity.getDuration(), goodsEntity.getAmount(), goodsEntity.getVisible(), goodsEntity.getDescription(), goodsEntity.getImg(), category, rule);
        return goods;
    }

}
