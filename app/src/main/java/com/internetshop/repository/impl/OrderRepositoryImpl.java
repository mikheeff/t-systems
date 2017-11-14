package com.internetshop.repository.impl;

import com.internetshop.entities.*;
import com.internetshop.repository.api.OrderRepository;
import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Set;

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
    public List<StatusEntity> getAllStatuses() {
        return em.createQuery("select status from StatusEntity status", StatusEntity.class).getResultList();
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
        return em.createQuery("select deliveryMethodEntity.id from DeliveryMethodEntity deliveryMethodEntity where name = :name",Integer.class).setParameter("name",name).getSingleResult();
    }

    //
    @Override
    public PaymentTypeEntity getPaymentTypeByName(String name) {
        return em.createQuery("select paymentTypeEntity from PaymentTypeEntity paymentTypeEntity where name = :name",PaymentTypeEntity.class).setParameter("name",name).getSingleResult();

    }

    @Override
    public DeliveryMethodEntity getDeliveryMethodByName(String name) {
        return em.createQuery("select deliveryMethodEntity from DeliveryMethodEntity deliveryMethodEntity where name = :name",DeliveryMethodEntity.class).setParameter("name",name).getSingleResult();
    }

    @Override
    public StatusEntity getStatusByName(String name) {
        return em.createQuery("select statusEntity from StatusEntity statusEntity where name = :name",StatusEntity.class).setParameter("name",name).getSingleResult();
    }
///


    /**
     * Adds order to database
     * @param cartItemEntitySet set of all items in order
     * @return id of the added order
     */
    @Override
    public int addOrder(OrderEntity orderEntity, Set<CartItemEntity> cartItemEntitySet) {
        em.getTransaction().begin();
        em.persist(orderEntity);
        for (CartItemEntity item : cartItemEntitySet){
            item.setOrderEntity(orderEntity);
            em.persist(item);
        }
        em.getTransaction().commit();
        return em.createQuery("from OrderEntity orderEntity order by id DESC", OrderEntity.class).setMaxResults(1).getSingleResult().getId();
    }

    @Override
    public List<OrderEntity> getAllOrdersByClientId(int id) {
        return em.createQuery("select orderEntity from OrderEntity orderEntity where clientEntity.id = :id order by date desc ", OrderEntity.class).setParameter("id",id).getResultList();
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return em.createQuery("select orderEntity from OrderEntity orderEntity order by date DESC ", OrderEntity.class).getResultList();
    }

    @Override
    public OrderEntity getOrderById(int id) {
        return em.find(OrderEntity.class, id);
    }

    @Override
    public void updateOrder(OrderEntity orderEntity) {
        em.getTransaction().begin();
        em.merge(orderEntity);
        em.getTransaction().commit();
    }

    @Override
    public List<CartItemEntity> getAllCartItemsFromOrderByOrderId(int id) {
        return em.createQuery("select cartItemEntity from CartItemEntity cartItemEntity where orderEntity.id = :id", CartItemEntity.class).setParameter("id",id).getResultList();
    }



    //    @Override
//    public OrderEntity getOrderByClientId(int id) {
//        return em.createQuery("select orderEntity from OrderEntity orderEntity where clientEntity.id = :id", OrderEntity.class).setParameter("id",id).getSingleResult();
//    }
}
