package com.gmail.voronovskyi.yaroslav.isg.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StudentDto {

    private int id;

    @Size(min = 2, max = 20, message = "can not be less then 2 and more then 20 characters")
    @NotBlank(message = " can not be null or empty")
    private String firstName;

    @Size(min = 2, max = 20, message = "can not be less then 2 and more then 20 characters")
    @NotBlank(message = " can not be null or empty")
    private String lastName;

    private GroupDto sgroupDto;
}
