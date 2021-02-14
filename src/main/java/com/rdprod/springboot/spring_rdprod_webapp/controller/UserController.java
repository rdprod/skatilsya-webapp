package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.role.RoleService;
import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Value("${user_avatar.path}")
    private String userAvatarPath;


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

        userService.saveUser(user);

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

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable("id") int userId,
                                  @RequestParam(value = "updated", required = false) boolean updated,
                                  Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("updated", updated);
        return "profile";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.saveUser(user);
        redirectAttributes.addAttribute("updated", true);

        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/uploadUserAvatar")
    public String uploadUserAvatar(@RequestParam("userAvatar") MultipartFile userAvatarFile,
                                   @RequestParam("id") int id) throws IOException {
        User user = userService.findUserById(id);
        if (userAvatarFile != null) {
            File userAvatarDir = new File(userAvatarPath);
            if (!userAvatarDir.exists()) {
                System.out.println("User avatar folder create: " + userAvatarDir.mkdir());
            }

            String uuidUserAvatarFile = UUID.randomUUID().toString();
            String finalUserAvatarFilename = uuidUserAvatarFile + userAvatarFile.getOriginalFilename();

            userAvatarFile.transferTo(new File(userAvatarPath + "/" + finalUserAvatarFilename));
            if (user.getAvatar() != null) {
                File oldUserAvatarFile = new File(userAvatarPath + "/" + user.getAvatar());
                System.out.println("Old user avatar was deleted: " + oldUserAvatarFile.delete());
            }
            user.setAvatar(finalUserAvatarFilename);
            userService.saveUser(user);
        }

        return "redirect:/profile/" + user.getId();
    }
}
