package com.gmail.voronovskyi.yaroslav.isg.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
public class GroupDto {

    private int id;

    @Size(min = 4, max = 20, message = "can not be less then 4 and more then 20 characters")
    @NotBlank(message = " can not be null or empty")
    private String name;

    private List<StudentDto> studentsDtoList;

    private List<CourseDto> coursesDtoList;
}
