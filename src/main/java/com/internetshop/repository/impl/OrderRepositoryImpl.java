package com.internetshop.repository.impl;

import com.internetshop.entities.DeliveryMethodEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.entities.PaymentTypeEntity;
import com.internetshop.entities.StatusEntity;
import com.internetshop.repository.api.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManager em = Persistence.createEntityManagerFactory("item-manager-pu").createEntityManager();

    @Override
    public List<DeliveryMethodEntity> getAllDeliveryMethods() {
        return em.createQuery("select methods from DeliveryMethodEntity methods", DeliveryMethodEntity.class).getResultList();
    }

    @Override
    public List<PaymentTypeEntity> getAllPaymentTypes() {
        return em.createQuery("select types from PaymentTypeEntity types", PaymentTypeEntity.class).getResultList();
    }

    @Override
    public StatusEntity getStatusById(int id) {
        return em.find(StatusEntity.class, id);
    }

    @Override
    public int getIdPaymentTypeByName(String name) {
        return em.createQuery("select paymentTypeEntity.id from PaymentTypeEntity paymentTypeEntity where name = :name",Integer.class).setParameter("name",name).getSingleResult();
    }

    @Override
    public int getIdDeliveryMethodByName(String name) {
        return em.createQuery("select DeliveryMethodEntity.id from DeliveryMethodEntity deliveryMethodEntity where name = :name",Integer.class).setParameter("name",name).getSingleResult();
    }

    @Override
    public void addOrder(OrderEntity orderEntity) {
        em.getTransaction().begin();
        em.persist(orderEntity);
        em.getTransaction().commit();
    }
}
