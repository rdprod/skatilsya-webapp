package com.rdprod.springboot.spring_rdprod_webapp.controller;


import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.role.RoleService;
import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ((UsernamePasswordAuthenticationToken) auth).isAuthenticated();

            return "redirect:/";
        } catch (ClassCastException exp) {
            model.addAttribute(new User());

            return "registration";
        }
    }

    @PostMapping("/registrationProcess")
    public String registerProcess(@ModelAttribute("user") User user, HttpServletRequest request) {
        String originalPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        user.setPassword(encodedPassword);

        Role simpleRole = roleService.findSimpleUserRole();
        user.addRoleToUser(simpleRole);

        userService.saveNewUser(user);

        try {
            request.login(user.getUsername(), originalPassword);
        } catch (ServletException exp) {
            System.out.println("Ошибка автоматической аутентификации - " + exp);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ((UsernamePasswordAuthenticationToken) auth).isAuthenticated();

            return "redirect:/";
        } catch (ClassCastException exp) {

            return "login";
        }
    }
}
