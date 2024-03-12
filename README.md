For run App you fetch all project, use command "docker compose up" in the terminal
of this project? and after that run App, also you should use Java 17

Full documentation you can find there:
http://localhost:8080/swagger-ui/index.html


# Aspect layer Integration with AWS DynamoDB in Java Spring Boot application

At first you need to create in AWS Console DynamoDb Table "SpringBootDemoAppForISG" 
in eu.central-1 Region (Frankfurt) with Partition key - uuid (String).
Also before run application you need edit configuration in your IDE and add environment
variables AWS_ACCESS_KEY_ID - with your AWS IAM User access key and AWS_SECRET_ACCESS_KEY
with your AWS IAM User secret access key, after that you can run application.
You can use http://localhost:8080/swagger-ui/index.html all endpoint and after that open
"SpringBootDemoAppForISG" table and you will that all your api calls were logged with some data.
