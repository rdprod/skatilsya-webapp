package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.details.UserDetailsImpl;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.role.RoleService;
import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @Value("${user_avatar.path}")
    private String userAvatarPath;

    public UserController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

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
    public String registerProcess(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                  HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации при регистрации пользователя: " +
                    bindingResult.getAllErrors()
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",\n")));

            return "registration";
        } else {
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
                                  @RequestParam(value = "emailError", required = false) boolean emailError,
                                  Model model, Principal principal) {
        UserDetailsImpl currentUserDetails = ((UserDetailsImpl)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal());

        if (currentUserDetails.getId() == userId || currentUserDetails.getAuthorities()
                .stream().anyMatch(role -> ((SimpleGrantedAuthority) role).getAuthority().contains("ADMIN"))) {
            User user = userService.findUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("updated", updated);
            model.addAttribute("emailError", emailError);

            return "profile";
        } else {

            return "redirect:/profile/" + currentUserDetails.getId();
        }
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@Valid @ModelAttribute User user, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasFieldErrors("email")) {
            System.out.println("Ошибка валидации про смене email: " +
                    bindingResult.getAllErrors()
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",\n")));

            redirectAttributes.addAttribute("emailError", true);
        } else {
            Set<Role> roles = ((UserDetailsImpl)((UsernamePasswordAuthenticationToken) principal)
                    .getPrincipal()).getRoles();
            user.setRoles(roles);
            userService.saveUser(user);

            redirectAttributes.addAttribute("updated", true);
        }

        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/uploadUserAvatar")
    public String uploadUserAvatar(@RequestParam("userAvatar") MultipartFile userAvatarFile,
                                   @RequestParam("id") int id) throws IOException {
        User user = userService.findUserById(id);
        if (userAvatarFile != null) {
            File userAvatarDir = new File(userAvatarPath);
            if (!userAvatarDir.exists()) {
                System.out.println("Папка для хранения аватарок пользователей создана: " + userAvatarDir.mkdir());
            }

            String uuidUserAvatarFile = UUID.randomUUID().toString();
            String finalUserAvatarFilename = uuidUserAvatarFile + userAvatarFile.getOriginalFilename();

            userAvatarFile.transferTo(new File(userAvatarPath + "/" + finalUserAvatarFilename));
            if (user.getAvatar() != null) {
                File oldUserAvatarFile = new File(userAvatarPath + "/" + user.getAvatar());
                System.out.println("Старая аватарка пользователя была удалена: " + oldUserAvatarFile.delete());
            }
            user.setAvatar(finalUserAvatarFilename);
            userService.saveUser(user);

            BufferedImage image = ImageIO.read
                    (new File(userAvatarPath + "/" + finalUserAvatarFilename));
            int imageSideSize = Math.min(image.getHeight(), image.getWidth());
            image = image.getSubimage(image.getWidth() / 2 - imageSideSize / 2,
                    image.getHeight() / 2 - imageSideSize / 2, imageSideSize, imageSideSize);
            ImageIO.write(image, finalUserAvatarFilename.split("\\.")[1],
                    new File(userAvatarPath + "/" + finalUserAvatarFilename));
        }

        return "redirect:/profile/" + user.getId();
    }
}
