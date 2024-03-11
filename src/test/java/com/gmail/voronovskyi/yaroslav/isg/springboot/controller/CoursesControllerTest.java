package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CoursesControllerTest {

    private final static int COURSE_ID = 1;
    private final static String COURSE_NAME = "Java";
    private final static String COURSE_DESCRIPTION = "Java learning";
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int COURSE_INDEX = 0;
    private final static String NOT_VALID_COURSE_NAME = "J";
    private final static String NOT_VALID_COURSE_DESCRIPTION = "J";

    @Mock
    private CourseService courseServiceMock;
    @InjectMocks
    private CoursesController coursesController;

    @Test
    void shouldGetExpectedCourseById() {
        Mockito.when(courseServiceMock.get(COURSE_ID)).thenReturn(createTestCourseDto());
        assertEquals(createTestCourseDto().getId(), coursesController.get(COURSE_ID).getId());
        assertEquals(createTestCourseDto().getName(), coursesController.get(COURSE_ID).getName());
        assertEquals(createTestCourseDto().getDescription(), coursesController.get(COURSE_ID).getDescription());
    }

    @Test
    void shouldGetAllCourses() {
        Mockito.when(courseServiceMock.getAll()).thenReturn(createTestCoursesDtoList());
        assertEquals(EXPECTED_SIZE_LIST, coursesController.getAll().size());
        assertEquals(createTestCourseDto().getId(), coursesController.getAll().get(COURSE_INDEX).getId());
        assertEquals(createTestCourseDto().getName(), coursesController.getAll().get(COURSE_INDEX).getName());
        assertEquals(createTestCourseDto().getDescription(), coursesController.getAll().get(COURSE_INDEX).getDescription());
    }

    @Test
    void shouldCreateNewCourse() {
        coursesController.create(createTestCourseDto());
        Mockito.verify(courseServiceMock).create(createTestCourseDto());
    }

    @Test
    void shouldUpdateExpectedCourse() {
        coursesController.update(COURSE_ID, createTestCourseDto());
        Mockito.verify(courseServiceMock).update(createTestCourseDto());
    }

    @Test
    void shouldDeleteExpectedCourse() {
        coursesController.delete(COURSE_ID);
        Mockito.verify(courseServiceMock).delete(COURSE_ID);
    }

//    @Test
//    void shouldThrowExceptionWhenTryCreateNewCourses() {
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            coursesController.create(createNotValidTestCourseDto());
//        });
//    }


    private CourseDto createNotValidTestCourseDto() {
        return new CourseDto()
                .setId(COURSE_ID)
                .setName(NOT_VALID_COURSE_NAME)
                .setDescription(NOT_VALID_COURSE_DESCRIPTION);
    }

    private CourseDto createTestCourseDto() {
        return new CourseDto()
                .setId(COURSE_ID)
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION);
    }

    private List<CourseDto> createTestCoursesDtoList() {
        return List.of(new CourseDto()
                .setId(COURSE_ID)
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION));
    }
}