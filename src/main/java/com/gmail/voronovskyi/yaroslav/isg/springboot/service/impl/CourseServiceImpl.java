package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.exception.NotValidDataException;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Course;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.CourseRepository;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CourseDto create(CourseDto courseDto) {
        log.debug("Try create new Course and save in DB");
        try {
            Course course = courseRepository.save(convertToEntity(courseDto));
            return convertToDto(course);
        } catch (DataIntegrityViolationException exception) {
            throw new NotValidDataException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto get(int id) {
        log.debug("Try get Course wih id {} from DB", id);
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course not found with ID " + id));
        return convertToDto(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAll() {
        log.debug("Try get all Courses from DB");
        List<Course> coursesList = courseRepository.findAll();
        if (coursesList.isEmpty()) {
            throw new EntityNotFoundException("Courses not fount!");
        }
        log.debug("All Courses was successfully got from DB");
        return convertToDtoList(coursesList);
    }

    @Override
    @Transactional
    public CourseDto update(CourseDto courseDto) {
        log.debug("Try update Course wih id {} from DB", courseDto.getId());
        Course course = courseRepository.save(convertToEntity(courseDto));
        return convertToDto(course);
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Try delete Course wih id {} from DB", id);
        try {
            courseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Course with id " + id + " does not exist or has been deleted");
        }
        log.debug("Course wih id {} was successfully deleted from DB", id);
    }

    private CourseDto convertToDto(Course course) {
        try {
            return modelMapper.map(course, CourseDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("Course does not exist or has been deleted");
        }
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    private List<CourseDto> convertToDtoList(List<Course> coursesList) {
        return coursesList.stream()
                .map(this::convertToDto)
                .toList();
    }
}
