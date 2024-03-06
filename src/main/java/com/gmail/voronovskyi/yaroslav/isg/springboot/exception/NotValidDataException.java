package com.gmail.voronovskyi.yaroslav.isg.springboot.exception;

public class NotValidDataException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Not valid data, try again";
    }
}
