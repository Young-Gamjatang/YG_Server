package com.contest.seoul.domain.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.contest.seoul.api.ModelRestaurant;
import com.contest.seoul.domain.service.DBtestServiceByMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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
}
