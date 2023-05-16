package com.contest.seoul.domain.controller;

import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.service.DBtestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    final DBtestService dBtestService;
    @GetMapping("test")
    public RestaurantItem test(){
        return dBtestService.test();
    }
    @GetMapping("test/get")
    public RestaurantItem getTest(){
        return dBtestService.getTest();
    }
}
