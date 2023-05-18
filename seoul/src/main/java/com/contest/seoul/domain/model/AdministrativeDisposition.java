package com.contest.seoul.domain.model;

import lombok.Data;

@Data
public class AdministrativeDisposition {
    private String cggCode; // 시군구코드
    private String admDispoYmd; // 처분일자
    private String gntNo; // 교부번호
    private String sntCobNm; // 업종명
    private String upsoNm; // 업소명
    private String siteAddrRd; // 소재지도로명
    private String siteAddr; // 소재지지번
    private String drtInspYmd; // 지도점검일자
    private String admmState; // 행정처분상태
    private String viorSort; // 위반내역분류
    private String basLaw; // 법적근거
    private String viorYmd; // 위반일자
    private String violCn; // 위반내용
    private String dispoCtnDt; // 처분내용
    private String dispoGigan; // 처분기간
}
