package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.controller.HomeController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;
    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class.getName());


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ClientEntity> getAllClients(){ return clientRepository.getAll(); }

    /**
     * Adds new client
     * @param client new client model
     * @throws EmailExistException email already exists
     */
    @Override
    public void addClient(Client client) throws EmailExistException {
        logger.info("addClient");
        if (clientRepository.isEmailExist(client.getEmail())){
            throw new EmailExistException(client.getEmail());
        }


        client.setPassword(passwordEncoder.encode(client.getPassword()));

        ClientEntity clientEntity = convertClientToDAO(client);
        Set<OrderEntity> orderEntitySet = new HashSet<>();
        clientEntity.setOrderEntities(orderEntitySet);
        this.clientRepository.addClient(clientEntity);

    }

    /**
     * Changes user's password
     * @throws PasswordWrongException
     */
    @Override
    public void changePassword(PasswordField passwordField, Client client) throws PasswordWrongException {
        logger.info("changePassword");
        if(passwordEncoder.matches(passwordField.getPassword(),client.getPassword())){
            logger.info("password matches = true");
            ClientEntity clientEntity = clientRepository.getUserById(client.getId());
            clientEntity.setPassword(passwordEncoder.encode(passwordField.getNewPasswordFirst()));
            clientRepository.updateUser(clientEntity);
        } else {
            throw new PasswordWrongException();
        }
    }

    /**
     * Gets client model by email
     * @return Client
     */
    @Override
    public Client getUserByEmail(String email) {
        logger.info("getUserByEmail");
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        Role role = new Role(clientEntity.getRoleEntity().getId(),clientEntity.getRoleEntity().getName());

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

    /**
     * Get client model by ID
     * @return Client
     */
    @Override
    public Client getClientById(int id) {
        logger.info("getClientById");

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

    /**
     * Updates client model fields
     * @param client
     */
    @Override
    public void updateUser(Client client) {
        logger.info("updateUser");

        ClientEntity clientEntity = clientRepository.getUserById(client.getId());

        ClientAddressEntity clientAddressEntity = clientEntity.getClientAddressEntity();
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

    /**
     * Converts client model to client Entity
     * @return Client Entity
     */

    public ClientEntity convertClientToDAO(Client client) {
        logger.info("convertClientToDAO");
        RoleEntity role = new RoleEntity();
        role.setId(3);                                  // default role:ROLE_CLIENT - 3(client)
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
