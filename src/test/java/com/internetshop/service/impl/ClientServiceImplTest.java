package com.internetshop.service.impl;

import com.internetshop.Exceptions.EmailExistException;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {

    ClientService clientService;
    ClientRepository clientRepository;
    PasswordEncoder encoder;

    @Before
    public void setUp() throws Exception {
        clientRepository = mock(ClientRepository.class);
        encoder = new BCryptPasswordEncoder();
        clientService = new ClientServiceImpl(clientRepository, encoder);
    }

    @Test(expected = EmailExistException.class)
    public void getAllClientsEmailExists() throws Exception {
        when(clientRepository.isEmailExist(anyString())).thenReturn(true);
        clientService.addClient(new Client());
    }

    @Test
    public void getAllClientsHashPassword() throws Exception {
        when(clientRepository.isEmailExist(anyString())).thenReturn(false);
        when(clientRepository.getRoleById(anyInt())).thenReturn(new RoleEntity());
        Client client = new Client();
        String password = "123123";
        client.setPassword(password);

        clientService.addClient(client);

        String encodedPassword = client.getPassword();
        assertTrue(encoder.matches(password, encodedPassword));
    }


}