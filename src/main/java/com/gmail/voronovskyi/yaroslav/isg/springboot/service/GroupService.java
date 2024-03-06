package com.gmail.voronovskyi.yaroslav.isg.springboot.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    GroupDto create(GroupDto groupDto);

    Optional<GroupDto> get(int id);

    List<GroupDto> getAll();

    GroupDto update(GroupDto groupDto);

    void delete(int id);
}
