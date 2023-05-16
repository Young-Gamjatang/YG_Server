package com.contest.seoul.domain.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {
//    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private final DynamoDBMapper dynamoDBMapper;
    // 생성
    public RestaurantItem saveRestaurant(RestaurantItem restaurantItem) {
        System.out.println(dynamoDBMapper);
        dynamoDBMapper.save(restaurantItem);
        return restaurantItem;
    }

    // 조회
    public RestaurantItem getRestaurantByLatitude(Double latitude, Double longitude) {

        return dynamoDBMapper.load(RestaurantItem.class, latitude, longitude);
    }

    // 삭제
    public String deleteRestaurantByLatitude(Double latitude) {
        dynamoDBMapper.delete(dynamoDBMapper.load(RestaurantItem.class, latitude));
        return "latitude : "+ latitude +" Deleted!";
    }

    // 수정
    public Double updateRestaurant(RestaurantItem restaurantItem) {
        dynamoDBMapper.save(restaurantItem,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("latutude",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(restaurantItem.getLatitude().toString())
                                )));
        return restaurantItem.getLatitude();
    }

}
