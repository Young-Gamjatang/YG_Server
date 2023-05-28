package com.contest.seoul.api;

import java.util.HashMap;
import java.util.Map;

public class ModelUrl {
    private static final String KEY = "6c514646726a6f6e32395044784652";

    public static Map<String,String> getMap(){
        Map<String, String> guMap = new HashMap<>();
        guMap.put("영등포구", "http://openapi.ydp.go.kr:8088/"+KEY+"/xml/YdpModelRestaurantDesignate/1/1000/");
        guMap.put("송파구", "http://openapi.songpa.seoul.kr:8088/"+KEY+"/xml/SpModelRestaurantDesignate/1/1000/");
        guMap.put("구로구", "http://openapi.guro.go.kr:8088/"+KEY+"/xml/GuroModelRestaurantDesignate/1/1000/");
        guMap.put("강동구", "http://openapi.gd.go.kr:8088/"+KEY+"/xml/GdModelRestaurantDesignate/1/1000/");
        guMap.put("중랑구", "http://openapi.jungnang.seoul.kr:8088/"+KEY+"/xml/JungnangModelRestaurantDesignate/1/1000/");
        guMap.put("성동구", "http://openapi.sd.go.kr:8088/"+KEY+"/xml/SdModelRestaurantDesignate/1/1000/");
        guMap.put("관악구", "http://openapi.gwanak.go.kr:8088/"+KEY+"/xml/GaModelRestaurantDesignate/1/1000/");
        guMap.put("강남구", "http://openapi.gangnam.go.kr:8088/"+KEY+"/xml/GnModelRestaurantDesignate/1/1000/");
        guMap.put("은평구", "http://openapi.ep.go.kr:8088/"+KEY+"/xml/EpModelRestaurantDesignate/1/1000/");
        guMap.put("광진구", "http://openapi.gwangjin.go.kr:8088/"+KEY+"/xml/GwangjinModelRestaurantDesignate/1/1000/");
        guMap.put("중구", "http://openapi.junggu.seoul.kr:8088/"+KEY+"/xml/JungguModelRestaurantDesignate/1/1000/");
        guMap.put("서초구", "http://openapi.seocho.go.kr:8088/"+KEY+"/xml/ScModelRestaurantDesignate/1/1000/");
        guMap.put("용산구", "http://openapi.yongsan.go.kr:8088/"+KEY+"/xml/YsModelRestaurantDesignate/1/1000/");
        guMap.put("성북구", "http://openapi.sb.go.kr:8088/"+KEY+"/xml/SbModelRestaurantDesignate/1/1000/");
        guMap.put("서대문구", "http://openapi.sdm.go.kr:8088/"+KEY+"/xml/SeodaemunModelRestaurantDesignate/1/1000/");
        guMap.put("금천구", "http://openapi.geumcheon.go.kr:8088/"+KEY+"/xml/GeumcheonModelRestaurantDesignate/1/1000/");
        guMap.put("도봉구", "http://openapi.dobong.go.kr:8088/"+KEY+"/xml/DobongModelRestaurantDesignate/1/1000/");
        guMap.put("마포구", "http://openapi.mapo.go.kr:8088/"+KEY+"/xml/MpModelRestaurantDesignate/1/1000/");
        guMap.put("광북구", "http://openapi.gangbuk.go.kr:8088/"+KEY+"/xml/GbModelRestaurantDesignate/1/1000/");
        guMap.put("동대문구", "http://openapi.ddm.go.kr:8088/"+KEY+"/xml/DongdeamoonModelRestaurantDesignate/1/1000/");
        guMap.put("종로구", "http://openapi.jongno.go.kr:8088/"+KEY+"/xml/JongnoModelRestaurantDesignate/1/1000/");
        guMap.put("노원구", "http://openapi.nowon.go.kr:8088/"+KEY+"/xml/NwModelRestaurantDesignate/1/1000/");
        guMap.put("동작구", "http://openapi.dongjak.go.kr:8088/"+KEY+"/xml/DjModelRestaurantDesignate/1/1000/");
        guMap.put("강서구", "http://openapi.gangseo.seoul.kr:8088/"+KEY+"/xml/GangseoModelRestaurantDesignate/1/1000/");
        guMap.put("양천구", "http://openapi.yangcheon.go.kr:8088/"+KEY+"/xml/YcModelRestaurantDesignate/1/1000/");
        return guMap;
    }
    public static String getCggCode(String gu){
        Map<String, String> districtCodeMap = new HashMap<>();
        districtCodeMap.put("동대문구", "3050000");
        districtCodeMap.put("중랑구", "3060000");
        districtCodeMap.put("강남구", "3220000");
        districtCodeMap.put("은평구", "3110000");
        districtCodeMap.put("성동구", "3030000");
        districtCodeMap.put("성북구", "3070000");
        districtCodeMap.put("종로구", "3000000");
        districtCodeMap.put("영등포구", "3180000");
        districtCodeMap.put("강동구", "3240000");
        districtCodeMap.put("광진구", "3040000");
        districtCodeMap.put("마포구", "3130000");
        districtCodeMap.put("구로구", "3160000");
        districtCodeMap.put("양천구", "3140000");
        districtCodeMap.put("중구", "3010000");
        districtCodeMap.put("용산구", "3020000");
        districtCodeMap.put("관악구", "3200000");
        districtCodeMap.put("노원구", "3100000");
        districtCodeMap.put("서대문구", "3120000");
        districtCodeMap.put("서초구", "3210000");
        districtCodeMap.put("동작구", "3190000");
        districtCodeMap.put("송파구", "3230000");
        districtCodeMap.put("강서구", "3150000");
        districtCodeMap.put("도봉구", "3090000");
        districtCodeMap.put("광북구", "3080000");
        districtCodeMap.put("금천구", "3170000");
        if(districtCodeMap.containsKey(gu)) {
            return districtCodeMap.get(gu);
        }else {
            System.out.println("서울이 아닙니다.");
            return null;
        }

    }
}
