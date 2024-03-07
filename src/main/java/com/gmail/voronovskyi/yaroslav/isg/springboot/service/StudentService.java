package com.gmail.voronovskyi.yaroslav.isg.springboot.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto create(StudentDto studentDto);

    StudentDto get(int id);

    List<StudentDto> getAll();

    StudentDto update(StudentDto studentDto);

    void delete(int id);
}
