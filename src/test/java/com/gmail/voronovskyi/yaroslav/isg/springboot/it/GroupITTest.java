package com.gmail.voronovskyi.yaroslav.isg.springboot.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.voronovskyi.yaroslav.isg.springboot.config.AppConfigTest;
import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Group;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.GroupRepository;
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
class GroupITTest extends AbstractTestcontainers {

    private final static int GROUP_ID_FIRST = 1;
    private final static int GROUP_ID_SECOND = 2;
    private final static int GROUP_ID_THIRD = 3;
    private final static int GROUP_ID_FOURTH = 4;
    private final static int GROUP_ID_FIFTH = 5;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int EXPECTED_SIZE_LIST_TWO_GROUP = 2;
    private final static int GROUP_INDEX = 0;
    private final static String GROUP_NAME = "FF-22";
    private final static String GROUP_NAME_FOR_UPDATE = "FF-33";
    private final static String HOST = "http://localhost:";
    private final static String GROUPS_PARAMETER = "/groups";
    private final static String PARAMETER_WITH_FIRST_ID = "/groups/1";
    private final static String PARAMETER_WITH_THIRD_ID = "/groups/3";
    private final static String PARAMETER_WITH_FOURTH_ID = "/groups/4";
    private final static String PARAMETER_WITH_FIFTH_ID = "/groups/5";

    @LocalServerPort
    private Integer port;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        groupRepository.save(createTestGroup());
    }

    @AfterEach
    public void afterEach() {
        groupRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldGetExpectedGroupById() {
        GroupDto groupDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIRST_ID, GroupDto.class);
        assertNotNull(groupDto);
        assertEquals(GROUP_ID_FIRST, groupDto.getId());
        assertEquals(GROUP_NAME, groupDto.getName());
    }

    @Test
    @Order(2)
    void shouldGetAllGroups() throws JsonProcessingException {
        String response = restTemplate.getForObject(HOST + port + GROUPS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<GroupDto> groupDtoList = mapper.readerForListOf(GroupDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, groupDtoList.size());
        assertEquals(GROUP_ID_SECOND, groupDtoList.get(GROUP_INDEX).getId());
        assertEquals(GROUP_NAME, groupDtoList.get(GROUP_INDEX).getName());
    }

    @Test
    @Order(3)
    void shouldCreateNewGroup() throws JsonProcessingException {
        GroupDto groupDto = createTestGroupDto();
        restTemplate.postForEntity(HOST + port + GROUPS_PARAMETER, groupDto, GroupDto.class);
        String response = restTemplate.getForObject(HOST + port + GROUPS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<GroupDto> groupDtoList = mapper.readerForListOf(GroupDto.class).readValue(response);
        GroupDto actualGroupDto = restTemplate.getForObject(HOST + port + PARAMETER_WITH_THIRD_ID, GroupDto.class);
        assertEquals(EXPECTED_SIZE_LIST_TWO_GROUP, groupDtoList.size());
        assertNotNull(actualGroupDto);
        assertEquals(GROUP_ID_THIRD, actualGroupDto.getId());
        assertEquals(GROUP_NAME, actualGroupDto.getName());
    }

    @Test
    @Order(4)
    void shouldUpdateExpectedGroup() {
        GroupDto groupDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, GroupDto.class);
        assertNotNull(groupDto);
        groupDto.setName(GROUP_NAME_FOR_UPDATE);
        restTemplate.put(HOST + port + PARAMETER_WITH_FIFTH_ID, groupDto);
        GroupDto actualGroupDto =
                restTemplate.getForObject(HOST + port + PARAMETER_WITH_FIFTH_ID, GroupDto.class);
        assertNotNull(actualGroupDto);
        assertEquals(GROUP_ID_FIFTH, actualGroupDto.getId());
        assertEquals(GROUP_NAME_FOR_UPDATE, actualGroupDto.getName());
    }

    @Test
    @Order(5)
    void shouldDeleteExpectedGroup() throws JsonProcessingException {
        restTemplate.delete(HOST + port + PARAMETER_WITH_FOURTH_ID);
        String response = restTemplate.getForObject(HOST + port + GROUPS_PARAMETER, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<GroupDto> groupDtoList = mapper.readerForListOf(GroupDto.class).readValue(response);
        assertEquals(EXPECTED_SIZE_LIST, groupDtoList.size());
        assertFalse(groupDtoList.contains(new GroupDto().setId(GROUP_ID_FOURTH)));
    }

    private GroupDto createTestGroupDto() {
        return new GroupDto()
                .setName(GROUP_NAME);
    }

    private Group createTestGroup() {
        return new Group()
                .setName(GROUP_NAME);
    }
}
