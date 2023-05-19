package com.contest.seoul.domain.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.contest.seoul.api.FoodSanditation;
import com.contest.seoul.api.LocationToLatiLongi;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.service.DBtestServiceByMapper;
import com.contest.seoul.domain.service.DBtestServiceBySDK;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    final DBtestServiceBySDK dBtestService;
    final DynamoDBMapper dynamoDBMapper;
    final DBtestServiceByMapper dBtestServiceByMapper;
    @GetMapping("test")
    public RestaurantItem test(){
        return dBtestService.test();
    }
    @GetMapping("test/get")
    public boolean getTest(){
        System.out.println("test");
        return dBtestService.createTable_ValidInput_TableHasBeenCreated();
    }
    @PostMapping("test/put")
    public int putTest() {
        return dBtestService.putItem_ShouldBeCalledAfterTableCreation_StatusOk();
    }
    @GetMapping("test/mapper/create")
    public boolean createTableByMapper(){
        boolean check = dBtestServiceByMapper.createTableByMapper();
        System.out.println(check ? "테이블 생성 성공" : "테이블 생성 실패");

        return check;
    }
    @PostMapping("test/mapper/put")
    public RestaurantItem putItemByMapper(){
        return dBtestServiceByMapper.saveItemByMapper();
    }

    @GetMapping("test/foodApi")
    public int testApi() throws ParserConfigurationException, IOException, SAXException {
        return FoodSanditation.totalCount();
    }
    @GetMapping("test/geo/{location}")
    public Double[] testGeo(@PathVariable String location) {
        System.out.println(location);
        return LocationToLatiLongi.findGeoPoint(location);
    }
    @GetMapping("test/getApi")
    public void testAPIAPI() throws ParserConfigurationException, IOException, SAXException {
        List<RestaurantItem> restaurantItems = FoodSanditation.getAPIList();
        restaurantItems.stream().parallel().forEach(dynamoDBMapper::save);
    }
}
