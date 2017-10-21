package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.ClientAddress;
import com.internetshop.model.PasswordField;
import com.internetshop.model.Role;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<ClientEntity> getAllClients(){ return clientRepository.getAll(); }

    @Override
    public void addClient(Client client) throws EmailExistException {

        if (clientRepository.isEmailExist(client.getEmail())){
            throw new EmailExistException();
        }


        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));

        ClientEntity clientEntity = convertClientToDAO(client);
        Set<OrderEntity> orderEntitySet = new HashSet<>();
        clientEntity.setOrderEntities(orderEntitySet);
        this.clientRepository.addClient(clientEntity);

    }

    @Override
    public void addAddress() {

    }

    @Override
    public void changePassword(PasswordField passwordField, Client client) throws PasswordWrongException {

        if(bCryptPasswordEncoder.matches(passwordField.getPassword(),client.getPassword())==true){
            ClientEntity clientEntity = clientRepository.getUserById(client.getId());
            clientEntity.setPassword(bCryptPasswordEncoder.encode(passwordField.getNewPasswordFirst()));
            clientRepository.updateUser(clientEntity);
        } else {
            throw new PasswordWrongException();
        }
    }

    @Override
    public Client getUserByEmail(String email) {
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        Role role = new Role(clientEntity.getRoleEntity().getId(),clientEntity.getRoleEntity().getName());

//        if (clientEntity.getClientAddressEntity() == null) {                        //если адрес юзера ещё не проинициализирован
//            ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
//            clientAddressEntity.setId(clientEntity.getId());
//            clientEntity.setClientAddressEntity(clientAddressEntity);
//        }

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
    public Client getClientById(int id) {
        Client client = new Client();
        ClientEntity clientEntity = clientRepository.getUserById(id);
        client.setId(clientEntity.getId());
        client.setName(clientEntity.getName());
        client.setBirthdate(clientEntity.getBirthdate());
        client.setEmail(clientEntity.getEmail());
        client.setPassword(clientEntity.getPassword());
        client.setPhone(clientEntity.getPhone());
        client.setOrderCounter(clientEntity.getOrderCounter());

        Role role = new Role();
        role.setId(clientEntity.getId());
        role.setName(clientEntity.getName());
        client.setRole(role);

        ClientAddress clientAddress = new ClientAddress();
        clientAddress.setId(clientEntity.getClientAddressEntity().getId());
        clientAddress.setCountry(clientEntity.getClientAddressEntity().getCountry());
        clientAddress.setCity(clientEntity.getClientAddressEntity().getCity());
        clientAddress.setPostcode(clientEntity.getClientAddressEntity().getPostcode());
        clientAddress.setStreet(clientEntity.getClientAddressEntity().getStreet());
        clientAddress.setHouse(clientEntity.getClientAddressEntity().getHouse());
        clientAddress.setFlat(clientEntity.getClientAddressEntity().getFlat());
        clientAddress.setAddition(clientEntity.getClientAddressEntity().getAddition());
        client.setClientAddress(clientAddress);


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
        role.setName(clientRepository.getRoleById(3).getName());
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setRoleEntity(role);
        return clientEntity;
    }


}
