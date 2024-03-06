package com.gmail.voronovskyi.yaroslav.isg.springboot.aspect;

import com.gmail.voronovskyi.yaroslav.isg.springboot.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class StudentAspect {

    @Before("execution(* com.gmail.voronovskyi.yaroslav.isg.springboot.service.StudentService.create(..)) && args(studentDto)")
    public void logCreating(StudentDto studentDto) {
        log.info("Creating product {}", studentDto);
    }
}
