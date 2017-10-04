package com.internetshop.repository.impl;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.repository.api.GoodsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;
@Repository
public class GoodsRepositoryImpl implements GoodsRepository {

    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManagerFactory emf;

    public List<GoodsEntity> getAll() {
        emf = Persistence.createEntityManagerFactory("item-manager-pu");
//        return emf.createEntityManager().createQuery("select goods.id,goods.name,goods.price,goods.nuberOfPlayaers,goods.duration,goods.rules,goods.amount,goods.visible,cat.name,goods.description from GoodsEntity goods,CategoryEntity cat where goods.id_category=cat.id", GoodsEntity.class).getResultList();
        return emf.createEntityManager().createQuery("select goods from GoodsEntity goods",GoodsEntity.class).getResultList();
    }
}
