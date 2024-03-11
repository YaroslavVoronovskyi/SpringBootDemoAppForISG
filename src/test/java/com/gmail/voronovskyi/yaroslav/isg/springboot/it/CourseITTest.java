package com.gmail.voronovskyi.yaroslav.isg.springboot.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.voronovskyi.yaroslav.isg.springboot.config.AppConfigTest;
import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Course;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient
@SpringBootTest(classes = AppConfigTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseITTest extends AbstractTestcontainers {

    private final static int COURSE_ID_FIRST = 1;
    private final static int COURSE_ID_SECOND = 2;
    private final static int COURSE_ID_THIRD = 3;
    private final static int COURSE_ID_FOURTH = 4;
    private final static int COURSE_ID_FIFTH = 5;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int EXPECTED_SIZE_LIST_TWO_COURSES = 2;
    private final static int COURSE_INDEX = 0;
    private final static String COURSE_NAME = "Java basic";
    private final static String COURSE_NAME_FOR_UPDATE = "Java advance";
    private final static String COURSE_DESCRIPTION = "Java learning";
    private final static String HOST = "http://localhost:";
    private final static String COURSES_PARAMETER = "/courses";
    private final static String PARAMETER_WITH_FIRST_ID = "/courses/1";
    private final static String PARAMETER_WITH_THIRD_ID = "/courses/3";
    private final static String PARAMETER_WITH_FOURTH_ID = "/courses/4";
    private final static String PARAMETER_WITH_FIFTH_ID = "/courses/5";

    @LocalServerPort
    private Integer port;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        courseRepository.save(createTestCourse());
    }

    @AfterEach
    public void afterEach() {
        courseRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldGetExpectedCourseById() {
        CourseDto courseDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIRST_ID, CourseDto.class);
        assertNotNull(courseDto);
        assertEquals(COURSE_ID_FIRST, courseDto.getId());
        assertEquals(COURSE_NAME, courseDto.getName());
        assertEquals(COURSE_DESCRIPTION, courseDto.getDescription());
    }

    @Test
    @Order(2)
    void shouldGetAllCourses() throws JsonProcessingException {
        String response = restTemplate.getForObject(HOST + port + COURSES_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDto> courseDtoList = mapper.readerForListOf(CourseDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, courseDtoList.size());
        assertEquals(COURSE_ID_SECOND, courseDtoList.get(COURSE_INDEX).getId());
        assertEquals(COURSE_NAME, courseDtoList.get(COURSE_INDEX).getName());
        assertEquals(COURSE_DESCRIPTION, courseDtoList.get(COURSE_INDEX).getDescription());
    }

    @Test
    @Order(3)
    void shouldCreateNewCourse() throws JsonProcessingException {
        CourseDto courseDto = createTestCourseDto();
        restTemplate.postForEntity(HOST + port + COURSES_PARAMETER, courseDto, CourseDto.class);
        String response = restTemplate.getForObject(HOST + port + COURSES_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDto> courseDtoList = mapper.readerForListOf(CourseDto.class).readValue(response);
        CourseDto actualCourseDto = restTemplate.getForObject(HOST + port + PARAMETER_WITH_THIRD_ID, CourseDto.class);
        assertEquals(EXPECTED_SIZE_LIST_TWO_COURSES, courseDtoList.size());
        assertNotNull(actualCourseDto);
        assertEquals(COURSE_ID_THIRD, actualCourseDto.getId());
        assertEquals(COURSE_NAME, actualCourseDto.getName());
        assertEquals(COURSE_DESCRIPTION, actualCourseDto.getDescription());
    }

    @Test
    @Order(4)
    void shouldUpdateExpectedCourse() {
        CourseDto courseDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, CourseDto.class);
        assertNotNull(courseDto);
        courseDto.setName(COURSE_NAME_FOR_UPDATE);
        restTemplate.put(HOST + port + PARAMETER_WITH_FIFTH_ID, courseDto);
        CourseDto actualCourseDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, CourseDto.class);
        assertNotNull(actualCourseDto);
        assertEquals(COURSE_ID_FIFTH, actualCourseDto.getId());
        assertEquals(COURSE_NAME_FOR_UPDATE, actualCourseDto.getName());
        assertEquals(COURSE_DESCRIPTION, actualCourseDto.getDescription());
    }

    @Test
    @Order(5)
    void shouldDeleteExpectedCourse() throws JsonProcessingException {
        restTemplate.delete(HOST + port + PARAMETER_WITH_FOURTH_ID);
        String response = restTemplate.getForObject(HOST + port + COURSES_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDto> courseDtoList = mapper.readerForListOf(CourseDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, courseDtoList.size());
        assertFalse(courseDtoList.contains(new Course().setId(COURSE_ID_FOURTH)));
    }

    private CourseDto createTestCourseDto() {
        return new CourseDto()
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION);
    }

    private Course createTestCourse() {
        return new Course()
                .setName(COURSE_NAME)
                .setDescription(COURSE_DESCRIPTION);
    }
}
