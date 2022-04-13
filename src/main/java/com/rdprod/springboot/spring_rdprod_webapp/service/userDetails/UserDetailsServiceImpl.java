package com.rdprod.springboot.spring_rdprod_webapp.service.userDetails;

import com.rdprod.springboot.spring_rdprod_webapp.dao.UserRepository;
import com.rdprod.springboot.spring_rdprod_webapp.details.UserDetailsImpl;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь - " + username + " не найден");
        }
        return new UserDetailsImpl(user);
    }
}
