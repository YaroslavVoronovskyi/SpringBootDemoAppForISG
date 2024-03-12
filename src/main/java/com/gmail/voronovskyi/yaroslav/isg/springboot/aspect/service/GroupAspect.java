package com.gmail.voronovskyi.yaroslav.isg.springboot.aspect.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.GroupDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GroupAspect {

    @Before("execution(* com.gmail.voronovskyi.yaroslav.isg.springboot.service.GroupService.create(..)) && args(groupDto)")
    public void logCreating(GroupDto groupDto) {
        log.info("Creating product {}", groupDto);
    }
}
