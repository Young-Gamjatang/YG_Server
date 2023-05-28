package com.contest.seoul.domain.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.contest.seoul.api.ModelRestaurant;
import com.contest.seoul.api.ModelUrl;
import com.contest.seoul.api.WrongRestaurantAPI;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.service.DBtestServiceByMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.websocket.server.PathParam;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("dynamo")
public class DynamoDBController {
    private final DBtestServiceByMapper dBtestServiceByMapper;
    private final DynamoDBMapper dynamoDBMapper;

    // 모범 음식점 테이블
    @PostMapping("restaurant/create")
    public String createTableByMapper(){
        return dBtestServiceByMapper.createTableByMapper() ? "테이블 생성 성공" : "테이블 생성 실패";
    }
    // 주소 에러 테이블
    @PostMapping("error/create")
    public String createErrorTableByMapper(){
        return dBtestServiceByMapper.createErrorTableByMapper() ? "테이블 생성 성공" : "테이블 생성 실패";
    }
    // 위반 음식점 테이블
    @PostMapping("wrong/create")
    public String createWrongTableByMapper(){
        return dBtestServiceByMapper.createWrongTableByMapper() ? "테이블 생성 성공" : "테이블 생성 실패";
    }
    @PostMapping("insert/total")
    public boolean insertRestaurantData() throws ParserConfigurationException, IOException, SAXException {
//        FoodSanditation foodSanditation = new FoodSanditation(dynamoDBMapper);
//        foodSanditation.getAPIList();
        ModelRestaurant modelRestaurant = new ModelRestaurant(dynamoDBMapper);
        return modelRestaurant.getAPIList();
    }
    @PostMapping("insert/wrong/total")
    public boolean insertWrongRestaurantData() throws ParserConfigurationException, IOException, SAXException {
        WrongRestaurantAPI wrongRestaurantAPI = new WrongRestaurantAPI(dynamoDBMapper);
        return wrongRestaurantAPI.getAPIList();
    }
    @GetMapping("query/cggcode")
    public List<Map<String,Object>> queryTest(@RequestParam("guName") String guName){
        try {
            return dBtestServiceByMapper.loadDataByCggCode(ModelUrl.getCggCode(guName));
        }catch (AmazonDynamoDBException e) {
            System.out.println("서울이 아닙니다.");
            return null;
        }
    }
    @GetMapping("query/near")
    public List<Map<String,Object>> queryTest(
            @RequestParam("guName") String guName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude){
        return dBtestServiceByMapper.loadNearByRestaurant(ModelUrl.getCggCode(guName),latitude,longitude);
    }
    @GetMapping("query/wrong/cggcode")
    public List<Map<String,Object>> queryWrongUpso(@RequestParam("guName") String guName){
        return dBtestServiceByMapper.loadWrongDataByCggCode(ModelUrl.getCggCode(guName));
    }
    // 모범식당 검색
    @GetMapping("search/restaurant")
    public List<Map<String, Object>> searchRestaurant(@RequestParam("upsoName") String upsoName) {
        return dBtestServiceByMapper.findModelRestaurantByUpsoNm(upsoName);
    }

    @GetMapping("scan")
    public List<RestaurantItem> scanTest(){
        return dBtestServiceByMapper.scanData();
    }



    @GetMapping("search/wrong")
    public List<Map<String, Object>> wrongQuery(@RequestParam("upsoName") String upsoName) {
        return dBtestServiceByMapper.findWrongRestaurantByUpsoNm(upsoName);
    }
}
