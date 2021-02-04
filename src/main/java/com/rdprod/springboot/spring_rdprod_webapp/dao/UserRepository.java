package com.rdprod.springboot.spring_rdprod_webapp.dao;

import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
