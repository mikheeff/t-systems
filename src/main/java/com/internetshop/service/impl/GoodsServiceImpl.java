package com.internetshop.service.impl;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.*;
import com.internetshop.model.*;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class.getName());

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    /**
     * Gets list of all goods
     * @return goodsList
     */
    @Override
    public List<Goods> getAllGoods() {
        logger.info("getAllGoods without pagination");
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll()) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * Gets all goods that need for selected page
     * (pagination)
     * @param firstId number of the first goods on page
     * @param maxResults number of the last goods on page
     * @return goodsList
     */
    @Override
    public List<Goods> getAllGoods(int firstId, int maxResults) {
        logger.info("getAllGoods ");

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll(firstId,maxResults)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * Gets all goods by selected category (also with pagination)
     * @param categoryName name of selected category
     * @return goods List
     */
    @Override
    public List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName) {
        logger.info("getAllGoodsByCategoryName");

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsByCategoryName(firstId,maxResults,categoryName)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * Adds new goods
     */
    @Transactional
    @Override
    public void addGoods(Goods goods) {
        logger.info("addGoods");
        this.goodsRepository.addGoods(convertGoodsToDAO(goods));
    }

    /**
     *  Deletes Selected goods
     */
    @Transactional
    @Override
    public void deleteGoodsById(int id) {
        logger.info("deleteGoodsById");
        this.goodsRepository.deleteGoodsById(id);
    }

    /**
     * Get goods by selected id
     * @return Goods model
     */
    @Override
    public Goods getGoodsById(int id) {
        logger.info("getGoodsById");
        GoodsEntity goodsEntity = goodsRepository.getGoodsById(id);

        Category category = new Category();
        category.setId(goodsEntity.getCategory().getId());
        category.setName(goodsEntity.getCategory().getName());

        Rule rule = new Rule();
        rule.setId(goodsEntity.getRule().getId());
        rule.setName(goodsEntity.getRule().getName());

        Goods goods = new Goods(
                goodsEntity.getId(),
                goodsEntity.getName(),
                goodsEntity.getPrice(),
                goodsEntity.getNumberOfPlayers(),
                goodsEntity.getDuration(),
                goodsEntity.getAmount(),
                goodsEntity.getVisible(),
                goodsEntity.getDescription(),
                goodsEntity.getImg(),
                category,
                rule);
        return goods;
    }

    /**
     * Sets new information from Goods model to Entity
     */
    @Transactional
    @Override
    public void updateGoods(Goods goods) {
        logger.info("updateGoods");

        GoodsEntity goodsEntity = this.goodsRepository.getGoodsById(goods.getId());
        goodsEntity.setName(goods.getName());
        goodsEntity.setPrice(goods.getPrice());
        goodsEntity.setNumberOfPlayers(goods.getNumberOfPlayers());
        goodsEntity.setDuration(goods.getDuration());
        goodsEntity.setAmount(goods.getAmount());
        goodsEntity.setVisible(goods.getVisible());
        goodsEntity.setDescription(goods.getDescription());
        goodsEntity.setImg(goods.getImg());

        RuleEntity ruleEntity = goodsRepository.getRuleByName(goods.getRule().getName());
        CategoryEntity categoryEntity = goodsRepository.getCategoryById(goodsRepository.getIdCategoryByName(goods.getCategory().getName()));

        goodsEntity.setRule(ruleEntity);
        goodsEntity.setCategory(categoryEntity);

        this.goodsRepository.updateGoods(goodsEntity);
    }

    /**
     * Gets one random goods id
     */
    @Override
    public int getRandomGoodsId() {
        logger.info("getRandomGoodsId");
        return goodsRepository.getRandomGoodsId();
    }

    /**
     * gets amount of goods in DataBase
     * @return amount
     */
    @Override
    public long getAmountOfGoods() {
        logger.info("getAmountOfGoods");

        return goodsRepository.getAmountOfGoods();
    }

    /**
     * Gets amount of goods which have selected category
     * @return amount
     */
    @Override
    public long getAmountOfGoodsByCategoryName(String categoryName) {
        logger.info("getAmountOfGoodsByCategoryName");

        return goodsRepository.getAmountOfGoodsByCategoryName(categoryName);
    }

    /**
     * Gets list of all categories
     */
    @Override
    public List<Category> getAllCategories() {
        logger.info("getAllCategories");
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity  : goodsRepository.getAllCategories()) {
            Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
            categories.add(category);
        }
        return categories;
    }

    /**
     * Gets Category by selected id
     * @return Category model
     */
    @Override
    public Category getCategoryById(int id) {
        logger.info("getCategoryById");
        CategoryEntity categoryEntity = goodsRepository.getCategoryById(id);
        Category category = new Category(categoryEntity.getId(),categoryEntity.getName());
        return category;
    }

    /**
     * Adds new category
     */
    @Transactional
    @Override
    public void addCategory(Category category) {
        logger.info("addCategory");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(category.getName());
        goodsRepository.addCategory(categoryEntity);
    }

    /**
     * Updates information of category
     * @param category model
     */
    @Transactional
    @Override
    public void updateCategory(Category category) {
        logger.info("updateCategory");
        CategoryEntity categoryEntity = goodsRepository.getCategoryById(category.getId());
        categoryEntity.setName(category.getName());
        goodsRepository.updateCategory(categoryEntity);
    }

    /**
     * Deletes category with selected id
     * @param id
     */
    @Transactional
    @Override
    public void deleteCategoryById(int id) {
        logger.info("deleteCategoryById");
        goodsRepository.deleteCategoryById(id);
    }


    /**
     * converts goods to data access object
     * @return Goods Entity
     */
    @Override
    public GoodsEntity convertGoodsToDAO(Goods goods) {
        logger.info("convertGoodsToDAO");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(goodsRepository.getIdCategoryByName(goods.getCategory().getName()));
        categoryEntity.setName(goods.getName());

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setId(goodsRepository.getIdRuleByName(goods.getRule().getName()));
        ruleEntity.setName(goods.getName());

        GoodsEntity goodsEntity = new GoodsEntity(
                goods.getName(),
                goods.getPrice(),
                goods.getNumberOfPlayers(),
                goods.getDuration(),
                goods.getAmount(),
                goods.getVisible(),
                goods.getDescription(),
                goods.getImg(),
                categoryEntity,
                ruleEntity);
        return goodsEntity;
    }

    /**
     * Converts goods to Data Tranfer Object
     * @return Goods model
     */
    @Override
    public Goods convertGoodsToDTO(GoodsEntity goodsEntity) {
        logger.info("convertGoodsToDTO");
        Category category = new Category(goodsEntity.getCategory().getId(), goodsEntity.getCategory().getName());
        Rule rule = new Rule(goodsEntity.getRule().getId(), goodsEntity.getRule().getName());
        Goods goods = new Goods(
                goodsEntity.getId(),
                goodsEntity.getName(),
                goodsEntity.getPrice(),
                goodsEntity.getNumberOfPlayers(),
                goodsEntity.getDuration(),
                goodsEntity.getAmount(),
                goodsEntity.getVisible(),
                goodsEntity.getDescription(),
                goodsEntity.getImg(),
                category,
                rule);
        return goods;
    }


}
