package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        UserAccount user = userAccountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserPrincipal(user);
    }
}
