package com.internetshop.repository.api;

import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;

import java.util.List;

public interface ClientRepository {
    List<ClientEntity> getAll();
    void addClient(ClientEntity clientEntity);
    ClientEntity getUserByEmail(String email);
    ClientEntity getUserById(int id);
    void updateUser(ClientEntity clientEntity);
    void addAddress(ClientAddressEntity clientAddressEntity);
    RoleEntity getRoleById(int id);


}
