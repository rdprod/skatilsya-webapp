package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.comment.CommentService;
import com.rdprod.springboot.spring_rdprod_webapp.service.role.RoleService;
import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final CommentService commentService;

    private final RoleService roleService;

    public AdminController(UserService userService,
                           CommentService commentService,
                           RoleService roleService) {
        this.userService = userService;
        this.commentService = commentService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        List<User> users = userService.findAllUsers();
        List<User> editUsers = new ArrayList<>();
        users.forEach(user -> {
            User editUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
            editUser.setId(user.getId());
            editUsers.add(editUser);
        });

        return editUsers;
    }

    @GetMapping("/comments")
    public List<Comment> showAllComments() {
        List<Comment> comments = commentService.findAllComments();
        List<Comment> editComments = new ArrayList<>();
        comments.forEach(comment -> {
            Comment editComment = new Comment(comment.getText());
            editComment.setId(comment.getId());
            editComments.add(editComment);
        });

        return editComments;
    }

    @GetMapping("/roles")
    public List<Role> showAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        List<Role> editRoles = new ArrayList<>();
        roles.forEach(role -> {
            Role editRole = new Role(role.getName());
            editRole.setId(role.getId());
            editRoles.add(editRole);
        });

        return editRoles;
    }
}
