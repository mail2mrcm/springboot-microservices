package com.hello2chandan.schoolms.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.hello2chandan.schoolms.model.School;

@Component
public interface SchoolRepository extends JpaRepository<School, Integer>  {
    
}
