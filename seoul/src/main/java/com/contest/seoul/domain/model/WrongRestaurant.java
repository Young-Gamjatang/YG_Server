package com.contest.seoul.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName = "wrongrestaurant") //해당 클래스를 엔티티로 설정합니다.
public class WrongRestaurant {
    @DynamoDBHashKey(attributeName = "id") //해당 필드를 HashKey로 설정합니다.
    @DynamoDBAutoGeneratedKey // 자동으로 Key를 생성합니다. UUID를 활용합니다.
    private String id;

    @DynamoDBAttribute  // 해당 필드를 Attribute로 설정합니다.
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "cggCode") // global secondary index HashKey 설정
    private String cggCode; // 시군구코드
    @DynamoDBAttribute(attributeName = "latitude")
    private Double latitude; // 위도

    @DynamoDBAttribute(attributeName = "longitude")
    private Double longitude; // 경도

    @DynamoDBAttribute
    private String admDispoYmd; // 처분일자
    @DynamoDBAttribute
    private String gntNo; // 교부번호
    @DynamoDBAttribute
    private String sntCobNm; // 업종명
    @DynamoDBAttribute
    private String upsoNm; // 업소명
    @DynamoDBAttribute
    private String siteAddrRd; // 소재지도로명
    @DynamoDBAttribute
    private String siteAddr; // 소재지지번
    @DynamoDBAttribute
    private String drtInspYmd; // 지도점검일자
    @DynamoDBAttribute
    private String admmState; // 행정처분상태
    @DynamoDBAttribute
    private String viorSort; // 위반내역분류
    @DynamoDBAttribute
    private String basLaw; // 법적근거
    @DynamoDBAttribute
    private String viorYmd; // 위반일자
    @DynamoDBAttribute
    private String violCn; // 위반내용
    @DynamoDBAttribute
    private String dispoCtnDt; // 처분내용
    @DynamoDBAttribute
    private String dispoGigan; // 처분기간
}
