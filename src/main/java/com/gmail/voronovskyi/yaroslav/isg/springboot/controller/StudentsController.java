package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.StudentService;
import com.gmail.voronovskyi.yaroslav.isg.springboot.util.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentsController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public Optional<StudentDto> get(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try get student wih id {}", id);
        Optional<StudentDto> studentDto = studentService.get(id);
        log.debug("Student wih id {} was successfully got", id);
        return studentDto;
    }

    @GetMapping()
    public List<StudentDto> getAll() {
        log.debug("Try get all students");
        List<StudentDto> studentsDtoList = studentService.getAll();
        log.debug("All students was successfully got");
        return  studentsDtoList;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto create(@RequestBody @Valid StudentDto studentDto) {
        log.debug("Try create new student");
        return  studentService.create(studentDto);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable(Constants.ENTITY_ID) int id, @RequestBody @Valid StudentDto studentDto) {
        log.debug("Try update student wih id {}", id);
        studentDto.setId(id);
        return  studentService.update(studentDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try delete student wih id {}", id);
        studentService.delete(id);
        log.debug("Student was deleted wih id {}", id);
    }
}
