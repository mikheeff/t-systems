package com.internetshop.service.impl;

import com.internetshop.controller.GoodsController;
import com.internetshop.entities.*;
import com.internetshop.jms.JmsProducer;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.GoodsService;
import com.tsystems.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class.getName());
    private final JmsProducer producer;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository, ClientRepository clientRepository, ClientService clientService, JmsProducer jmsProducer) {
        this.goodsRepository = goodsRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.producer = jmsProducer;
    }

    /**
     * Gets list of all goods
     *
     * @return goodsList
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll()) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * loads all goods from database then creates small goods object
     * which contains short information about goods.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SmallGoods> getAllSmallGoods() {
        List<SmallGoods> smallGoodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAll()) {
            SmallGoods smallGoods = new SmallGoods();
            smallGoods.setId(goodsEntity.getId());
            smallGoods.setName(goodsEntity.getName());
            smallGoods.setPrice(goodsEntity.getPrice());
            smallGoods.setSalesCounter(goodsEntity.getSalesCounter());
            smallGoods.setVisible(goodsEntity.getVisible());
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
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getAllGoods(int firstId, int maxResults) {
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
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsByCategoryName(firstId, maxResults, categoryName)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * gets all goods by search line
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getAllGoodsBySearch(String searchStr, int firstId, int maxResults) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsBySearch(searchStr, firstId, maxResults)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * gets all goods by filter query
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getAllGoodsByFilter(catalogQuery, firstId, maxResults)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * gets list of best sellers by specified category
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getBestSellersByCategory(Category category, int amount) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getBestSellersByCategoryName(category.getName(), amount)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * gets list of reviews about specified goods
     * @param id goods id
     * @return list of reviews
     */
    @Transactional(readOnly = true)
    @Override
    public List<Review> getAllReviewsByGoodsId(int id) {
        List<Review> reviewList = new ArrayList<>();
        for (ReviewEntity reviewEntity : goodsRepository.getAllReviewsByGoodsId(id)) {
            reviewList.add(convertReviewToDTO(reviewEntity));
        }
        return reviewList;
    }

    /**
     * Adds new goods
     */
    @Transactional
    @Override
    public void addGoods(Goods goods) {
        logger.info("addGoods");
        goods.setSalesCounter(0); //When employee adds new goods its counter is 0
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        goods.setDate(format.format(date));
        int idNewGoods = this.goodsRepository.addGoods(convertGoodsToDAO(goods));
        goods.setId(idNewGoods);
        createAddMessage(goods);

    }

    /**
     * creates add message for second app
     * @param goods
     */
    public void createAddMessage(Goods goods) {
        SmallGoods smallGoods = new SmallGoods();
        smallGoods.setId(goods.getId());
        smallGoods.setName(goods.getName());
        smallGoods.setPrice(goods.getPrice());
        smallGoods.setSalesCounter(goods.getSalesCounter());
        smallGoods.setVisible(goods.getVisible());

        Event event = new AddEvent(smallGoods);
        sendMessage(event);
    }

    /**
     * add new review about goods
     */
    @Transactional
    @Override
    public void addReview(Review review) {
        logger.info("addReview {}",review);
        ReviewEntity reviewEntity = new ReviewEntity();

        ClientEntity clientEntity = clientRepository.getClientById(review.getClient().getId());
        reviewEntity.setClientEntity(clientEntity);

        GoodsEntity goodsEntity = goodsRepository.getGoodsById(review.getGoods().getId());
        reviewEntity.setGoodsEntity(goodsEntity);

        reviewEntity.setContent(review.getContent());
        reviewEntity.setRating(review.getRating());
        goodsRepository.addReview(reviewEntity);

        updateGoodsRating(reviewEntity);

    }

    /**
     * evaluates goods ration by counting all reviews marks
     * @param reviewEntity
     */
    @Transactional
    @Override
    public void updateGoodsRating(ReviewEntity reviewEntity) {
        GoodsEntity goodsEntity = reviewEntity.getGoodsEntity();
        goodsEntity.setRating(goodsRepository.getGoodsRating(goodsEntity.getId()));
        goodsRepository.updateGoods(goodsEntity);
    }

    /**
     * Deletes Selected goods
     */
    @Transactional
    @Override
    public void deleteGoodsById(int id) {
        logger.info("deleteGoodsById {}",id);
        this.goodsRepository.deleteGoodsById(id);
        createDeleteMessage(id);
    }

    public void createDeleteMessage(int id) {
        Event event = new DeleteEvent(id);
        sendMessage(event);
    }

    public void sendMessage(Event event) {
        if (!producer.isAlive()) {
            producer.start();
        }
        producer.send(event);
    }

    /**
     * Get goods by selected id
     *
     * @return Goods model
     */
    @Transactional(readOnly = true)
    @Override
    public Goods getGoodsById(int id) {
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
                goodsEntity.getSalesCounter(),
                goodsEntity.getRating(),
                category,
                rule,
                goodsEntity.getDate());
        return goods;
    }

    /**
     * Sets new information from Goods model to Entity
     */
    @Transactional
    @Override
    public void updateGoods(Goods goods) {
        logger.info("updateGoods {}", goods);

        GoodsEntity goodsEntity = this.goodsRepository.getGoodsById(goods.getId());
        goodsEntity.setName(goods.getName());
        goodsEntity.setPrice(goods.getPrice());
        goodsEntity.setNumberOfPlayers(goods.getNumberOfPlayers());
        goodsEntity.setDuration(goods.getDuration());
        goodsEntity.setAmount(goods.getAmount());
        goodsEntity.setVisible(goods.getVisible());
        goodsEntity.setDescription(goods.getDescription());

        RuleEntity ruleEntity = goodsRepository.getRuleByName(goods.getRule().getName());
        CategoryEntity categoryEntity = goodsRepository.getCategoryById(goodsRepository.getIdCategoryByName(goods.getCategory().getName()));

        goodsEntity.setRule(ruleEntity);
        goodsEntity.setCategory(categoryEntity);

        this.goodsRepository.updateGoods(goodsEntity);

        createUpdateMessage(goodsEntity);
    }

    /**
     * creates update message for second app
     */
    public void createUpdateMessage(GoodsEntity goods) {
        SmallGoods smallGoods = new SmallGoods();
        smallGoods.setId(goods.getId());
        smallGoods.setName(goods.getName());
        smallGoods.setPrice(goods.getPrice());
        smallGoods.setSalesCounter(goods.getSalesCounter());
        smallGoods.setVisible(goods.getVisible());
        Event event = new UpdateEvent(smallGoods);
        sendMessage(event);
    }


    /**
     * gets amount of goods in DataBase
     *
     * @return amount
     */
    @Transactional(readOnly = true)
    @Override
    public long getAmountOfGoods() {
        return goodsRepository.getAmountOfGoods();
    }

    /**
     * Gets amount of goods which have selected category
     *
     * @return amount
     */
    @Transactional(readOnly = true)
    @Override
    public long getAmountOfGoodsByCategoryName(String categoryName) {
        return goodsRepository.getAmountOfGoodsByCategoryName(categoryName);
    }

    /**
     * gets amount of goods by search line
     */
    @Transactional(readOnly = true)
    @Override
    public long getAmountOfGoodsBySearch(String searchStr) {
        return goodsRepository.getAmountOfGoodsBySearch(searchStr);
    }

    /**
     * gets amount of goods by filter query
     */
    @Transactional(readOnly = true)
    @Override
    public long getAmountOfGoodsByFilter(CatalogQuery catalogQuery) {
        return goodsRepository.getAmountOfGoodsByFilter(catalogQuery);
    }

    /**
     * Gets list of all categories
     */
    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories() {
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
    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(int id) {
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
        logger.info("addCategory {}",category);
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

    /**
     * gets list of best sellers
     * @param amountOfBestSellers amount of needed goods
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getBestSellers(int amountOfBestSellers) {
        List<Goods> goodsBestSellersList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getBestSellers(amountOfBestSellers)) {
            goodsBestSellersList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsBestSellersList;
    }

    /**
     * gets best sellers list from the same category as specified goods
     * ordered by popularity
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Goods> getRandomGoods(int amountOfRandomGoodsOnPage) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getRandomGoods(amountOfRandomGoodsOnPage)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * checks if client had already left a review
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isAvailableToLeaveReview(Review review) {
        return goodsRepository.isAvailableToLeaveReview(review.getClient().getId(), review.getGoods().getId());
    }

    /**
     * gets top new goods
     * @param amountOfNewGoodsOnPage needed amount of goods
     */
    @Transactional(readOnly = true)
    @Override
    public List<Goods> getNewGoods(int amountOfNewGoodsOnPage) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsRepository.getNewGoods(amountOfNewGoodsOnPage)) {
            goodsList.add(convertGoodsToDTO(goodsEntity));
        }
        return goodsList;
    }

    /**
     * evaluates a place of goods if goods are ordered by rating
     */
    @Transactional(readOnly = true)
    @Override
    public long getPlaceOfGoods(int id) {
        return goodsRepository.getPlaceOfGoods(goodsRepository.getGoodsById(id).getRating());
    }

    /**
     * adds new goods image
     */
    @Transactional
    @Override
    public void addGoodsImage(GoodsImage goodsImage) {
        logger.info("addGoodsImage");
        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        GoodsEntity goodsEntity = goodsRepository.getGoodsById(goodsImage.getGoods().getId());
        goodsImageEntity.setGoodsEntity(goodsEntity);
        goodsImageEntity.setImg(goodsImage.getImg());
        goodsRepository.addGoodsImage(goodsImageEntity);
        createUpdateMessage(goodsEntity);
    }

    /**
     * returns list of all images of goods
     */
    @Transactional(readOnly = true)
    @Override
    public List<GoodsImage> getAllImagesByGoodsId(int id) {
        List<GoodsImage> goodsImageList = new ArrayList<>();
        for (GoodsImageEntity img : goodsRepository.getAllImagesByGoodsId(id)){
            GoodsImage goodsImage = new GoodsImage(img.getId(),img.getImg(),convertGoodsToDTO(img.getGoodsEntity()));
            goodsImage.setImgBase64(img.getImgBase64());
            goodsImageList.add(goodsImage);
        }
        return goodsImageList;
    }

    /**
     * delete image of goods by entered id
     */
    @Transactional
    @Override
    public void deleteImageById(int id) {
        logger.info("deleteImageById", id);
        GoodsEntity goodsEntity = goodsRepository.getImageById(id).getGoodsEntity();
        createUpdateMessage(goodsEntity);
        goodsRepository.deleteImageById(id);
    }

    /**
     * checks does cart contain goods
     */
    @Override
    public boolean isCartContainsGoods(List<CartItem> cartList, int id) {
        for (CartItem item : cartList) {
            if (item.getGoods().getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks is any goods in database have specified category
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isAnyGoodsConnectedWithCategory(int id) {
        return goodsRepository.isAnyGoodsConnectedWithCategory(id);
    }

    /**
     * puts default objects to model map
     */
    @Override
    public void putDefaultAttributes(ModelMap modelMap) {
        modelMap.put("listCategory", getAllCategories());
        modelMap.put("randomGoods", getRandomGoods(GoodsController.amountOfRandomGoodsOnPage));
        modelMap.put("bestSellersList", getBestSellers(GoodsController.amountOfBestSellers));
    }



    /**
     * converts goods to data access object
     *
     * @return Goods Entity
     */
    @Override
    public GoodsEntity convertGoodsToDAO(Goods goods) {

        CategoryEntity categoryEntity = goodsRepository.getCategoryByName(goods.getCategory().getName());

        RuleEntity ruleEntity = goodsRepository.getRuleByName(goods.getRule().getName());

        GoodsEntity goodsEntity = new GoodsEntity(
                goods.getName(),
                goods.getPrice(),
                goods.getNumberOfPlayers(),
                goods.getDuration(),
                goods.getAmount(),
                goods.getVisible(),
                goods.getDescription(),
                categoryEntity,
                ruleEntity);

        goodsEntity.setDate(goods.getDate());

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
                goodsEntity.getSalesCounter(),
                goodsEntity.getRating(),
                category,
                rule,
                goodsEntity.getDate());
        return goods;
    }

    /**
     * converts review entity to data access object
     */
    Review convertReviewToDTO(ReviewEntity reviewEntity) {
        Review review = new Review();
        review.setId(reviewEntity.getId());
        Client client = clientService.getClientById(reviewEntity.getClientEntity().getId());
        review.setClient(client);
        Goods goods = getGoodsById(reviewEntity.getGoodsEntity().getId());
        review.setGoods(goods);
        review.setContent(reviewEntity.getContent());
        review.setRating(reviewEntity.getRating());
        return review;
    }


}
