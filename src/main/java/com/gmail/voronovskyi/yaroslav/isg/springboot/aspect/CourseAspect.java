package com.gmail.voronovskyi.yaroslav.isg.springboot.aspect;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.CourseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CourseAspect {

    @Before("execution(* com.gmail.voronovskyi.yaroslav.isg.springboot.service.CourseService.create(..)) && args(courseDto)")
    public void logCreating(CourseDto courseDto) {
        log.info("Creating product {}", courseDto);
    }
}
