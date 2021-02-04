package com.rdprod.springboot.spring_rdprod_webapp.service.comment;

import com.rdprod.springboot.spring_rdprod_webapp.dao.CommentRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void addNewComment(Comment comment) {
        commentRepository.save(comment);
    }
}
