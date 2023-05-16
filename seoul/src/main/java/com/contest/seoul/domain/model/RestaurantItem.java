package com.contest.seoul.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@DynamoDBTable(tableName = "restaurant")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RestaurantItem {

    @DynamoDBHashKey
    private Double latitude;
    @DynamoDBRangeKey
    private Double longitude;
//    @DynamoDBAttribute
//    private String upsoSno;
//    @DynamoDBAttribute
//    private String cggCode;
//    @DynamoDBAttribute
//    private String sntCobNm;
//    @DynamoDBAttribute
//    private String upsoNm;
//    @DynamoDBAttribute
//    private String sntUptaeNm;
//    @DynamoDBAttribute
//    private String siteAddrRd;
//    @DynamoDBAttribute
//    private Integer bdngJisgFlrNum;
//    @DynamoDBAttribute
//    private Integer bdngUnderFlrNum;
//    @DynamoDBAttribute
//    private String geEhYn;

}