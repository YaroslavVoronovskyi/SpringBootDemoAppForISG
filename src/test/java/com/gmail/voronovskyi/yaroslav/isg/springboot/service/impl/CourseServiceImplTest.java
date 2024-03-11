package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Course;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.CourseRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    private final static int COURSE_ID = 1;
    private final static String COURSE_NAME = "Java";
    private final static String COURSE_DESCRIPTION = "Java learning";
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int COURSE_INDEX = 0;

    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void shouldGetExpectedCourseById() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCourseDto());
        Mockito.when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.ofNullable(createTestCourse()));
        assertEquals(createTestCourseDto().getId(), courseService.get(COURSE_ID).getId());
        assertEquals(createTestCourseDto().getName(), courseService.get(COURSE_ID).getName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetCourseByWrongId() {
        Mockito.when(courseRepositoryMock.findById(COURSE_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.get(COURSE_ID);
        });
    }

    @Test
    void shouldGetAllCourses() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCourseDto());
        Mockito.when(courseRepositoryMock.findAll()).thenReturn(createTestCoursesList());
        assertEquals(EXPECTED_SIZE_LIST, courseService.getAll().size());
        assertEquals(createTestCourseDto().getId(), courseService.getAll().get(COURSE_INDEX).getId());
        assertEquals(createTestCourseDto().getName(), courseService.getAll().get(COURSE_INDEX).getName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetAllCourses() {
        Mockito.when(courseRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.getAll();
        });
    }

    @Test
    void shouldCreateNewCourse() {
        courseService.create(createTestCourseDto());
        Mockito.verify(courseRepositoryMock).save(any());
    }

    @Test
    void shouldUpdateExpectedCourse() {
        courseService.update(createTestCourseDto());
        Mockito.verify(courseRepositoryMock).save(any());
    }

    @Test
    void shouldDeleteExpectedCourse() {
        courseService.delete(COURSE_ID);
        Mockito.verify(courseRepositoryMock).deleteById(COURSE_ID);
    }

    private Course createTestCourse() {
        return new Course()
                .setId(COURSE_ID)
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION);
    }

    private CourseDto createTestCourseDto() {
        return new CourseDto()
                .setId(COURSE_ID)
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION);
    }

    private List<Course> createTestCoursesList() {
        return List.of(new Course()
                .setId(COURSE_ID)
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION));
    }
}