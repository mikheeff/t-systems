package com.internetshop.service.impl;

import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.ClientAddress;
import com.internetshop.model.Role;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));

        this.clientRepository.addClient(convertClientToDAO(client));

//        ClientEntity clientEntity = clientRepository.getUserByEmail(client.getEmail());
//        clientAddressEntity.setId(clientEntity.getId());
//        clientEntity.setClientAddressEntity(clientAddressEntity);
//        clientAddressEntity.setClientEntity(clientEntity);
//        clientRepository.addAddress(clientAddressEntity);
//        clientRepository.updateUser(clientEntity);
    }

    @Override
    public void addAddress() {
    }

    @Override
    public Client getUserByEmail(String email) {
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        Role role = new Role(clientEntity.getRoleEntity().getId(),clientEntity.getRoleEntity().getName());

        if (clientEntity.getClientAddressEntity() == null) {                        //если адрес юзера ещё не проинициализирован
            ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
            clientAddressEntity.setId(clientEntity.getId());
            clientEntity.setClientAddressEntity(clientAddressEntity);
        }

        ClientAddress clientAddress = new ClientAddress(
                clientEntity.getClientAddressEntity().getId(),
                clientEntity.getClientAddressEntity().getCountry(),
                clientEntity.getClientAddressEntity().getCity(),
                clientEntity.getClientAddressEntity().getPostcode(),
                clientEntity.getClientAddressEntity().getStreet(),
                clientEntity.getClientAddressEntity().getHouse(),
                clientEntity.getClientAddressEntity().getFlat(),
                clientEntity.getClientAddressEntity().getAddition());

        Client client = new Client(
                clientEntity.getId(),
                clientEntity.getName(),
                clientEntity.getSurname(),
                clientEntity.getBirthdate(),
                clientEntity.getEmail(),
                clientEntity.getPassword(),
                clientEntity.getPhone(),
                clientEntity.getOrderCounter(),
                role,
                clientAddress);

        return client;
    }

    @Override
    public void updateUser(Client client) {
        ClientEntity clientEntity = clientRepository.getUserById(client.getId());

//        RoleEntity roleEntity = clientEntity.getRoleEntity();
//        roleEntity.setId(client.getRole().getId());
//        roleEntity.setName(client.getRole().getName());
//
        ClientAddressEntity clientAddressEntity = clientEntity.getClientAddressEntity();
//        clientAddressEntity.setId(client.getClientAddress().getId());
        clientAddressEntity.setCountry(client.getClientAddress().getCountry());
        clientAddressEntity.setCity(client.getClientAddress().getCity());
        clientAddressEntity.setPostcode(client.getClientAddress().getPostcode());
        clientAddressEntity.setStreet(client.getClientAddress().getStreet());
        clientAddressEntity.setHouse(client.getClientAddress().getHouse());
        clientAddressEntity.setFlat(client.getClientAddress().getFlat());
        clientAddressEntity.setAddition(client.getClientAddress().getAddition());
        if(client.getName()!=null) {
            clientEntity.setName(client.getName());
        }
        clientEntity.setSurname(client.getSurname());
//        clientEntity.setBirthdate(client.getBirthdate());  //todo как ввести дату??
        if(client.getEmail()!=null){
        clientEntity.setEmail(client.getEmail());
        }
        if(client.getPassword()!=null) {
            clientEntity.setPassword(client.getPassword());
        }
        if(client.getPhone()!=null) {
            clientEntity.setPhone(client.getPhone());
        }

        clientRepository.updateUser(clientEntity);
    }

    public ClientEntity convertClientToDAO(Client client) {
        RoleEntity role = new RoleEntity();
        role.setId(3);                                  // по умолчанию ставим роль юзера - 3(client)

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setRoleEntity(role);
        return clientEntity;
    }


}
