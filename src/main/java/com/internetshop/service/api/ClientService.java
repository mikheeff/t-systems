package com.internetshop.service.api;

import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;
import com.internetshop.model.Client;

import java.util.List;

public interface ClientService {
    List<ClientEntity> getAllClients();
    void addClient(Client client);
}
