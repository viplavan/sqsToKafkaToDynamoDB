/*
package com.sqstokafkatodynamo.sqsToKafkaToDynamoDB;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBClientFactory {

    public static DynamoDbClient createClient() {

        AwsSessionCredentials sessionCredentials = AwsSessionCredentials.create("AKIA6ODU227GGR63JVXA", "vXx1aMBarhNuz3LIsMaaZd4TLEFBvZYBNR/eD7eD", "sessionToken");
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
*/
