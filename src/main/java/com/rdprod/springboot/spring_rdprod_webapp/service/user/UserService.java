package com.rdprod.springboot.spring_rdprod_webapp.service.user;

import com.rdprod.springboot.spring_rdprod_webapp.entity.User;

public interface UserService {

    public void saveUser(User user);

    public User findUserByUsername(String username);

    public User findUserById(int id);
}
