package com.rdprod.springboot.spring_rdprod_webapp.service.user;

import com.rdprod.springboot.spring_rdprod_webapp.dao.UserRepository;
import com.rdprod.springboot.spring_rdprod_webapp.details.UserDetailsImpl;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь - " + username + " не найден");
        }
        return new UserDetailsImpl(user);
    }
}
