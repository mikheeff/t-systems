package com.internetshop.repository.impl;

import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.repository.api.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext
    private EntityManager em;

    public List<ClientEntity> getAll() {
        return em.createQuery("select c from ClientEntity c ", ClientEntity.class).getResultList();
    }

    @Override
    public List<ClientEntity> getBestClientsList(int amountOfBestClients) {
        return em.createQuery("select client from ClientEntity client order by client.orderCounter desc",ClientEntity.class).setMaxResults(amountOfBestClients).getResultList();
    }

    @Override
    public void addClient(ClientEntity clientEntity) {

        ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
        em.persist(clientAddressEntity);
        clientEntity.setClientAddressEntity(clientAddressEntity);
        em.persist(clientEntity);
    }

    @Override
    public void addAddress(ClientAddressEntity clientAddressEntity) {
        em.persist(clientAddressEntity);
    }

    @Override
    public ClientEntity getUserByEmail(String email) {
        return em.createQuery("select client from ClientEntity client where email = :email", ClientEntity.class).setParameter("email",email).getSingleResult();
    }

    @Override
    public ClientEntity getUserById(int id) {
        return em.find(ClientEntity.class, id);
    }

    @Override
    public String getEmailByConfirmationId(String id) {
        return em.createQuery("select client.email from ClientEntity client where confirmationId = :id", String.class).setParameter("id",id).getSingleResult();
    }

    @Override
    public void updateUser(ClientEntity clientEntity) {
        em.merge(clientEntity);
    }

    @Override
    public boolean isEmailExist(String email) {
       return em.createQuery("select count(*) from ClientEntity client where client.email = :email",Long.class).setParameter("email",email).getSingleResult()>0;
    }

    @Override
    public RoleEntity getRoleById(int id) {
        return em.createQuery("select roleEntity from RoleEntity roleEntity where id = :id", RoleEntity.class).setParameter("id",id).getSingleResult();
    }

    @Override
    public ClientAddressEntity getAddressById(int id) {
        return em.createQuery("select clientAddressEntity from ClientAddressEntity clientAddressEntity where id = :id", ClientAddressEntity.class).setParameter("id",id).getSingleResult();
    }
}
