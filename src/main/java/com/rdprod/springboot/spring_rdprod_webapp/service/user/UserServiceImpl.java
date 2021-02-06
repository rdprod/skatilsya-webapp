package com.rdprod.springboot.spring_rdprod_webapp.service.user;

import com.rdprod.springboot.spring_rdprod_webapp.dao.UserRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
