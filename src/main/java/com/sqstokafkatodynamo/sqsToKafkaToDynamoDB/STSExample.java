/*
package com.sqstokafkatodynamo.sqsToKafkaToDynamoDB;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;

public class STSExample {

    static void main(String[] args) {
        String roleArn = "arn:aws:iam::123456789012:role/YourRoleName";
        String roleSessionName = "YourSessionName";

        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create("your_access_key_id", "your_secret_access_key"));

        StsClient stsClient = StsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        AssumeRoleRequest assumeRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName(roleSessionName)
                .build();

        AssumeRoleResponse response = stsClient.assumeRole(assumeRequest);

        Credentials sessionCredentials = response.credentials();

        AwsSessionCredentials awsSessionCredentials = AwsSessionCredentials.create(
                sessionCredentials.accessKeyId(),
                sessionCredentials.secretAccessKey(),
                sessionCredentials.sessionToken());

        System.out.println("Session Token: " + sessionCredentials.sessionToken());
        System.out.println("Expiration: " + sessionCredentials.expiration());
}
*/
