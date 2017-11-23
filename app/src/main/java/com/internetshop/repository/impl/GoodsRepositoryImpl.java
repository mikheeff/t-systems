package com.internetshop.repository.impl;

import com.internetshop.Exceptions.NoSuchCategoryException;
import com.internetshop.Exceptions.NoSuchRulesException;
import com.internetshop.entities.CategoryEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.RuleEntity;
import com.internetshop.model.CatalogQuery;
import com.internetshop.repository.api.GoodsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GoodsRepositoryImpl implements GoodsRepository {


    @PersistenceContext
    private EntityManager em;


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
    public List<GoodsEntity> getRelatedGoodsByCategoryName(int amount, String categoryName) {
        return em
                .createQuery("select goods from GoodsEntity goods where category.name = :category order by goods.salesCounter DESC", GoodsEntity.class)
                .setParameter("category", categoryName).setMaxResults(amount).getResultList();
    }

    @Override
    public List<GoodsEntity> getAllGoodsBySearch(String searchStr, int firstId, int maxResults) {
        return em
                .createQuery("select goods from GoodsEntity goods where goods.name LIKE CONCAT('%', :searchStr, '%')", GoodsEntity.class)
                .setParameter("searchStr", searchStr).setFirstResult(firstId).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<GoodsEntity> getAllGoodsByFilter(CatalogQuery catalogQuery, int firstId, int maxResults) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<GoodsEntity> criteria = builder.createQuery(GoodsEntity.class);
        Root<GoodsEntity> goodsRoot = criteria.from(GoodsEntity.class);

        criteria = buildFilterQueryString(catalogQuery, builder, criteria, goodsRoot);
        if (catalogQuery.getSort() != null) {
            if (catalogQuery.getSort().equals("PRICE")) {
                criteria.orderBy(builder.asc(goodsRoot.get("price")));
            }
            if (catalogQuery.getSort().equals("ALPHABET")) {
                criteria.orderBy(builder.asc(goodsRoot.get("name")));
            }
            if (catalogQuery.getSort().equals("RATING")) {
                criteria.orderBy(builder.asc(goodsRoot.get("price")));
            }
            if (catalogQuery.getSort().equals("DATE")) {
                criteria.orderBy(builder.asc(goodsRoot.get("price")));
            }
        }
        List<GoodsEntity> list = em.createQuery(criteria).setFirstResult(firstId).setMaxResults(maxResults).getResultList();
        return list;
    }

    @Override
    public long getAmountOfGoodsByFilter(CatalogQuery catalogQuery) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<GoodsEntity> goodsRoot = criteria.from(GoodsEntity.class);
        criteria = buildFilterQueryString(catalogQuery, builder, criteria, goodsRoot);
        return em.createQuery(criteria.select(builder.count(goodsRoot))).getSingleResult();
    }

    private CriteriaQuery buildFilterQueryString(CatalogQuery catalogQuery, CriteriaBuilder builder, CriteriaQuery criteria, Root<GoodsEntity> goodsRoot) {
        criteria.select(goodsRoot);
        if (catalogQuery.getNumberOfPlayers() != null) {
            criteria.where(builder.lessThanOrEqualTo(goodsRoot.get("numberOfPlayers"), catalogQuery.getNumberOfPlayers()));
        }
        if (catalogQuery.getDuration() != null) {
            criteria.where(builder.lessThanOrEqualTo(goodsRoot.get("duration"), catalogQuery.getDuration()));
        }
        if (catalogQuery.getPrice() != null) {
            if (catalogQuery.getPrice() <= 3000) {
                criteria.where(builder.lessThanOrEqualTo(goodsRoot.get("price"), catalogQuery.getPrice()));
            } else {
                criteria.where(builder.greaterThanOrEqualTo(goodsRoot.get("price"), catalogQuery.getPrice()));
            }
        }
        if (catalogQuery.getRules() != null) {
            criteria.where(builder.equal(goodsRoot.get("rule").get("name"), catalogQuery.getRules()));
        }

        return criteria;
    }

    @Override
    public int addGoods(GoodsEntity goodsEntity) {
        em.persist(goodsEntity);
        return em.createQuery("from GoodsEntity goodsEntity order by id DESC", GoodsEntity.class).setMaxResults(1).getSingleResult().getId();
    }

    @Override
    public void deleteGoodsById(int id) {
        em.remove(em.find(GoodsEntity.class, id));
    }

    @Override
    public GoodsEntity getGoodsById(int id) {
        return em.find(GoodsEntity.class, id);
    }

    @Override
    public void updateGoods(GoodsEntity goodsEntity) {
        em.merge(goodsEntity);
    }

    @Override
    public long getAmountOfGoods() {
        return em.createQuery("select count(*) FROM GoodsEntity goods", Long.class).getSingleResult();
    }

    @Override
    public long getAmountOfGoodsByCategoryName(String categoryName) {
        return em.createQuery("select count(*) from GoodsEntity goods where category.name = :category", Long.class).setParameter("category", categoryName).getSingleResult();
    }

    @Override
    public long getAmountOfGoodsBySearch(String searchStr) {
        return em
                .createQuery("select count(*) from GoodsEntity goods where goods.name LIKE CONCAT('%', :searchStr, '%')", Long.class)
                .setParameter("searchStr", searchStr).getSingleResult();
    }

    @Override
    public int getRandomGoodsId() {
        return em.createQuery("select goods.id from GoodsEntity goods order by rand()", Integer.class).setMaxResults(1).getSingleResult();
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return em.createQuery("select categories from CategoryEntity categories", CategoryEntity.class).getResultList();

    }

    @Override
    public int getIdCategoryByName(String name) {
        return em.createQuery("select categoryEntity.id from CategoryEntity categoryEntity where name = :name", Integer.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public CategoryEntity getCategoryById(int id) {
        return em.find(CategoryEntity.class, id);
    }

    @Override
    public void updateCategory(CategoryEntity categoryEntity) {
        em.merge(categoryEntity);
    }

    @Override
    public void addCategory(CategoryEntity categoryEntity) {
        em.persist(categoryEntity);
    }

    @Override
    public void deleteCategoryById(int id) {
        em.remove(em.find(CategoryEntity.class, id));
    }

    @Override
    public int getIdRuleByName(String name) {
        return em.createQuery("select ruleEntity.id from RuleEntity ruleEntity where name = :name", Integer.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public RuleEntity getRuleByName(String name) {
        return em.createQuery("select ruleEntity from RuleEntity ruleEntity where name = :name", RuleEntity.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<GoodsEntity> getBestSellers(int amountOfBestSellers) {
        return em.createQuery("select goods from GoodsEntity goods order by goods.salesCounter desc", GoodsEntity.class).setMaxResults(amountOfBestSellers).getResultList();
    }
}
