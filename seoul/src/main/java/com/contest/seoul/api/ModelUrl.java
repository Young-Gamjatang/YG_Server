package com.contest.seoul.api;

import java.util.HashMap;
import java.util.Map;

public class ModelUrl {
    private static final String KEY = "6c514646726a6f6e32395044784652";

    public static Map<String,String> getMap(){
        Map<String, String> guMap = new HashMap<>();
        guMap.put("yeongdeungpo", "http://openapi.ydp.go.kr:8088/"+KEY+"/xml/YdpModelRestaurantDesignate/1/1000/");
        guMap.put("songpa", "http://openapi.songpa.seoul.kr:8088/"+KEY+"/xml/SpModelRestaurantDesignate/1/1000/");
        guMap.put("guro", "http://openapi.guro.go.kr:8088/"+KEY+"/xml/GuroModelRestaurantDesignate/1/1000/");
        guMap.put("gangdong", "http://openapi.gd.go.kr:8088/"+KEY+"/xml/GdModelRestaurantDesignate/1/1000/");
        guMap.put("jungnang", "http://openapi.jungnang.seoul.kr:8088/"+KEY+"/xml/JungnangModelRestaurantDesignate/1/1000/");
        guMap.put("seongdong", "http://openapi.sd.go.kr:8088/"+KEY+"/xml/SdModelRestaurantDesignate/1/1000/");
        guMap.put("gwanak", "http://openapi.gwanak.go.kr:8088/"+KEY+"/xml/GaModelRestaurantDesignate/1/1000/");
        guMap.put("gangnam", "http://openapi.gangnam.go.kr:8088/"+KEY+"/xml/GnModelRestaurantDesignate/1/1000/");
        guMap.put("eunpyeong", "http://openapi.ep.go.kr:8088/"+KEY+"/xml/EpModelRestaurantDesignate/1/1000/");
        guMap.put("gwangjin", "http://openapi.gwangjin.go.kr:8088/"+KEY+"/xml/GwangjinModelRestaurantDesignate/1/1000/");
        guMap.put("jung", "http://openapi.junggu.seoul.kr:8088/"+KEY+"/xml/JungguModelRestaurantDesignate/1/1000/");
        guMap.put("seocho", "http://openapi.seocho.go.kr:8088/"+KEY+"/xml/ScModelRestaurantDesignate/1/1000/");
        guMap.put("yongsan", "http://openapi.yongsan.go.kr:8088/"+KEY+"/xml/YsModelRestaurantDesignate/1/1000/");
        guMap.put("seongbuk", "http://openapi.sb.go.kr:8088/"+KEY+"/xml/SbModelRestaurantDesignate/1/1000/");
        guMap.put("seodaemun", "http://openapi.sdm.go.kr:8088/"+KEY+"/xml/SeodaemunModelRestaurantDesignate/1/1000/");
        guMap.put("geumcheon", "http://openapi.geumcheon.go.kr:8088/"+KEY+"/xml/GeumcheonModelRestaurantDesignate/1/1000/");
        guMap.put("dobong", "http://openapi.dobong.go.kr:8088/"+KEY+"/xml/DobongModelRestaurantDesignate/1/1000/");
        guMap.put("mapo", "http://openapi.mapo.go.kr:8088/"+KEY+"/xml/MpModelRestaurantDesignate/1/1000/");
        guMap.put("gangbuk", "http://openapi.gangbuk.go.kr:8088/"+KEY+"/xml/GbModelRestaurantDesignate/1/1000/");
        guMap.put("dongdaemun", "http://openapi.ddm.go.kr:8088/"+KEY+"/xml/DongdeamoonModelRestaurantDesignate/1/1000/");
        guMap.put("jongno", "http://openapi.jongno.go.kr:8088/"+KEY+"/xml/JongnoModelRestaurantDesignate/1/1000/");
        guMap.put("nowon", "http://openapi.nowon.go.kr:8088/"+KEY+"/xml/NwModelRestaurantDesignate/1/1000/");
        guMap.put("dongjak", "http://openapi.dongjak.go.kr:8088/"+KEY+"/xml/DjModelRestaurantDesignate/1/1000/");
        guMap.put("gangseo", "http://openapi.gangseo.seoul.kr:8088/"+KEY+"/xml/GangseoModelRestaurantDesignate/1/1000/");
        guMap.put("yangcheon", "http://openapi.yangcheon.go.kr:8088/"+KEY+"/xml/YcModelRestaurantDesignate/1/1000/");
        return guMap;
    }
}
