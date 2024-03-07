package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.CourseService;
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

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public CourseDto get(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try get course wih id {}", id);
        CourseDto courseDto = courseService.get(id);
        log.debug("Course wih id {} was successfully got", id);
        return courseDto;
    }

    @GetMapping()
    public List<CourseDto> getAll() {
        log.debug("Try get all courses");
        List<CourseDto> coursesDtoList = courseService.getAll();
        log.debug("All courses was successfully got");
        return coursesDtoList;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto create(@RequestBody @Valid CourseDto courseDto) {
        log.debug("Try create new course");
        return courseService.create(courseDto);
    }

    @PutMapping("/{id}")
    public CourseDto update(@PathVariable(Constants.ENTITY_ID) int id, @RequestBody @Valid CourseDto courseDto) {
        log.debug("Try update course wih id {}", id);
        courseDto.setId(id);
        return courseService.update(courseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try delete course wih id {}", id);
        courseService.delete(id);
        log.debug("Course was deleted wih id {}", id);
    }
}
