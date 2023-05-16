package com.contest.seoul.domain.controller;

import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.service.DBtestServiceByMapper;
import com.contest.seoul.domain.service.DBtestServiceBySDK;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {
    final DBtestServiceBySDK dBtestService;
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
}
