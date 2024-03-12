package com.gmail.voronovskyi.yaroslav.isg.springboot.aspect.controller;

import com.gmail.voronovskyi.yaroslav.isg.springboot.service.DynamoDbService;
import com.gmail.voronovskyi.yaroslav.isg.springboot.util.DataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class StudentControllerAspect {

    private static final int MIN_SIZE = 1;
    private static final String API_POINT_CUT =
            "execution(* com.gmail.voronovskyi.yaroslav.isg.springboot.controller.StudentsController.*(..))";
    private static final String REQUEST_MESSAGE = " request values ";
    private static final String RESPONSE_MESSAGE = " response values ";
    private static final String MESSAGE_IF_METHOD_WITHOUT_PARAMETERS = " method don`t have parameters ";
    private static final String API_POINT_CUT_PARAMETER = "studentLogController()";
    private static final String EXCEPTION = "exception";
    private static final String DELIMITER = " ";

    private final DynamoDbService dynamoDbService;

    @Pointcut(API_POINT_CUT)
    public void studentLogController() {
    }

    @Before(API_POINT_CUT_PARAMETER)
    public void logRequest(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder()
                .append(joinPoint.getSignature())
                .append(DELIMITER)
                .append(joinPoint.getSignature().getName())
                .append(DELIMITER)
                .append(joinPoint.getKind())
                .append(DELIMITER)
                .append(createJoinPointForLogs(joinPoint, DataType.REQUEST))
                .append(DELIMITER)
                .append(LocalDateTime.now());
        dynamoDbService.storeMessage(message.toString());
    }

    @AfterReturning(API_POINT_CUT_PARAMETER)
    public void logsResponse(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder()
                .append(joinPoint.getSignature())
                .append(DELIMITER)
                .append(joinPoint.getSignature().getName())
                .append(DELIMITER)
                .append(joinPoint.getKind())
                .append(DELIMITER)
                .append(createJoinPointForLogs(joinPoint, DataType.RESPONSE))
                .append(DELIMITER)
                .append(LocalDateTime.now());
        dynamoDbService.storeMessage(message.toString());
    }

    @AfterThrowing(value = API_POINT_CUT_PARAMETER, throwing = EXCEPTION)
    public void logsErrors(JoinPoint joinPoint, Throwable exception) {
        StringBuilder message = new StringBuilder()
                .append(joinPoint.getSignature())
                .append(DELIMITER)
                .append(joinPoint.getSignature().getName())
                .append(DELIMITER)
                .append(joinPoint.getKind())
                .append(DELIMITER)
                .append(exception.getMessage())
                .append(DELIMITER)
                .append(LocalDateTime.now());
        dynamoDbService.storeMessage(message.toString());
    }

    private String createJoinPointForLogs(JoinPoint joinPoint, DataType dataType) {
        if (joinPoint.getArgs().length < MIN_SIZE) {
            return joinPoint.getSignature().getName()
                    .concat(MESSAGE_IF_METHOD_WITHOUT_PARAMETERS);
        }
        Object[] objectsArray = joinPoint.getArgs();
        StringBuilder value = new StringBuilder();
        if (dataType.equals(DataType.REQUEST)) {
            value
                    .append(DELIMITER)
                    .append(REQUEST_MESSAGE);
        } else {
            value
                    .append(DELIMITER)
                    .append(RESPONSE_MESSAGE);
        }
        Arrays.stream(objectsArray)
                .forEach(element -> value
                        .append(element.toString())
                        .append(DELIMITER));
        return value.toString();
    }
}
