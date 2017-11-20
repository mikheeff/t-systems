package com.internetshop.repository.impl;

import com.internetshop.entities.*;
import com.internetshop.repository.api.OrderRepository;
import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

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


    /**
     * Adds order to database
     * @param cartItemEntitySet set of all items in order
     * @return id of the added order
     */
    @Override
    public int addOrder(OrderEntity orderEntity, Set<CartItemEntity> cartItemEntitySet) {
        em.persist(orderEntity);
        for (CartItemEntity item : cartItemEntitySet){
            item.setOrderEntity(orderEntity);
            em.persist(item);
        }
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
        em.merge(orderEntity);
    }

    @Override
    public List<CartItemEntity> getAllCartItemsFromOrderByOrderId(int id) {
        return em.createQuery("select cartItemEntity from CartItemEntity cartItemEntity where orderEntity.id = :id", CartItemEntity.class).setParameter("id",id).getResultList();
    }


    @Override
    public long getAmountOfClosedOrdersByClientId(int id) {
        return em.createQuery("select count(*) from OrderEntity orderM where status.name = :status and clientEntity.id = :id", Long.class).setParameter("status","closed").setParameter("id",id).getSingleResult();
    }
    public List<Integer> getAllClosedOrdersIdsByDayOfMonth(int day){
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(calendar.getTimeZone());

        calendar.roll(Calendar.DAY_OF_MONTH,(-1)*(calendar.get(Calendar.DAY_OF_MONTH)-day));
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        String dateStart = format.format(calendar.getTime());

        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        String dateEnd = format.format(calendar.getTime());

        return em.createQuery("select orderM.id from OrderEntity orderM where status.name = :status and date between :dateStart and :dateEnd",Integer.class).setParameter("status","closed").setParameter("dateStart",dateStart).setParameter("dateEnd",dateEnd).getResultList();
    }
}
