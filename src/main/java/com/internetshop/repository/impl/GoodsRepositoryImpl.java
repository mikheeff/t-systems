package com.internetshop.repository.impl;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.repository.api.GoodsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class GoodsRepositoryImpl implements GoodsRepository {

    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManager em = Persistence.createEntityManagerFactory("item-manager-pu").createEntityManager();



    public List<GoodsEntity> getAll() {
        return em.createQuery("select goods from GoodsEntity goods", GoodsEntity.class).getResultList();
    }

    public List<GoodsEntity> getAll(int firstId, int maxResults) {
        return em.createQuery("select goods from GoodsEntity goods", GoodsEntity.class).setFirstResult(firstId).setMaxResults(maxResults).getResultList();
    }


    @Override
    public List<GoodsEntity> getAllGoodsByCategoryName(int firstId, int maxResults, String categoryName) {
        return em
                .createQuery("select goods from GoodsEntity goods where category.name = :category", GoodsEntity.class)
                .setParameter("category", categoryName).setFirstResult(firstId).setMaxResults(maxResults).getResultList();
    }


    @Override
    public void addGoods(GoodsEntity goodsEntity) {
        em.getTransaction().begin();
        em.persist(goodsEntity);
        em.getTransaction().commit();
    }

    @Override
    public void deleteGoodsById(int id) {
        em.getTransaction().begin();
        em.remove(em.find(GoodsEntity.class, id));
        em.getTransaction().commit();
    }

    @Override
    public GoodsEntity getGoodsById(int id) {
        return em.find(GoodsEntity.class, id);
    }

    @Override
    public void updateGoods(GoodsEntity goodsEntity) {
        em.getTransaction().begin();
        em.merge(goodsEntity);
        em.getTransaction().commit();
    }

    @Override
    public long getAmountOfGoods() {
        return em.createQuery("select count(*) FROM GoodsEntity goods",Long.class).getSingleResult();
    }

    @Override
    public long getAmountOfGoodsByCategoryName(String categoryName) {
        return em.createQuery("select count(*) from GoodsEntity goods where category.name = :category",Long.class).setParameter("category", categoryName).getSingleResult();
    }

    @Override
    public int getRandomGoodsId() {
        return em.createQuery("select goods.id from GoodsEntity goods order by rand()",Integer.class).setMaxResults(1).getSingleResult();
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return em.createQuery("select categories from CategoryEntity categories", CategoryEntity.class).getResultList();

    }

    @Override
    public int getIdCategoryByName(String name)  {
        return em.createQuery("select categoryEntity.id from CategoryEntity categoryEntity where name = :name",Integer.class).setParameter("name",name).getSingleResult();
    }

    @Override
    public CategoryEntity getCategoryById(int id) {
        return em.find(CategoryEntity.class, id);
    }

    @Override
    public void updateCategory(CategoryEntity categoryEntity) {
        em.getTransaction().begin();
        em.merge(categoryEntity);
        em.getTransaction().commit();
    }

    @Override
    public void addCategory(CategoryEntity categoryEntity) {
        em.getTransaction().begin();
        em.persist(categoryEntity);
        em.getTransaction().commit();
    }


    @Override
    public int getIdRuleByName(String name) {
        return em.createQuery("select ruleEntity.id from RuleEntity ruleEntity where name = :name",Integer.class).setParameter("name",name).getSingleResult();
    }
}
