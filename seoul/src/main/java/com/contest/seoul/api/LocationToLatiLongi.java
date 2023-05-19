package com.contest.seoul.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LocationToLatiLongi {
    public static Double[] findGeoPoint(String location) {

        String apikey = "4F67E194-48F1-3B5A-9744-42517407627F";
        String searchType = "parcel";
        String searchAddr = location;
        String epsg = "epsg:4326";

        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=getCoord");
        sb.append("&format=json");
        sb.append("&crs=" + epsg);
        sb.append("&key=" + apikey);
        sb.append("&type=" + searchType);
        sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));

        try{
            URL url = new URL(sb.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            JsonParser jspa = new JsonParser();
            JsonObject jsob = (JsonObject) jspa.parse(reader);
            JsonObject jsrs = (JsonObject) jsob.get("response");
            JsonObject jsResult = (JsonObject) jsrs.get("result");
            JsonObject jspoitn = (JsonObject) jsResult.get("point");

//            System.out.print("위도 :" +jspoitn.get("y"));
//            System.out.println("경도 : "+jspoitn.get("x"));
            Double[] coords = new Double[2];
            coords[0] = Double.parseDouble ((jspoitn.get("y")+"").replace("\"",""));
            coords[1] = Double.parseDouble ((jspoitn.get("x")+"").replace("\"",""));
            return coords;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
