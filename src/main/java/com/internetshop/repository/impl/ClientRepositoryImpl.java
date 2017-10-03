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

    //private List<ClientEntity> clients = Arrays.asList(new ClientEntity("Vasya","Ivanov","01.01.1992","vasya@mail.ru","vasya1992"));
    @PersistenceUnit(unitName = "item-manager-pu")
    private EntityManagerFactory emf;
    @PersistenceContext(unitName = "item-manager-pu")
    private EntityManager em;


    public List<ClientEntity> getAll() {
        emf = Persistence.createEntityManagerFactory("item-manager-pu");
        return emf.createEntityManager().createQuery("select c from ClientEntity c ", ClientEntity.class).getResultList();
    }
}
