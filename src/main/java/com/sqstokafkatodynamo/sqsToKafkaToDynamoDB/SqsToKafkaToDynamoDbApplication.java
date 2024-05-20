package com.sqstokafkatodynamo.sqsToKafkaToDynamoDB;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqsToKafkaToDynamoDbApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(SqsToKafkaToDynamoDbApplication.class, args);
		/*SqsConfig sqsClient = new SqsConfig();

		DynamoDBConfiguration ddbClient = new DynamoDBConfiguration();

		// Create Camel context
		CamelContext camelContext = new DefaultCamelContext();

		// Register SQS client with Camel
		camelContext.getRegistry().bind("sqsClient", sqsClient);

		// Register DynamoDB client with Camel
		camelContext.getRegistry().bind("ddbClient", ddbClient);

		/// Create an instance of SQSRouteBuilder and pass the SQS client
		SQSAndKafkaToDynamoDBRoute sqsRouteBuilder = new SQSAndKafkaToDynamoDBRoute();

		// Add Camel routes
		camelContext.addRoutes(sqsRouteBuilder);

		// Start Camel context
		camelContext.start();

		// Keep the application running until terminated
		Thread.sleep(Long.MAX_VALUE);

		// Shutdown Camel context when terminating
		camelContext.stop();*/
	}

}
