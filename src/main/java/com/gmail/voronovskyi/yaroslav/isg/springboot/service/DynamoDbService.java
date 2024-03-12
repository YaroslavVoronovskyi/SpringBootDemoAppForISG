package com.gmail.voronovskyi.yaroslav.isg.springboot.service;


public interface DynamoDbService {

    void storeMessage(String message);
}
