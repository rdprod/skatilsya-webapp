package com.rdprod.springboot.spring_rdprod_webapp.dao;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
