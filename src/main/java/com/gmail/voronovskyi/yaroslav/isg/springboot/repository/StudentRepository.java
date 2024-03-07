package com.gmail.voronovskyi.yaroslav.isg.springboot.repository;

import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
