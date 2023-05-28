package com.contest.seoul.api;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
import com.contest.seoul.domain.model.WrongRestaurant;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class WrongRestaurantAPI {
    private final DynamoDBMapper dynamoDBMapper;
    static String url = "http://openapi.seoul.go.kr:8088/6c514646726a6f6e32395044784652/xml/SeoulAdminMesure/";

    // tag값의 정보를 가져오는 메소드
    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public Boolean getAPIList() throws ParserConfigurationException, IOException, SAXException {
        int page = 1;  // 페이지 초기값
        // 총 개수 가져오기
        int totalCount = totalCount();
        int closedCount = 95;
        int wrong = 0;


        try {
            for (int i = 96001; i <= totalCount; i += 1000) {
                String tempUrl = url + i + "/" + (i + 999) + "/";
                closedCount++;
                System.out.println(closedCount+"트");
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(tempUrl);

                // root tag
                doc.getDocumentElement().normalize();
//                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("row");


                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        if (getTagValue("SNT_COB_NM", eElement).equals("일반음식점") && Integer.parseInt(getTagValue("ADM_DISPO_YMD", eElement).replace(" ","")) >= 20210000) {   // 폐업 구분
                            WrongRestaurant restaurantItem = new WrongRestaurant();
                            restaurantItem.setCggCode(getTagValue("CGG_CODE", eElement));
                            restaurantItem.setAdmDispoYmd(getTagValue("ADM_DISPO_YMD", eElement));
                            restaurantItem.setGntNo(getTagValue("GNT_NO", eElement));
                            restaurantItem.setSntCobNm(getTagValue("SNT_COB_NM", eElement));
                            restaurantItem.setUpsoNm(getTagValue("UPSO_NM", eElement));
                            restaurantItem.setSiteAddrRd(getTagValue("SITE_ADDR_RD", eElement));
                            restaurantItem.setSiteAddr(getTagValue("SITE_ADDR", eElement));
                            restaurantItem.setDrtInspYmd(getTagValue("DRT_INSP_YMD", eElement));
                            restaurantItem.setAdmmState(getTagValue("ADMM_STATE", eElement));
                            restaurantItem.setViorSort(getTagValue("VIOR_SORT", eElement));
                            restaurantItem.setBasLaw(getTagValue("BAS_LAW", eElement));
                            restaurantItem.setViorYmd(getTagValue("VIOR_YMD", eElement));
                            restaurantItem.setViolCn(getTagValue("VIOL_CN", eElement));
                            restaurantItem.setDispoCtnDt(getTagValue("DISPO_CTN_DT", eElement));
                            restaurantItem.setDispoGigan(getTagValue("DISPO_GIGAN", eElement));
                            try {
                                Double[] coords = LocationToLatiLongi.findGeoPoint(restaurantItem.getSiteAddr());
                                restaurantItem.setLatitude(coords[0]);
                                restaurantItem.setLongitude(coords[1]);
                                try{
                                    System.out.println(restaurantItem);
                                    dynamoDBMapper.save(restaurantItem);
                                }catch (ProvisionedThroughputExceededException e){
                                    System.out.println("프로비저닝 오류");
                                    Thread.sleep(10000*6); //1분 대기
                                }
                            } catch (Exception e) {
                                System.out.println("주소 변환 에러");
                                wrong++;
                            }

                        }
                    }  // for end
                }  // if end

            }

        } catch (Exception e) {
            e.printStackTrace();
        }  // try~catch end
        System.out.println("데이터베이스 데이터 삽입 종료");

        return true;
    }  // main end
    public int totalCount() throws ParserConfigurationException, IOException, SAXException {
        int page = 1;
        String url2 = url +page
                +"/1";
        System.out.println(url2);

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(url2);
        // root tag
        doc.getDocumentElement().normalize();

        // 파싱할 tag
        NodeList countList = doc.getElementsByTagName("SeoulAdminMesure");

        // 총 수 출력
        int totalCount = Integer.parseInt(getTagValue("list_total_count",(Element) ((Node)countList.item(0))));
        System.out.println("총 데이터 수 : "+ totalCount);
        return totalCount;
    }
}  // class end