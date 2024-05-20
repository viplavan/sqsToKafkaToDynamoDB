package com.sqstokafkatodynamo.sqsToKafkaToDynamoDB.router;

import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.services.s3.model.PutObjectRequest.*;

@Component
public class SQSAndKafkaToDynamoDBRoute extends EndpointRouteBuilder {

      private String accessKey = "AKIA6ODU227GGR63JVXA";
      private String secretKey = "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";

    @Override
    public void configure() throws Exception {


       DynamoDbClient dBClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,secretKey)))
                .build();
                getCamelContext().getRegistry().bind("client", dBClient);

        // Create S3 client
        /*S3Client s3Client = S3Client.create();*/
        S3Client s3Client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.US_EAST_1).build();
        getCamelContext().getRegistry().bind("client", s3Client);

            // Route from SQS to Kafka
        System.out.println("CamelContext routers about to add.");
        from(aws2Sqs("{{sqs-queue-name}}").deleteAfterRead(true).autoCreateQueue(true))
                .log("Received message from sourceQueue: ${body}")
                .to("kafka:codeDecode-Topic1?brokers=localhost:9092")
                .log("Message sent to Kafka topic: ${body}")
                .doTry()
                .process(exchange -> {
                    // Extract data from Kafka message
                    String kafkaMessage = exchange.getIn().getBody(String.class);
                    String[] parts = kafkaMessage.split(",");
                    if (parts.length != 2) {
                        // Log an error if message format is incorrect
                        String errorMessage = "Invalid message format: " + kafkaMessage;
                        exchange.setException(new IllegalArgumentException(errorMessage));
                        return;
                    }

                    String orderId = parts[0].trim();
                    String message = parts[1].trim();

                    // Create a map representing the DynamoDB item
                    Map<String, AttributeValue> item = new HashMap<>();
                    item.put("OrderId", AttributeValue.builder().s(orderId).build()); // Ensure attribute name matches the DynamoDB table schema
                    item.put("Message", AttributeValue.builder().s(message).build());

                    // Create a PutItemRequest
                    PutItemRequest putItemRequest = PutItemRequest.builder()
                            .tableName("myDynamoDBTable") // Replace tableName with your DynamoDB table name
                            .item(item)
                            .build();

                    // Send the PutItem request to DynamoDB
                    try {
                        dBClient.putItem(putItemRequest);
                        System.out.println("Successfully stored item in DynamoDB.");
                    } catch (DynamoDbException e) {
                        System.err.println("Error storing item in DynamoDB: " + e.getMessage());
                    }
                  /*  // Upload the message to S3
                    try {
                        String bucketName = "kafkatos3bucket1"; // Replace with your S3 bucket name
                        String key = orderId + ".txt"; // Use OrderId as the file name

                        // Create a PutObjectRequest instance using the builder pattern
                        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build();
                        s3Client.putObject(putObjectRequest, message);
                        System.out.println("Successfully stored item in S3.");
                    } catch (S3Exception e) {
                        System.err.println("Error storing item in S3: " + e.getMessage());
                    }*/
                })
                .doCatch(Exception.class)
                .log("Error occurred: ${exception.message}")
                .end()
               .log("Message saved to DynamoDB: ${body}");

    }
}
