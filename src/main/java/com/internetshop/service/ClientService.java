package com.internetshop.service;

import com.internetshop.entities.ClientEntity;
import com.internetshop.repository.api.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository; // конструктор?

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientEntity> getAllClients(){ return clientRepository.getAll(); }
}
