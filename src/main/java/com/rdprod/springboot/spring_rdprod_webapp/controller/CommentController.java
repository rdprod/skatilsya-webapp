package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import com.rdprod.springboot.spring_rdprod_webapp.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/feedback")
    public String showFeedbackPage(Model model) {
        List<Comment> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "feedback";
    }

    @PostMapping("/addNewCommentProcess")
    public String addNewComment(@ModelAttribute("newComment") Comment newComment, Principal principal) {
        newComment.setUser((User) principal);
        commentService.addNewComment(newComment);

        return "redirect:/feedback";
    }
}
