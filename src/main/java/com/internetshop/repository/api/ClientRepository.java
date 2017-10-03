package com.internetshop.repository.api;

import com.internetshop.entities.ClientEntity;
import com.internetshop.model.Client;

import java.util.List;

public interface ClientRepository {
    List<ClientEntity> getAll();
}
