package com.gmail.voronovskyi.yaroslav.isg.springboot.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;

import java.util.List;

public interface GroupService {

    GroupDto create(GroupDto groupDto);

    GroupDto get(int id);

    List<GroupDto> getAll();

    GroupDto update(GroupDto groupDto);

    void delete(int id);
}
