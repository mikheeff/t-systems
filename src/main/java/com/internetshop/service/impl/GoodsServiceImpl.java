package com.internetshop.service.impl;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Goods;
import com.internetshop.repository.api.GoodsRepository;
import com.internetshop.service.api.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        // TODO: 05.10.2017 model -> entity
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(goods.getCategoryId());
        GoodsEntity goodsEntity = new GoodsEntity(goods.getName(),goods.getPrice(),goods.getNumberOfPlayers(),goods.getDuration(),goods.getRules(),goods.getAmount(), goods.getVisible(),goods.getDescription(),categoryEntity);
        goodsEntity.setId(goods.getId());
        this.goodsRepository.addGoods(goodsEntity);
    }



}
