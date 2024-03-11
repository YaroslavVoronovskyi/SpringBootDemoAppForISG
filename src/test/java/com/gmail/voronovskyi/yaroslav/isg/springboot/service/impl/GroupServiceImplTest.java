package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Group;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.GroupRepository;
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
class GroupServiceImplTest {
    private final static int GROUP_ID = 1;
    private final static int EXPECTED_SIZE_LIST = 1;
    private final static int GROUP_INDEX = 0;
    private final static String GROUP_NAME = "FF-22";

    @Mock
    private GroupRepository groupRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void shouldGetExpectedGroupById() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestGroupDto());
        Mockito.when(groupRepositoryMock.findById(GROUP_ID)).thenReturn(Optional.ofNullable(createTestGroup()));
        assertEquals(createTestGroupDto().getId(), groupService.get(GROUP_ID).getId());
        assertEquals(createTestGroupDto().getName(), groupService.get(GROUP_ID).getName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetGroupByWrongId() {
        Mockito.when(groupRepositoryMock.findById(GROUP_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            groupService.get(GROUP_ID);
        });
    }

    @Test
    void shouldGetAllGroups() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestGroupDto());
        Mockito.when(groupRepositoryMock.findAll()).thenReturn(createTestGroupsList());
        assertEquals(EXPECTED_SIZE_LIST, groupService.getAll().size());
        assertEquals(createTestGroupDto().getId(), groupService.getAll().get(GROUP_INDEX).getId());
        assertEquals(createTestGroupDto().getName(), groupService.getAll().get(GROUP_INDEX).getName());
    }

    @Test
    void shouldThrowExceptionWhenTryGetAllGroups() {
        Mockito.when(groupRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> {
            groupService.getAll();
        });
    }

    @Test
    void shouldCreateNewGroup() {
        groupService.create(createTestGroupDto());
        Mockito.verify(groupRepositoryMock).save(any());
    }

    @Test
    void shouldUpdateExpectedGroup() {
        groupService.update(createTestGroupDto());
        Mockito.verify(groupRepositoryMock).save(any());
    }

    @Test
    void shouldDeleteExpectedGroup() {
        groupService.delete(GROUP_ID);
        Mockito.verify(groupRepositoryMock).deleteById(GROUP_ID);
    }


    private Group createTestGroup() {
        return new Group()
                .setId(GROUP_ID)
                .setName(GROUP_NAME);
    }

    private GroupDto createTestGroupDto() {
        return new GroupDto()
                .setId(GROUP_ID)
                .setName(GROUP_NAME);
    }

    private List<Group> createTestGroupsList() {
        return List.of(new Group()
                .setId(GROUP_ID)
                .setName(GROUP_NAME));
    }
}