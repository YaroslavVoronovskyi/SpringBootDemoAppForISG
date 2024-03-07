package com.gmail.voronovskyi.yaroslav.isg.springboot.repository;

import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
