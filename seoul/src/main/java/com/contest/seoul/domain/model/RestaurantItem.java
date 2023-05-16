package com.contest.seoul.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName = "restaurant") //해당 클래스를 엔티티로 설정합니다.
public class RestaurantItem {

    @DynamoDBHashKey(attributeName = "id") //해당 필드를 HashKey로 설정합니다.
    @DynamoDBAutoGeneratedKey // 자동으로 Key를 생성합니다. UUID를 활용합니다.
    private String id;

    @DynamoDBAttribute  // 해당 필드를 Attribute로 설정합니다.
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "latitude") // global secondary index HashKey 설정
    private Double latitude; // 위도
    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "longitude") // global secondary index RangeKey 설정
    private Double longitude; // 경도
    @DynamoDBAttribute(attributeName = "upsoSno")
    private Integer upsoSno;  // 업소일련번호
    @DynamoDBAttribute
    private Integer cggCode;// 시군구 코드
    @DynamoDBAttribute
    private String sntCobNm; // 업종명
    @DynamoDBAttribute
    private String upsoNm; // 업소명
    @DynamoDBAttribute
    private String sntUptaeNm; // 업태명
    @DynamoDBAttribute
    private String siteAddrRd; // 소재지도로명
    @DynamoDBAttribute
    private Integer bdngJisgFlrNum; // 지상_부터
    @DynamoDBAttribute
    private Integer bdngUnderFlrNum; // 지하_부터
    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean geEhYn; // 모범음식점여부

}