package com.rdprod.springboot.spring_rdprod_webapp.service.role;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;

import java.util.List;

public interface RoleService {
    Role findSimpleUserRole();

    List<Role> findAllRoles();
}
