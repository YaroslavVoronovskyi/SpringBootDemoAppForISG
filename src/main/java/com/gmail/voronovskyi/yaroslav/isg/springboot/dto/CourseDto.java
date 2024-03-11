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
public class CourseDto {

    private int id;

    @Size(min = 4, max = 30, message = "can not be less then 4 and more then 30 characters")
    @NotBlank(message = " can not be null or empty")
    private String name;

    @Size(min = 1, max = 300, message = "can not be less then 1 and more then 300 characters")
    @NotBlank(message = " can not be null or empty")
    private String description;

    private List<GroupDto> sgroupsDtoList;
}
