package com.rdprod.springboot.spring_rdprod_webapp.service.user;

import com.rdprod.springboot.spring_rdprod_webapp.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User findUserByUsername(String username);

    User findUserById(int id);

    List<User> findAllUsers();
}
