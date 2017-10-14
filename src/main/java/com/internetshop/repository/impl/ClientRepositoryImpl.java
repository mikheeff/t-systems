package com.internetshop.repository.impl;

import com.internetshop.entities.ClientEntity;
import com.internetshop.model.Client;
import com.internetshop.repository.api.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManager em = Persistence.createEntityManagerFactory("item-manager-pu").createEntityManager();

    public List<ClientEntity> getAll() {
        return em.createQuery("select c from ClientEntity c ", ClientEntity.class).getResultList();
    }

    @Override
    public void addClient(ClientEntity clientEntity) {
        em.getTransaction().begin();
        em.persist(clientEntity);
        em.getTransaction().commit();
    }

    @Override
    public ClientEntity getUserByEmail(String email) {
        return em.createQuery("select client from ClientEntity client where email = :email", ClientEntity.class).setParameter("email",email).getSingleResult();
    }
}
