package com.internetshop.service.impl;

import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.Role;
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

    @Override
    public void addClient(Client client) {
        this.clientRepository.addClient(convertClientToDAO(client));
    }

    public ClientEntity convertClientToDAO(Client client) {
        RoleEntity role = new RoleEntity();
        role.setId(3);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setRoleEntity(role);
        return clientEntity;
    }
}
