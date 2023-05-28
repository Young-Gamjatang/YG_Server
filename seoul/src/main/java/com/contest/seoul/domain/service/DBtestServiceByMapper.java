package com.contest.seoul.domain.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.model.WrongRestaurant;
import com.contest.seoul.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DBtestServiceByMapper {

    private final AmazonDynamoDB amazonDynamoDb;
    private final DynamoDBMapper dynamoDbMapper;
    private final RestaurantRepository restaurantRepository;

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
// 테이블 생성
    public boolean createWrongTableByMapper(){
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(WrongRestaurant.class)
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));

        createTableRequest.getGlobalSecondaryIndexes().forEach(
                idx -> idx
                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                        .withProjection(new Projection().withProjectionType("ALL"))
        );
    return TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
}
    public List<Map<String,Object>> loadDataByCggCode(String cggCode){
        return restaurantRepository.findByCggCode(cggCode);
    }

    public List<RestaurantItem> scanData(){
        return restaurantRepository.findAll();
    }

    public List<Map<String, Object>> loadNearByRestaurant(String cggCode, Double latitude, Double longitude) {
        List<Map<String, Object>> list = restaurantRepository.findByCggCode(cggCode);

        double targetLatitude = latitude; // 주어진 latitude
        double targetLongitude = longitude; // 주어진 longitude

        // 거리 계산에 사용할 Comparator
        Comparator<Map<String, Object>> distanceComparator = (m1, m2) -> {
            double lat1 = Double.valueOf(m1.get("latitude")+"");
            double lon1 = Double.valueOf(m1.get("longitude")+"");
            double lat2 = Double.valueOf(m2.get("latitude")+"");
            double lon2 = Double.valueOf(m2.get("longitude")+"");

            // 주어진 latitude와 longitude와의 거리 계산
            double distance1 = calculateDistance(targetLatitude, targetLongitude, lat1, lon1);
            double distance2 = calculateDistance(targetLatitude, targetLongitude, lat2, lon2);

            return Double.compare(distance1, distance2);
        };
        // 가까운 거리에 있는 데이터 5개 추출
        List<Map<String, Object>> nearestRestaurants = list.stream()
                .sorted(distanceComparator)
                .limit(5)
                .collect(Collectors.toList());

        // 추출된 데이터 출력
//        for (Map<String, Object> restaurant : nearestRestaurants) {
//            latitude = (double) restaurant.get("latitude");
//            longitude = (double) restaurant.get("longitude");
//            System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
//        }
        return nearestRestaurants;
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 거리 계산 로직을 구현해야 함
        // 예시: Haversine formula를 활용한 거리 계산

        double earthRadius = 6371; // 지구 반지름 (단위: km)

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        return distance;
    }
}
