package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DBtestServiceBySDK {
    final RestaurantRepository restaurantRepository;
    final DynamoDBMapper mapper;
    private final AmazonDynamoDB amazonDynamoDb;
    private final DynamoDBMapper dynamoDBMapper;
    public RestaurantItem test(){
        return null;
    }

    public boolean createTable_ValidInput_TableHasBeenCreated() {
        System.out.println("테이블 생성 테스트 시작");
        CreateTableRequest createTableRequest = (new CreateTableRequest())
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.S),
                        new AttributeDefinition("mentionId", ScalarAttributeType.N),
                        new AttributeDefinition("createdAt", ScalarAttributeType.S)
                ).withTableName("Comment").withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                ).withGlobalSecondaryIndexes(
                        (new GlobalSecondaryIndex())
                                .withIndexName("byMentionId")
                                .withKeySchema(
                                        new KeySchemaElement("mentionId", KeyType.HASH),
                                        new KeySchemaElement("createdAt", KeyType.RANGE))
                                .withProjection(
                                        (new Projection()).withProjectionType(ProjectionType.ALL))
                                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                ).withProvisionedThroughput(
                        new ProvisionedThroughput(1L, 1L)
                );

        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
        System.out.println("테이블 생성 결과  :  "+hasTableBeenCreated);
        return hasTableBeenCreated;
    }
    public int putItem_ShouldBeCalledAfterTableCreation_StatusOk() {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", (new AttributeValue()).withS("uuid"));
        item.put("mentionId", (new AttributeValue()).withN("1"));
        item.put("content", (new AttributeValue()).withS("comment content"));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS("1836-03-07T02:21:30.536Z"));

        PutItemRequest putItemRequest = (new PutItemRequest())
                .withTableName("Comment")
                .withItem(item);

        PutItemResult putItemResult = amazonDynamoDb.putItem(putItemRequest);
        System.out.println("Status : "+putItemResult.getSdkHttpMetadata().getHttpStatusCode());
        return putItemResult.getSdkHttpMetadata().getHttpStatusCode();
    }
}
