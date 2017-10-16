package com.internetshop.service.impl;

import com.internetshop.model.Client;
import com.internetshop.repository.api.ClientRepository;
import com.internetshop.service.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Client client = clientService.getUserByEmail(email);
//        List<GrantedAuthorities>
        return null;
    }
}
