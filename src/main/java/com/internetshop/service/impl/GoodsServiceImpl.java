package com.internetshop.service.impl;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.RuleEntity;
import com.internetshop.model.Goods;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public List<GoodsEntity> getAllGoods() {
        return goodsRepository.getAll();
    }

    @Override
    public void addGoods(Goods goods) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(goods.getCategoryId());
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setId(goods.getRuleId());
        GoodsEntity goodsEntity = new GoodsEntity(goods.getName(),goods.getPrice(),goods.getNumberOfPlayers(),goods.getDuration(),goods.getAmount(), goods.getVisible(),goods.getDescription(),categoryEntity,ruleEntity);
        this.goodsRepository.addGoods(goodsEntity);
    }



}
