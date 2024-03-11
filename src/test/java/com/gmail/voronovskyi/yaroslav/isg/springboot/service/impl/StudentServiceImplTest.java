package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Student;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private final static int STUDENT_ID = 1;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int STUDENT_INDEX = 0;
    private final static String STUDENT_FIRST_NAME = "Yaroslav";
    private final static String STUDENT_LAST_NAME = "Voronovskyi";

    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldGetExpectedStudentById() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestStudentDto());
        Mockito.when(studentRepositoryMock.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(createTestStudent()));
        assertEquals(createTestStudentDto().getId(), studentService.get(STUDENT_ID).getId());
        assertEquals(createTestStudentDto().getFirstName(), studentService.get(STUDENT_ID).getFirstName());
        assertEquals(createTestStudentDto().getLastName(), studentService.get(STUDENT_ID).getLastName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetStudentByWrongId() {
        Mockito.when(studentRepositoryMock.findById(STUDENT_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.get(STUDENT_ID);
        });
    }

    @Test
    void shouldGetAllStudents() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestStudentDto());
        Mockito.when(studentRepositoryMock.findAll()).thenReturn(createTestStudentsList());
        assertEquals(EXPECTED_SIZE_LIST, studentService.getAll().size());
        assertEquals(createTestStudentDto().getId(), studentService.getAll().get(STUDENT_INDEX).getId());
        assertEquals(createTestStudentDto().getFirstName(), studentService.getAll().get(STUDENT_INDEX).getFirstName());
        assertEquals(createTestStudentDto().getLastName(), studentService.getAll().get(STUDENT_INDEX).getLastName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetAllStudents() {
        Mockito.when(studentRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getAll();
        });
    }

    @Test
    void shouldCreateNewStudent() {
        studentService.create(createTestStudentDto());
        Mockito.verify(studentRepositoryMock).save(any());
    }

    @Test
    void shouldUpdateExpectedStudent() {
        studentService.update(createTestStudentDto());
        Mockito.verify(studentRepositoryMock).save(any());
    }

    @Test
    void shouldDeleteExpectedStudent() {
        studentService.delete(STUDENT_ID);
        Mockito.verify(studentRepositoryMock).deleteById(STUDENT_ID);
    }

    private Student createTestStudent() {
        return new Student()
                .setId(STUDENT_ID)
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME);
    }

    private List<Student> createTestStudentsList() {
        return List.of(new Student()
                .setId(STUDENT_ID)
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME));
    }

    private StudentDto createTestStudentDto() {
        return new StudentDto()
                .setId(STUDENT_ID)
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME);
    }
}