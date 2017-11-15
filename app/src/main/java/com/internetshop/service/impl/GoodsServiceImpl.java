package com.internetshop.service.impl;

import com.internetshop.config.AppConfig;
import com.internetshop.entities.*;
import com.internetshop.jms.JmsProducer;
import com.internetshop.model.*;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.GoodsService;
import com.tsystems.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class.getName());
    private static JmsProducer producer = new JmsProducer(AppConfig.ACTIVE_MQ_URL);

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    /**
     * Gets list of all goods
     *
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

    @Override
    public List<SmallGoods> getAllSmallGoods() {
        List<SmallGoods> smallGoodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll()) {
            SmallGoods smallGoods = new SmallGoods();
            smallGoods.setId(goodsEntity.getId());
            smallGoods.setName(goodsEntity.getName());
            smallGoods.setPrice(goodsEntity.getPrice());
            smallGoods.setImg(goodsEntity.getImg());
            smallGoods.setSalesCounter(goodsEntity.getSalesCounter());
            smallGoodsList.add(smallGoods);
        }
        return smallGoodsList;
    }

    /**
     * Gets all goods that need for selected page
     * (pagination)
     *
     * @param firstId    number of the first goods on page
     * @param maxResults number of the last goods on page
     * @return goodsList
     */
    @Override
    public List<Goods> getAllGoods(int firstId, int maxResults) {
        logger.info("getAllGoods ");

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll(firstId, maxResults)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * Gets all goods by selected category (also with pagination)
     *
     * @param categoryName name of selected category
     * @return goods List
     */
    @Override
    public List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName) {
        logger.info("getAllGoodsByCategoryName");

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsByCategoryName(firstId, maxResults, categoryName)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    @Override
    public List<Goods> getAllGoodsBySearch(String searchStr, int firstId, int maxResults) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsBySearch(searchStr, firstId, maxResults)) {
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
        goods.setSalesCounter(0); //When employee adds new goods its counter is 0
        int idNewGoods = this.goodsRepository.addGoods(convertGoodsToDAO(goods));
        goods.setId(idNewGoods);
        createAddMessage(goods);

    }

    public void createAddMessage(Goods goods) {
        SmallGoods smallGoods = new SmallGoods();
        smallGoods.setId(goods.getId());
        smallGoods.setName(goods.getName());
        smallGoods.setPrice(goods.getPrice());
        smallGoods.setImg(goods.getImg());
        smallGoods.setSalesCounter(goods.getSalesCounter());

        Event event = new AddEvent(smallGoods);
        sendMessage(event);
    }

    /**
     * Deletes Selected goods
     */
    @Transactional
    @Override
    public void deleteGoodsById(int id) {
        logger.info("deleteGoodsById");
        this.goodsRepository.deleteGoodsById(id);
        createDeleteMessage(id);
    }

    public void createDeleteMessage(int id) {
        Event event = new DeleteEvent(id);
        sendMessage(event);
    }

    public void sendMessage(Event event){
        if (!producer.isAlive()) {
            producer.start();
        }
        producer.send(event); //close??
    }

    /**
     * Get goods by selected id
     *
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
                goodsEntity.getSalesCounter(),
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

        createUpdateMessage(goodsEntity);
    }

    public void createUpdateMessage(GoodsEntity goods) {
        SmallGoods smallGoods = new SmallGoods();
        smallGoods.setId(goods.getId());
        smallGoods.setName(goods.getName());
        smallGoods.setPrice(goods.getPrice());
        smallGoods.setImg(goods.getImg());
        smallGoods.setSalesCounter(goods.getSalesCounter());
        Event event = new UpdateEvent(smallGoods);
        sendMessage(event);
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
     *
     * @return amount
     */
    @Override
    public long getAmountOfGoods() {
        logger.info("getAmountOfGoods");

        return goodsRepository.getAmountOfGoods();
    }

    /**
     * Gets amount of goods which have selected category
     *
     * @return amount
     */
    @Override
    public long getAmountOfGoodsByCategoryName(String categoryName) {
        logger.info("getAmountOfGoodsByCategoryName");

        return goodsRepository.getAmountOfGoodsByCategoryName(categoryName);
    }

    @Override
    public long getAmountOfGoodsBySearch(String searchStr) {
        return goodsRepository.getAmountOfGoodsBySearch(searchStr);
    }

    /**
     * Gets list of all categories
     */
    @Override
    public List<Category> getAllCategories() {
        logger.info("getAllCategories");
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : goodsRepository.getAllCategories()) {
            Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
            categories.add(category);
        }
        return categories;
    }

    /**
     * Gets Category by selected id
     *
     * @return Category model
     */
    @Override
    public Category getCategoryById(int id) {
        logger.info("getCategoryById");
        CategoryEntity categoryEntity = goodsRepository.getCategoryById(id);
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
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
     *
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
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteCategoryById(int id) {
        logger.info("deleteCategoryById");
        goodsRepository.deleteCategoryById(id);
    }


    @Override
    public List<Goods> getBestSellers(int amountOfBestSellers) {
        List<Goods> goodsBestSellersList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getBestSellers(amountOfBestSellers)) {
            goodsBestSellersList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsBestSellersList;
    }

    @Override
    public List<Goods> getRelatedGoods(int amount, Goods goods) {
        List<Goods> relatedGoodsList = new ArrayList<>();

        for (GoodsEntity goodsEntity : goodsRepository.getRelatedGoodsByCategoryName(amount, goods.getCategory().getName())) {
            if (goodsEntity.getId() != goods.getId()) {
                relatedGoodsList.add(convertGoodsToDTO(goodsEntity));
            }
        }
        return relatedGoodsList;
    }

    /**
     * Gets selected amount of different random goods
     *
     * @return goods list
     */

    public List<Goods> getRandomGoods(int amountOfRandomGoodsOnPage) {
        Set<Integer> randomGoodsIdSet = new HashSet<>();
        while (randomGoodsIdSet.size() < amountOfRandomGoodsOnPage) {
            randomGoodsIdSet.add(getRandomGoodsId());
        }
        List<Goods> goodsList = new ArrayList<>();
        for (int id : randomGoodsIdSet) {
            goodsList.add(getGoodsById(id));
        }
        return goodsList;
    }

    /**
     * converts goods to data access object
     *
     * @return Goods Entity
     */
    @Override
    public GoodsEntity convertGoodsToDAO(Goods goods) {
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
     *
     * @return Goods model
     */
    @Override
    public Goods convertGoodsToDTO(GoodsEntity goodsEntity) {
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
                goodsEntity.getSalesCounter(),
                category,
                rule);
        return goods;
    }


}
