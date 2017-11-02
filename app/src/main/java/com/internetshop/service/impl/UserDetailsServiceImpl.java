package com.internetshop.service.impl;

import com.internetshop.Exceptions.UserNotConfirmException;
import com.internetshop.controller.ClientController;
import com.internetshop.entities.ClientEntity;
import com.internetshop.entities.RoleEntity;
import com.internetshop.model.Client;
import com.internetshop.model.Role;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

    private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        logger.info("loadUserByUsername with param:"+email);
        ClientEntity clientEntity;
        try {
            clientEntity = clientRepository.getUserByEmail(email);
            if(clientEntity.getIsConfirm()==0){
                throw new UsernameNotFoundException("User not confirm");
            }
        } catch (NoResultException e) {
            logger.error("No client with entered email");
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = buildUserAuthority(clientEntity.getRoleEntity());
        logger.info("found user with email "+clientEntity.getEmail());
        return buildUserForAuthentication(clientEntity,authorities);
    }

    private User buildUserForAuthentication(ClientEntity client, List<GrantedAuthority> authorities) {
        return new User(client.getEmail(),client.getPassword(),authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(RoleEntity clientRole) {

        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(clientRole.getName()));
        List<GrantedAuthority> result = new ArrayList<>(setAuths);
        return result;
    }

}
