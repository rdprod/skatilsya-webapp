package com.rdprod.springboot.spring_rdprod_webapp.dao;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
