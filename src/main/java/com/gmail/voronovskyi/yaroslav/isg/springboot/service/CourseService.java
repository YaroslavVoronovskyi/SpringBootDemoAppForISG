package com.gmail.voronovskyi.yaroslav.isg.springboot.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    CourseDto create(CourseDto courseDto);

    Optional<CourseDto> get(int id);

    List<CourseDto> getAll();

    CourseDto update(CourseDto courseDto);

    void delete(int id);
}
