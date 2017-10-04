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
    private EntityManagerFactory emf;  //конструктор?


    public List<ClientEntity> getAll() {
        emf = Persistence.createEntityManagerFactory("item-manager-pu");
        return emf.createEntityManager().createQuery("select c from ClientEntity c ", ClientEntity.class).getResultList();
    }
}
