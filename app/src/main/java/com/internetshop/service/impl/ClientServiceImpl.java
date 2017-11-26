package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.config.AppConfig;
import com.internetshop.controller.HomeController;
import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.OrderEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.*;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final MailService mailService;

    PasswordEncoder passwordEncoder;
    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class.getName());


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder,MailService mailService){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Transactional(readOnly = true)
    public List<ClientEntity> getAllClients(){ return clientRepository.getAll(); }

    @Transactional(readOnly = true)
    @Override
    public List<Client> getBestClientsList(int amountOfBestClients) {
        List<Client> clientList = new ArrayList<>();
        for(ClientEntity clientEntity : clientRepository.getBestClientsList(amountOfBestClients)){
            clientList.add(convertClientToDTO(clientEntity));
        }
        return clientList;
    }

    /**
     * Adds new client
     * @param client new client model
     * @throws EmailExistException email already exists
     */
    @Transactional
    @Override
    public void addClient(Client client) throws EmailExistException {
        logger.info("addClient");
        if (clientRepository.isEmailExist(client.getEmail())){
            throw new EmailExistException(client.getEmail());
        }
        Role role = new Role();
        role.setId(3);
        client.setRole(role);

        client.setPassword(passwordEncoder.encode(client.getPassword()));

        ClientEntity clientEntity = convertClientToDAO(client);
        clientEntity.setIsConfirm(0);
        clientEntity.setConfirmationId(UUID.randomUUID().toString());
        Set<OrderEntity> orderEntitySet = new HashSet<>();
        clientEntity.setOrderEntities(orderEntitySet);
        this.clientRepository.addClient(clientEntity);

    }

    /**
     * Changes user's password
     * @throws PasswordWrongException
     */
    @Transactional
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
    @Transactional(readOnly = true)
    @Override
    public Client getUserByEmail(String email) {
        logger.info("getUserByEmail");
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        return convertClientToDTO(clientEntity);
    }

    /**
     * Get client model by ID
     * @return Client
     */
    @Transactional(readOnly = true)
    @Override
    public Client getClientById(int id) {
        logger.info("getClientById");

        ClientEntity clientEntity = clientRepository.getUserById(id);

        return convertClientToDTO(clientEntity);
    }
    @Transactional(readOnly = true)
    @Override
    public String getEmailByConfirmationId(String id) {
        return clientRepository.getEmailByConfirmationId(id);
    }

    /**
     * Updates client model fields
     * @param client
     */
    @Transactional
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
        clientEntity.setBirthdate(client.getBirthdate());
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

    @Transactional(readOnly = true)
    @Override
    public boolean isIdContains(String email, String id) {
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        return id.equals(clientEntity.getConfirmationId());
    }
    @Transactional
    @Override
    public void confirmClientEmail(String email) {
        ClientEntity clientEntity = clientRepository.getUserByEmail(email);
        clientEntity.setIsConfirm(1);
        clientRepository.updateUser(clientEntity);
    }
    @Transactional
    @Override
    public String resetConfirmationId(String email) {
        ClientEntity client = clientRepository.getUserByEmail(email);
        String confirmationId = UUID.randomUUID().toString();
        client.setConfirmationId(confirmationId);
        clientRepository.updateUser(client);
        return confirmationId;
    }



    @Transactional
    @Override
    public void recoverConfirmationIdAndSendEmail(String email) throws UsernameNotFoundException {
        String confirmationId = resetConfirmationId(email);
        ClientEntity client = clientRepository.getUserByEmail(email);

        Mail mail = new Mail();
        mail.setMailFrom(AppConfig.MAIL_FROM);
        mail.setMailTo(client.getEmail());
        mail.setMailSubject("Dice Games. Recover Password");

        Map< String, Object > model = new HashMap<>();

        model.put("firstName", client.getName());
        model.put("location", AppConfig.MAIL_LOCATION);
        model.put("signature", AppConfig.MAIL_SIGNATURE);
        model.put("mailMsg", "To recover your password follow the link below");
        model.put("link", AppConfig.HOST_URL+"/recover/password?id="+confirmationId);
        mail.setModel(model);
        mailService.sendEmail(mail,"email-template.txt");
    }

    /**
     * Converts client model to client Entity
     * @return Client Entity
     */

    public ClientEntity convertClientToDAO(Client client) {
        RoleEntity role = clientRepository.getRoleById(client.getRole().getId());
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setRoleEntity(role);
        return clientEntity;
    }

    public Client convertClientToDTO(ClientEntity clientEntity){
        Client client = new Client();
        Role role = new Role(clientEntity.getRoleEntity().getId(),clientEntity.getRoleEntity().getName());
        client.setRole(role);

        client.setId(clientEntity.getId());
        client.setName(clientEntity.getName());
        client.setSurname(client.getSurname());
        client.setBirthdate(clientEntity.getBirthdate());
        client.setEmail(clientEntity.getEmail());
        client.setPassword(clientEntity.getPassword());
        client.setPhone(clientEntity.getPhone());
        client.setOrderCounter(clientEntity.getOrderCounter());
        client.setIsConfirm(clientEntity.getIsConfirm());
        client.setConfirmationId(clientEntity.getConfirmationId());


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
}
