package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.exception.NotValidDataException;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Student;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.StudentRepository;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public StudentDto create(StudentDto studentDto) {
        log.debug("Try create new Student and save in DB");
        try {
            Student student = studentRepository.save(convertToEntity(studentDto));
            return convertToDto(student);
        } catch (DataIntegrityViolationException exception) {
            throw new NotValidDataException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto get(int id) {
        log.debug("Try get Student wih id {} from DB", id);
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student not found with ID " + id));
        return convertToDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAll() {
        log.debug("Try get all Students from DB");
        List<Student> studentsList = studentRepository.findAll();
        if (studentsList.isEmpty()) {
            throw new EntityNotFoundException("Students not fount!");
        }
        log.debug("All Students was successfully got from DB");
        return convertToDtoList(studentsList);
    }

    @Override
    @Transactional
    public StudentDto update(StudentDto studentDto) {
        log.debug("Try update Student wih id {} from DB", studentDto.getId());
        Student student = studentRepository.save(convertToEntity(studentDto));
        return convertToDto(student);
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Try delete Student wih id {} from DB", id);
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Student with id " + id + " does not exist or has been deleted");
        }
        log.debug("Student wih id {} was successfully deleted from DB", id);
    }


    private StudentDto convertToDto(Student student) {
        try {
            return modelMapper.map(student, StudentDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("Student does not exist or has been deleted");
        }
    }

    private Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    private List<StudentDto> convertToDtoList(List<Student> studenstList) {
        return studenstList.stream()
                .map(this::convertToDto)
                .toList();
    }
}
