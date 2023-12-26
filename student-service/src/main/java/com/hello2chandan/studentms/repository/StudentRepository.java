package com.hello2chandan.studentms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.hello2chandan.studentms.model.Student;

@Component
public interface StudentRepository extends JpaRepository<Student, Integer>  {
    
}
