package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBtestService {
    final RestaurantRepository restaurantRepository;
    final DynamoDBMapper mapper;
    private final AmazonDynamoDB amazonDynamoDb;
    public RestaurantItem test(){


        RestaurantItem item = new RestaurantItem();
        item.setLatitude(31.3515);
        item.setLongitude(12.52423);
//        item.setUpsoNm("앨리스의 마켓");
//        item.setSntCobNm("건강기능식품일반판매업");
//        item.setSntUptaeNm("전자상거래");
//        item.setSiteAddrRd("장전온천천로");
//        item.setBdngJisgFlrNum(0);
//        item.setBdngUnderFlrNum(0);
//        item.setUpsoSno("123");
//        item.setCggCode("124153");
//        item.setGeEhYn("false");
        System.out.println(item.toString());
        mapper.save(item);
//        restaurantRepository.saveRestaurant(item);


        // 쿼리
//        RestaurantItem partitionKey = new RestaurantItem();
//
//        partitionKey.setLatutide(31.3515);
//        DynamoDBQueryExpression<RestaurantItem> queryExpression = new DynamoDBQueryExpression<RestaurantItem>()
//                .withHashKeyValues(partitionKey);
//
//        List<RestaurantItem> itemList = mapper.query(RestaurantItem.class, queryExpression);
//
//        for (int i = 0; i < itemList.size(); i++) {
//            System.out.println(itemList.get(i).getLatutide());
//            System.out.println(itemList.get(i).getCggCode());
//        }
//        CreateTableRequest createTableRequest = new CreateTableRequest()
//                .withTableName("testtable")
//                .withKeySchema(
//                        new KeySchemaElement("id", KeyType.HASH)
//                )
//                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
//                .withAttributeDefinitions(
//                        new AttributeDefinition("id", "S")
//                );
//
//        CreateTableResult result = amazonDynamoDB.createTable(createTableRequest);
        return item;
    }
    public RestaurantItem getTest(){
        return restaurantRepository.getRestaurantByLatitude(123.1, 123.1);
    }
    public boolean createTable_ValidInput_TableHasBeenCreated() {
        System.out.println("테이블 생성 테스트 시작");
//        CreateTableRequest createTableRequest = (new CreateTableRequest())
//                .withAttributeDefinitions(
//                        new AttributeDefinition("longitude", ScalarAttributeType.N),
//                        new AttributeDefinition("latitude", ScalarAttributeType.N),
//                        new AttributeDefinition("upsoNm", ScalarAttributeType.S)
//                ).withTableName("Test").withKeySchema(
//                        new KeySchemaElement("longitude", KeyType.HASH),
//                        new KeySchemaElement("latitude", KeyType.RANGE)
//                ).withGlobalSecondaryIndexes(
//                        (new GlobalSecondaryIndex())
//                                .withIndexName("upsoNm")
//                                .withKeySchema(
//                                        new KeySchemaElement("upsoNm", KeyType.HASH))
//                                .withProjection(
//                                        (new Projection()).withProjectionType(ProjectionType.ALL))
//                                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
//                .withProvisionedThroughput(
//                        new ProvisionedThroughput(1L, 1L))
//                );
//
//        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
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
}
