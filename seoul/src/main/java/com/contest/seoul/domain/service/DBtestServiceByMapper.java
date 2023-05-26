package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public boolean createErrorTableByMapper(){
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(ErrorRestaurant.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        return TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }
//    public RestaurantItem saveItemByMapper() {
//        RestaurantItem restaurantItem = RestaurantItem.builder()
//                .latitude(143.4313)
//                .longitude(123.134)
//                .upsoSno("003013")
//                .upsoNm("동래밀면")
//                .siteAddrRd("숭진리 3515번길 ")
//                .bdngJisgFlrNum("1")
//                .bdngUnderFlrNum("0")
//                .geEhYn("비대상")
//                .build();
//        System.out.println("생성 Item ID : "+ restaurantItem.getId());
//        dynamoDbMapper.save(restaurantItem);
//
//        RestaurantItem test = dynamoDbMapper.load(RestaurantItem.class, restaurantItem.getId());
//
//        return test;
//
//    }
    public List<RestaurantItem> loadData(){
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDb);

        Table table = dynamoDB.getTable("restaurantItem");
        Index index = table.getIndex("cggCode-index");
        TableDescription tableDesc = table.describe();

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("cggCode = :v_code ")
                .withValueMap(new ValueMap()
                        .withString(":v_code","3140000"));

        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toJSONPretty());
        }

        return null;
    }
    public void scanData(){
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("restaurantItem");
        int total = 0;
        ScanResult result = amazonDynamoDb.scan(scanRequest);
        List<RestaurantItem> restaurantItems = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.getItems()){
            RestaurantItem restaurantItem = new RestaurantItem();
            restaurantItem.setCggCode(String.valueOf(item.get("cggCode")));
            restaurantItem.setSiteAddress(String.valueOf(item.get("siteAddress")));
            restaurantItem.setSiteAddressRd(String.valueOf(item.get("siteAddressRd")));
            restaurantItems.add(restaurantItem);
            total ++;
        }

        List<RestaurantItem> sorted = restaurantItems.stream().distinct().collect(Collectors.toList());
        System.out.println(total+","+sorted.size());
        for(int i=0; i< sorted.size(); i++) {
            System.out.println("시군구 코드 : "+sorted.get(i).getCggCode() +" : "+sorted.get(i).getSiteAddress()+ " : "+ sorted.get(i).getSiteAddressRd());
        }
    }
}
