package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.Exceptions.PasswordWrongException;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.PasswordField;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {

    ClientService clientService;
    ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;
    Client client;
    @Before
    public void setUp() throws Exception {
        clientRepository = mock(ClientRepository.class);
        client = mock(Client.class);
        passwordEncoder = spy(BCryptPasswordEncoder.class);
//        clientService = new ClientServiceImpl(clientRepository, passwordEncoder);
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
        clientService.changePassword(new PasswordField(),new Client());
    }




}