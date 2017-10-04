package com.internetshop.service.impl;

import com.internetshop.entities.ClientEntity;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientEntity> getAllClients(){ return clientRepository.getAll(); }
}
