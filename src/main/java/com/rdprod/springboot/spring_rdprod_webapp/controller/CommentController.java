package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.details.UserDetailsImpl;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.comment.CommentService;
import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @GetMapping("/feedback")
    public String showFeedbackPage(Model model) {
        List<Comment> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            User user = userService.findUserByUsername(((UserDetails) principal).getUsername());
            model.addAttribute("userId", user.getId());
        }

        return "feedback";
    }

    @PostMapping("/addNewCommentProcess")
    public String addNewComment(@ModelAttribute("newComment") Comment newComment, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        newComment.setUser(user);
        commentService.addNewComment(newComment);

        return "redirect:/feedback";
    }

    @GetMapping("/deleteCommentProcess/{id}")
    public String deleteComment(@PathVariable("id") int id) {
        commentService.deleteComment(id);

        return "redirect:/feedback";
    }
}
