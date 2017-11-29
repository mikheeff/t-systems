package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.entities.ClientAddressEntity;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.ClientAddress;
import com.internetshop.model.PasswordField;
import com.internetshop.model.Role;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import com.internetshop.service.api.MailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    ClientService clientService;
    ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;
    MailService mailService;
    Client client;
    ClientEntity clientEntity;

    @Spy
    List<Client> clientList = new ArrayList<>();
//    ClientEntity clientEntity;
    PasswordField passwordField;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientList = getClientList();
        clientEntity = getClientEntity();
        clientRepository = mock(ClientRepository.class);
        client = mock(Client.class);
        passwordEncoder = spy(BCryptPasswordEncoder.class);
        passwordField = spy(PasswordField.class);
        MailService mailService = mock(MailService.class);
        clientService = new ClientServiceImpl(clientRepository, passwordEncoder,mailService);
    }

    @Test(expected = EmailExistException.class)
    public void getAllClientsEmailExists() throws Exception {
        when(clientRepository.isEmailExist(anyString())).thenReturn(true);
        clientService.addClient(new Client());
    }

    @Test
    public void getAllClientsHashPassword() throws Exception { //
        when(clientRepository.isEmailExist(anyString())).thenReturn(false);
        when(clientRepository.getRoleById(anyInt())).thenReturn(new RoleEntity());
        Client client = new Client();
        String password = "123123";
        client.setPassword(password);

        clientService.addClient(client);

        String encodedPassword = client.getPassword();
        assertTrue(passwordEncoder.matches(password, encodedPassword));
    }
    @Test(expected = PasswordWrongException.class)
    public void changePasswordPasswordWrong() throws Exception {
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);
        when(passwordField.getPassword()).thenReturn("");
        clientService.changePassword(passwordField,new Client());
    }

    @Test
    public void getClientByIdTest() throws Exception {
        Client client1 = clientList.get(0);
        when(clientRepository.getClientById(anyInt())).thenReturn(clientEntity);
        Assert.assertEquals(clientService.getClientById(client1.getId()).getId(),client1.getId());
    }
    @Test
    public void addClientTest() throws Exception{
        Client client1 = clientList.get(0);
        doNothing().when(clientRepository).addClient(any(ClientEntity.class));
        clientService.addClient(client1);
        verify(clientRepository,atLeastOnce()).addClient(any(ClientEntity.class));
    }
    @Test
    public void updateClient() throws Exception {
        Client client1 = clientList.get(0);
        when(clientRepository.getClientById(anyInt())).thenReturn(clientEntity);
        doNothing().when(clientRepository).updateClient(any(ClientEntity.class));
        clientService.updateClient(client1);
        verify(clientRepository,atLeastOnce()).getClientById(anyInt());
    }

    public List<Client> getClientList(){
        Client client1 = new Client();
        client1.setId(1);
        client1.setName("Alex");
        client1.setEmail("alex@mail.ru");
        client1.setPassword("123456");
        client1.setPhone("+79818829192");
        Role role = new Role();
        role.setId(3);
        role.setName("ROLE_CLIENT");
        client1.setRole(role);
        ClientAddress clientAddress = new ClientAddress();
        clientAddress.setId(1);
        client1.setClientAddress(clientAddress);

        Client client2 = new Client();
        client2.setId(2);
        client2.setName("Vasya");
        client2.setEmail("vasya@mail.ru");
        client2.setPassword("qwertyu");
        client2.setPhone("+7125681423");
        clientList.add(client1);
        clientList.add(client2);
        return clientList;
    }

    public ClientEntity getClientEntity(){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setName("Alex");
        clientEntity.setPassword("123456");
        clientEntity.setEmail("alex@mail.ru");
        clientEntity.setPhone("+79818829192");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(3);
        roleEntity.setName("ROLE_CLIENT");
        clientEntity.setRoleEntity(roleEntity);
        ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
        clientAddressEntity.setId(1);
        clientEntity.setClientAddressEntity(clientAddressEntity);
        return clientEntity;
    }

}