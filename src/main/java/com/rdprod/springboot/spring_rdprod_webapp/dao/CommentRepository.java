package com.rdprod.springboot.spring_rdprod_webapp.dao;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
