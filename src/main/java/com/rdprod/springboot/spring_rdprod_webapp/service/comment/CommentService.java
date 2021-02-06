package com.rdprod.springboot.spring_rdprod_webapp.service.comment;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> findAllComments();

    public void addNewComment(Comment comment);

    public void deleteComment(int id);
}
