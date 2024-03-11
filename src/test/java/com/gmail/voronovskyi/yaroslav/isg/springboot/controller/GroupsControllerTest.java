package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GroupsControllerTest {

    private final static int GROUP_ID = 1;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int GROUP_INDEX = 0;
    private final static String GROUP_NAME = "FF-22";

    @Mock
    private GroupService groupServiceMock;
    @InjectMocks
    private GroupsController groupsController;

    @Test
    void shouldGetExpectedGroupById() {
        Mockito.when(groupServiceMock.get(GROUP_ID)).thenReturn(createTestGroupDto());
        assertEquals(createTestGroupDto().getId(), groupsController.get(GROUP_ID).getId());
        assertEquals(createTestGroupDto().getName(), groupsController.get(GROUP_ID).getName());

    }

    @Test
    void shouldGetAllGroups() {
        Mockito.when(groupServiceMock.getAll()).thenReturn(createTestGroupsDtoList());
        assertEquals(EXPECTED_SIZE_LIST, groupsController.getAll().size());
        assertEquals(createTestGroupDto().getId(), groupsController.getAll().get(GROUP_INDEX).getId());
        assertEquals(createTestGroupDto().getName(), groupsController.getAll().get(GROUP_INDEX).getName());
    }

    @Test
    void shouldCreateNewGroup() {
        groupsController.create(createTestGroupDto());
        Mockito.verify(groupServiceMock).create(createTestGroupDto());
    }

    @Test
    void shouldUpdateExpectedGroup() {
        groupsController.update(GROUP_ID, createTestGroupDto());
        Mockito.verify(groupServiceMock).update(createTestGroupDto());
    }

    @Test
    void shouldDeleteExpectedGroup() {
        groupsController.delete(GROUP_ID);
        Mockito.verify(groupServiceMock).delete(GROUP_ID);
    }

    private GroupDto createTestGroupDto() {
        return new GroupDto()
                .setId(GROUP_ID)
                .setName(GROUP_NAME);
    }

    private List<GroupDto> createTestGroupsDtoList() {
        return List.of(new GroupDto()
                .setId(GROUP_ID)
                .setName(GROUP_NAME));
    }
}