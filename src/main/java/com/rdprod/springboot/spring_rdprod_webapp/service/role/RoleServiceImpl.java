package com.rdprod.springboot.spring_rdprod_webapp.service.role;

import com.rdprod.springboot.spring_rdprod_webapp.dao.RoleRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findSimpleUserRole() {
       return roleRepository.findByName("USER");
    }
}
