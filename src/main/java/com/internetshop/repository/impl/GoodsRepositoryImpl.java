package com.internetshop.repository.impl;

import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.repository.api.GoodsRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
@Repository
public class GoodsRepositoryImpl implements GoodsRepository {

    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManagerFactory emf;


    public List<GoodsEntity> getAll() {
        emf = Persistence.createEntityManagerFactory("item-manager-pu");
        return emf.createEntityManager().createQuery("select goods from GoodsEntity goods",GoodsEntity.class).getResultList();
    }

    @Override
    public void addGoods(GoodsEntity goodsEntity) {
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(goodsEntity);
        em.getTransaction().commit();
    }

}