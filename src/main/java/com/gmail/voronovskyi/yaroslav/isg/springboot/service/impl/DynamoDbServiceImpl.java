package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.service.DynamoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DynamoDbServiceImpl implements DynamoDbService {

    private static final String TABLE_NAME = "SpringBootDemoAppForISG";
    private static final String ID_PATTERN = "uuid";
    private static final String MESSAGE = "message";
    private final DynamoDbClient dynamoDbClient;

    @Override
    public void storeMessage(String message) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(ID_PATTERN, AttributeValue.fromS(UUID.randomUUID().toString()));
        item.put(MESSAGE, AttributeValue.fromS(message));
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();
        dynamoDbClient.putItem(putItemRequest);
    }
}
