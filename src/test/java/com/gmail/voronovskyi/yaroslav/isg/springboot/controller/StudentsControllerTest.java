package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentsControllerTest {

    private final static int STUDENT_ID = 1;
    private final static String STUDENT_FIRST_NAME = "Yaroslav";
    private final static String STUDENT_LAST_NAME = "Voronovskyi";
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int STUDENT_INDEX = 0;

    @Mock
    private StudentService studentServiceMock;
    @InjectMocks
    private StudentsController studentsController;

    @Test
    void shouldGetExpectedStudentById() {
        Mockito.when(studentServiceMock.get(STUDENT_ID)).thenReturn(createTestStudentDto());
        assertEquals(createTestStudentDto().getId(), studentsController.get(STUDENT_ID).getId());
        assertEquals(createTestStudentDto().getFirstName(), studentsController.get(STUDENT_ID).getFirstName());
        assertEquals(createTestStudentDto().getLastName(), studentsController.get(STUDENT_ID).getLastName());

    }

    @Test
    void shouldGetAllStudents() {
        Mockito.when(studentServiceMock.getAll()).thenReturn(createTestStudentsDtoList());
        assertEquals(EXPECTED_SIZE_LIST, studentsController.getAll().size());
        assertEquals(createTestStudentDto().getId(), studentsController.getAll().get(STUDENT_INDEX).getId());
        assertEquals(createTestStudentDto().getFirstName(), studentsController.getAll().get(STUDENT_INDEX).getFirstName());
        assertEquals(createTestStudentDto().getLastName(), studentsController.getAll().get(STUDENT_INDEX).getLastName());
    }

    @Test
    void shouldCreateNewStudent() {
        studentsController.create(createTestStudentDto());
        Mockito.verify(studentServiceMock).create(createTestStudentDto());
    }

    @Test
    void shouldUpdateExpectedStudent() {
        studentsController.update(STUDENT_ID, createTestStudentDto());
        Mockito.verify(studentServiceMock).update(createTestStudentDto());
    }

    @Test
    void shouldDeleteExpectedStudent() {
        studentsController.delete(STUDENT_ID);
        Mockito.verify(studentServiceMock).delete(STUDENT_ID);
    }

    private StudentDto createTestStudentDto() {
        return new StudentDto()
                .setId(STUDENT_ID)
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME);
    }

    private List<StudentDto> createTestStudentsDtoList() {
        return List.of(new StudentDto()
                .setId(STUDENT_ID)
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME));
    }
}