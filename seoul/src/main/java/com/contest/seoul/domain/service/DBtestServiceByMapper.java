package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DBtestServiceByMapper {

    private final AmazonDynamoDB amazonDynamoDb;
    private final DynamoDBMapper dynamoDbMapper;

    // 테이블 생성
    public boolean createTableByMapper(){
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(RestaurantItem.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        createTableRequest.getGlobalSecondaryIndexes().forEach(
                idx -> idx
                        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                        .withProjection(new Projection().withProjectionType("ALL"))
        );
        return TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }
    public RestaurantItem saveItemByMapper() {
        RestaurantItem restaurantItem = RestaurantItem.builder()
                .latitude(143.4313)
                .longitude(123.134)
                .upsoSno("003013")
                .upsoNm("동래밀면")
                .siteAddrRd("숭진리 3515번길 ")
                .bdngJisgFlrNum("1")
                .bdngUnderFlrNum("0")
                .geEhYn("비대상")
                .build();
        System.out.println("생성 Item ID : "+ restaurantItem.getId());
        dynamoDbMapper.save(restaurantItem);

        RestaurantItem test = dynamoDbMapper.load(RestaurantItem.class, restaurantItem.getId());

        return test;

    }
}
