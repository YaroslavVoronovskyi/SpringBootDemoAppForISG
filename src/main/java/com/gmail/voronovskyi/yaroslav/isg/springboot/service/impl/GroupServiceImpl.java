package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.exception.NotValidDataException;
import com.gmail.voronovskyi.yaroslav.isg.springboot.model.Group;
import com.gmail.voronovskyi.yaroslav.isg.springboot.repository.GroupRepository;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GroupDto create(GroupDto groupDto) {
        log.debug("Try create new Group and save in DB");
        try {
            Group group = groupRepository.save(convertToEntity(groupDto));
            return convertToDto(group);
        } catch (DataIntegrityViolationException exception) {
            throw new NotValidDataException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto get(int id) {
        log.debug("Try get Group wih id {} from DB", id);
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Group not found with ID " + id));
        return convertToDto(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDto> getAll() {
        log.debug("Try get all Groups from DB");
        List<Group> groupsList = groupRepository.findAll();
        if (groupsList.isEmpty()) {
            throw new EntityNotFoundException("Groups not fount!");
        }
        log.debug("All Group was successfully got from DB");
        return convertToDtoList(groupsList);
    }

    @Override
    @Transactional
    public GroupDto update(GroupDto groupDto) {
        log.debug("Try update Group wih id {} from DB", groupDto.getId());
        Group group = groupRepository.save(convertToEntity(groupDto));
        return convertToDto(group);
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Try delete Group wih id {} from DB", id);
        try {
            groupRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Group with id " + id + " does not exist or has been deleted");
        }
        log.debug("Group wih id {} was successfully deleted from DB", id);
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }

    private List<GroupDto> convertToDtoList(List<Group> groupsList) {
        return groupsList.stream()
                .map(this::convertToDto)
                .toList();
    }
}
