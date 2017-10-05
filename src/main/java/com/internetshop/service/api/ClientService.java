package com.internetshop.service.api;

import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.GoodsEntity;

import java.util.List;

public interface ClientService {
    List<ClientEntity> getAllClients();
}
