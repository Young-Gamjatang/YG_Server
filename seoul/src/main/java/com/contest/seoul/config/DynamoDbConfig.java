//package com.contest.seoul.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
//
//@Configuration
//public class DynamoDbConfig {
//    @Value("${amazon.aws.access-key}")
//    private String accessKey;
//
//    @Value("${amazon.aws.secret-key}")
//    private String secretKey;
//
//    @Value("${amazon.aws.region}")
//    private String region;
//
//    @Value("${amazon.aws.dynamodb.endpoint}")
//    private String dynamoDBEndpoint;
//
//    @Bean
//    public DynamoDBMapper dynamoDBMapper() {
//        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
//                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
//                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
//                .withTableNameOverride(null)
//
//                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
//                .build();
//
//        return new DynamoDBMapper(amazonDynamoDB(), mapperConfig);
//    }
//
//    @Primary
//    @Bean
//    public AWSCredentialsProvider awsCredentialsProvider() {
//        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey));
//    }
//
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        System.out.println("설정완료");
//        AmazonDynamoDB a;
//
//        return AmazonDynamoDBClientBuilder
//                .standard()
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, region)
//                )
//                .withCredentials(awsCredentialsProvider())
//                .build();
//    }
//
//}