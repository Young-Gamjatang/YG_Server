package com.contest.seoul.domain.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.contest.seoul.api.ModelRestaurant;
import com.contest.seoul.api.ModelUrl;
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

    @PostMapping("restaurant/create")
    public boolean createTableByMapper(){
        boolean check = dBtestServiceByMapper.createTableByMapper();
        System.out.println(check ? "테이블 생성 성공" : "테이블 생성 실패");

        return check;
    }
    @PostMapping("error/create")
    public boolean createErrorTableByMapper(){
        boolean check = dBtestServiceByMapper.createErrorTableByMapper();
        System.out.println(check ? "테이블 생성 성공" : "테이블 생성 실패");

        return check;
    }
    @PostMapping("insert/total")
    public boolean insertRestaurantData() throws ParserConfigurationException, IOException, SAXException {
//        FoodSanditation foodSanditation = new FoodSanditation(dynamoDBMapper);
//        foodSanditation.getAPIList();
        ModelRestaurant modelRestaurant = new ModelRestaurant(dynamoDBMapper);
        return modelRestaurant.getAPIList();
    }
    @GetMapping("query/cggcode")
    public List<Map<String,Object>> queryTest(@RequestParam("guName") String guName){
        return dBtestServiceByMapper.loadDataByCggCode(ModelUrl.getCggCode(guName));
    }
    @GetMapping("query/near")
    public List<Map<String,Object>> queryTest(
            @RequestParam("guName") String guName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude){
        return dBtestServiceByMapper.loadNearByRestaurant(ModelUrl.getCggCode(guName),latitude,longitude);
    }
    @GetMapping("scan")
    public List<RestaurantItem> scanTest(){
        return dBtestServiceByMapper.scanData();
    }
}
