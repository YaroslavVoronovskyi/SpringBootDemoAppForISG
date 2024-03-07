package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.GroupService;
import com.gmail.voronovskyi.yaroslav.isg.springboot.util.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {

    private final GroupService groupService;

    @GetMapping("/{id}")
    public GroupDto get(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try get group wih id {}", id);
        GroupDto groupDto = groupService.get(id);
        log.debug("Group wih id {} was successfully got", id);
        return groupDto;
    }

    @GetMapping()
    public List<GroupDto> getAll() {
        log.debug("Try get all groups");
        List<GroupDto> groupsDtoList = groupService.getAll();
        log.debug("All groups was successfully got");
        return groupsDtoList;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto create(@RequestBody @Valid GroupDto groupDto) {
        log.debug("Try create new group");
        return  groupService.create(groupDto);
    }

    @PutMapping("/{id}")
    public GroupDto update(@PathVariable(Constants.ENTITY_ID) int id, @RequestBody @Valid GroupDto groupDto) {
        log.debug("Try update group wih id {}", id);
        groupDto.setId(id);
        return  groupService.update(groupDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(Constants.ENTITY_ID) int id) {
        log.debug("Try delete group wih id {}", id);
        groupService.delete(id);
        log.debug("Group was deleted wih id {}", id);
    }
}
