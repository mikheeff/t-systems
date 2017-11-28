package com.internetshop.repository.api;

import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;

import java.util.List;

public interface ClientRepository {
    List<ClientEntity> getBestClientsList(int amountOfBestClients);
    void addClient(ClientEntity clientEntity);
    ClientEntity getClientByEmail(String email);
    ClientEntity getClientById(int id);
    String getEmailByConfirmationId(String id);
    void updateClient(ClientEntity clientEntity);
    RoleEntity getRoleById(int id);
    boolean isEmailExist(String email);

}
