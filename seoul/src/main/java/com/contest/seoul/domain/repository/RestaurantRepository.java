package com.contest.seoul.domain.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {
    private final AmazonDynamoDB amazonDynamoDB;
    private final DynamoDBMapper dynamoDBMapper;
    // 생성
    public RestaurantItem saveRestaurant(RestaurantItem restaurantItem) {
        System.out.println(dynamoDBMapper);
        dynamoDBMapper.save(restaurantItem);
        return restaurantItem;
    }
    // 글로벌 인덱스 cggCode 조회
    public List<Map<String,Object>> findByCggCode(String cggCode){
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("restaurantItem");
        Index index = table.getIndex("cggCode-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("cggCode = :v_code")
                .withValueMap(new ValueMap()
                        .withString(":v_code",cggCode));

        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();
        List<Map<String,Object>> restaurantItem = new ArrayList<>();

        while (iter.hasNext()) {
            restaurantItem.add(iter.next().asMap());
        }
        return restaurantItem;
    }
    public List<Map<String,Object>> findByUpsoNM(String upsoNM){
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("wrongrestaurant");
        Index index = table.getIndex("upsoNm-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("upsoNm = :v_upsoNm")
                .withValueMap(new ValueMap()
                        .withString(":v_upsoNm", upsoNM));

        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();
        List<Map<String,Object>> restaurantItem = new ArrayList<>();

        while (iter.hasNext()) {
            restaurantItem.add(iter.next().asMap());
        }
        return restaurantItem;
    }
    public List<RestaurantItem> findAll(){
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("restaurantItem");
        int total = 0;
        ScanResult result = amazonDynamoDB.scan(scanRequest);
        List<RestaurantItem> restaurantItems = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.getItems()){
            // 여기서 모든 데이터 삽입 시키면 Scan 가능 !
            RestaurantItem restaurantItem = new RestaurantItem();
            restaurantItem.setCggCode(String.valueOf(item.get("cggCode")));
            restaurantItem.setSiteAddress(String.valueOf(item.get("siteAddress")));
            restaurantItem.setSiteAddressRd(String.valueOf(item.get("siteAddressRd")));
            restaurantItems.add(restaurantItem);
            total ++;
        }
//
//        List<RestaurantItem> sorted = restaurantItems.stream().distinct().collect(Collectors.toList());
//        System.out.println(total+","+sorted.size());
//        for(int i=0; i< sorted.size(); i++) {
//            System.out.println("시군구 코드 : "+sorted.get(i).getCggCode() +" : "+sorted.get(i).getSiteAddress()+ " : "+ sorted.get(i).getSiteAddressRd());
//        }
        return restaurantItems;
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
