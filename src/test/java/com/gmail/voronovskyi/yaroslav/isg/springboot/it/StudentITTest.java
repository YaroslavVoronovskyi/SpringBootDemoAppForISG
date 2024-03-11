package com.gmail.voronovskyi.yaroslav.isg.springboot.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.voronovskyi.yaroslav.isg.springboot.config.AppConfigTest;
import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Student;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.StudentRepository;
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
class StudentITTest extends AbstractTestcontainers {

    private final static int STUDENT_ID_FIRST = 1;
    private final static int STUDENT_ID_SECOND = 2;
    private final static int STUDENT_ID_THIRD = 3;
    private final static int STUDENT_ID_FOURTH = 4;
    private final static int STUDENT_ID_FIFTH = 5;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int EXPECTED_SIZE_LIST_TWO_STUDENTS = 2;
    private final static int STUDENT_INDEX = 0;
    private final static String STUDENT_FIRST_NAME = "Yaroslav";
    private final static String STUDENT_FIRST_NAME_FOR_UPDATE = "Matthew";
    private final static String STUDENT_LAST_NAME = "Voronovskyi";
    private final static String HOST = "http://localhost:";
    private final static String STUDENTS_PARAMETER = "/students";
    private final static String PARAMETER_WITH_FIRST_ID = "/students/1";
    private final static String PARAMETER_WITH_THIRD_ID = "/students/3";
    private final static String PARAMETER_WITH_FOURTH_ID = "/students/4";
    private final static String PARAMETER_WITH_FIFTH_ID = "/students/5";

    @LocalServerPort
    private Integer port;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        studentRepository.save(createTestStudent());
    }

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldGetExpectedStudentById() {
        StudentDto studentDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIRST_ID, StudentDto.class);
        assertNotNull(studentDto);
        assertEquals(STUDENT_ID_FIRST, studentDto.getId());
        assertEquals(STUDENT_FIRST_NAME, studentDto.getFirstName());
        assertEquals(STUDENT_LAST_NAME, studentDto.getLastName());
    }

    @Test
    @Order(2)
    void shouldGetAllStudents() throws JsonProcessingException {
        String response = restTemplate.getForObject(HOST + port + STUDENTS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<StudentDto> studentDtoList = mapper.readerForListOf(StudentDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, studentDtoList.size());
        assertEquals(STUDENT_ID_SECOND, studentDtoList.get(STUDENT_INDEX).getId());
        assertEquals(STUDENT_FIRST_NAME, studentDtoList.get(STUDENT_INDEX).getFirstName());
        assertEquals(STUDENT_LAST_NAME, studentDtoList.get(STUDENT_INDEX).getLastName());
    }

    @Test
    @Order(3)
    void shouldCreateNewStudent() throws JsonProcessingException {
        StudentDto studentDto = createTestStudentDto();
        restTemplate.postForEntity(HOST + port + STUDENTS_PARAMETER, studentDto, StudentDto.class);
        String response = restTemplate.getForObject(HOST + port + STUDENTS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<StudentDto> studentDtoList = mapper.readerForListOf(StudentDto.class).readValue(response);
        StudentDto actualStudentDto = restTemplate.getForObject(HOST + port + PARAMETER_WITH_THIRD_ID, StudentDto.class);
        assertEquals(EXPECTED_SIZE_LIST_TWO_STUDENTS, studentDtoList.size());
        assertNotNull(actualStudentDto);
        assertEquals(STUDENT_ID_THIRD, actualStudentDto.getId());
        assertEquals(STUDENT_FIRST_NAME, actualStudentDto.getFirstName());
        assertEquals(STUDENT_LAST_NAME, actualStudentDto.getLastName());
    }

    @Test
    @Order(4)
    void shouldUpdateExpectedStudent() {
        StudentDto studentDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, StudentDto.class);
        assertNotNull(studentDto);
        studentDto.setFirstName(STUDENT_FIRST_NAME_FOR_UPDATE);
        restTemplate.put(HOST + port + PARAMETER_WITH_FIFTH_ID, studentDto);
        StudentDto actualStudentDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, StudentDto.class);
        assertNotNull(actualStudentDto);
        assertEquals(STUDENT_ID_FIFTH, actualStudentDto.getId());
        assertEquals(STUDENT_FIRST_NAME_FOR_UPDATE, actualStudentDto.getFirstName());
        assertEquals(STUDENT_LAST_NAME, actualStudentDto.getLastName());
    }

    @Test
    @Order(5)
    void shouldDeleteExpectedStudent() throws JsonProcessingException {
        restTemplate.delete(HOST + port + PARAMETER_WITH_FOURTH_ID);
        String response = restTemplate.getForObject(HOST + port + STUDENTS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<StudentDto> studentDtoList = mapper.readerForListOf(StudentDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, studentDtoList.size());
        assertFalse(studentDtoList.contains(new StudentDto().setId(STUDENT_ID_FOURTH)));
    }

    private StudentDto createTestStudentDto() {
        return new StudentDto()
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME);
    }

    private Student createTestStudent() {
        return new Student()
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME);
    }
}
