package com.rdprod.springboot.spring_rdprod_webapp.service.role;

import com.rdprod.springboot.spring_rdprod_webapp.dao.RoleRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findSimpleUserRole() {
       return roleRepository.findByName("USER");
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
