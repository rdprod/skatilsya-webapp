package com.rdprod.springboot.spring_rdprod_webapp.service.comment;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllComments();

    void addNewComment(Comment comment);

    void deleteComment(int id);
}
