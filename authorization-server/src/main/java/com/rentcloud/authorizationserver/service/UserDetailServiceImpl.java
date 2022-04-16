package com.rentcloud.authorizationserver.service;

import com.rentcloud.authorizationserver.model.AuthUserDetails;
import com.rentcloud.authorizationserver.model.User;
import com.rentcloud.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userDetailRepository.findByUsername(name);
        User u = user.orElseThrow(() -> new UsernameNotFoundException("Username or Password is wrong!"));
        UserDetails userDetails = new AuthUserDetails(u);

        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }
}