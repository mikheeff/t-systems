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

import javax.management.relation.Role;
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
        for (GoodsEntity goodsEntity:goodsRepository.getAll()) {
            goodsList.add(convertToDTO(goodsEntity));
        }
        return goodsList;
    }

    @Override
    public void addGoods(Goods goods) {
        this.goodsRepository.addGoods(convertToDAO(goods));
    }

    @Override
    public void deleteGoodsById(int id) {
        this.goodsRepository.deleteGoodsById(id);
    }

    @Override
    public Goods getGoodsById(int id) {
        Category category = new Category();
        category.setId(goodsRepository.getGoodsById(id).getCategory().getId());
        category.setName(goodsRepository.getGoodsById(id).getCategory().getName());
//        for (GoodsEntity ge : goodsRepository.getGoodsById(id).getCategory().getGoodsSet()) {
//            Goods goods = new goods
//            for(Goods g : category.getGoodsSet()){
//                g.
//            }
//        }
        Rule rule = new Rule();
        rule.setId(goodsRepository.getGoodsById(id).getRule().getId());
        rule.setName(goodsRepository.getGoodsById(id).getRule().getName());
        Goods goods = new Goods(goodsRepository.getGoodsById(id).getId(),goodsRepository.getGoodsById(id).getName(),goodsRepository.getGoodsById(id).getPrice(),goodsRepository.getGoodsById(id).getNumberOfPlayers(),goodsRepository.getGoodsById(id).getDuration(),goodsRepository.getGoodsById(id).getAmount(),goodsRepository.getGoodsById(id).getVisible(),goodsRepository.getGoodsById(id).getDescription(),goodsRepository.getGoodsById(id).getImg(),category,rule);
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
    public GoodsEntity convertToDAO(Goods goods) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(goods.getCategory().getId());
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setId(goods.getRule().getId());
        GoodsEntity goodsEntity = new GoodsEntity(goods.getName(),goods.getPrice(),goods.getNumberOfPlayers(),goods.getDuration(),goods.getAmount(), goods.getVisible(),goods.getDescription(),goods.getImg(),categoryEntity,ruleEntity);
        return goodsEntity;
    }

    @Override
    public Goods convertToDTO(GoodsEntity goodsEntity) {
        Category category = new Category(goodsEntity.getCategory().getId(),goodsEntity.getCategory().getName());
        Rule rule = new Rule(goodsEntity.getRule().getId(),goodsEntity.getRule().getName());
        Goods goods = new Goods(goodsEntity.getId(),goodsEntity.getName(),goodsEntity.getPrice(),goodsEntity.getNumberOfPlayers(),goodsEntity.getDuration(),goodsEntity.getAmount(),goodsEntity.getVisible(),goodsEntity.getDescription(),goodsEntity.getImg(),category,rule);
        return goods;
    }
}
