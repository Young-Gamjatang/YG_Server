package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
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
}
