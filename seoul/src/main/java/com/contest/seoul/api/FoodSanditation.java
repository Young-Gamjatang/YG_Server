package com.contest.seoul.api;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
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
public class FoodSanditation {
    private final DynamoDBMapper dynamoDBMapper;
    static String url = "http://openapi.seoul.go.kr:8088/6c514646726a6f6e32395044784652/xml/SeoulFoodHygieneBizHealthImport/";

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
        int closedCount = 0;

        List<RestaurantItem> itemList = new ArrayList<>();
        List<ErrorRestaurant> errorRestaurants = new ArrayList<>();

        try {
            for (int i = 1; i <= 1; i += 1000) {
                String tempUrl = url + i + "/" + (i + 999) + "/";

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
                        if (getTagValue("DCB_GBN_NM", eElement) == null) {   // 폐업 구분
                            RestaurantItem restaurantItem = new RestaurantItem();
                            restaurantItem.setUpsoSno(getTagValue("UPSO_SNO", eElement));
                            restaurantItem.setUpsoNm(getTagValue("CGG_CODE", eElement));
                            restaurantItem.setUpsoNm(getTagValue("UPSO_NM", eElement));
                            restaurantItem.setSiteAddrRd(getTagValue("SITE_ADDR_RD", eElement));
                            restaurantItem.setSiteAddr(getTagValue("SITE_ADDR", eElement));
                            restaurantItem.setBdngJisgFlrNum(getTagValue("BDNG_JISG_FLR_NUM", eElement));
                            restaurantItem.setBdngUnderFlrNum(getTagValue("BDNG_UNDER_FLR_NUM", eElement));
                            restaurantItem.setGeEhYn(getTagValue("GE_EH_YN", eElement));
                            try {
                                Double[] coords = LocationToLatiLongi.findGeoPoint(restaurantItem.getSiteAddr());
                                restaurantItem.setLatitude(coords[0]);
                                restaurantItem.setLongitude(coords[1]);
                                itemList.add(restaurantItem);
                            } catch (Exception e) {
                                ErrorRestaurant errorRestaurant = new ErrorRestaurant();
                                errorRestaurant.setUpsoSno(getTagValue("UPSO_SNO", eElement));
                                errorRestaurant.setUpsoNm(getTagValue("CGG_CODE", eElement));
                                errorRestaurant.setUpsoNm(getTagValue("UPSO_NM", eElement));
                                errorRestaurant.setSiteAddrRd(getTagValue("SITE_ADDR_RD", eElement));
                                errorRestaurant.setSiteAddr(getTagValue("SITE_ADDR", eElement));
                                errorRestaurant.setBdngJisgFlrNum(getTagValue("BDNG_JISG_FLR_NUM", eElement));
                                errorRestaurant.setBdngUnderFlrNum(getTagValue("BDNG_UNDER_FLR_NUM", eElement));
                                errorRestaurant.setGeEhYn(getTagValue("GE_EH_YN", eElement));
                                errorRestaurant.setNum(itemList.size()+"");
                                errorRestaurants.add(errorRestaurant);
                            }

                        } else {
                            closedCount++;
                        }
                        System.out.println("정상:" + itemList.size() + ", 주소에러:" + errorRestaurants.size() + ", 폐업:" + closedCount);

                    }  // for end
                }  // if end

            }

        } catch (Exception e) {
            e.printStackTrace();
        }  // try~catch end
        System.out.println("XXXXXXXXXXXXXXXXX");
        System.out.println("총 데이터 : " + totalCount + "개");
        System.out.println("삽입 가능 데이터 : " + itemList.size() + "개");
        System.out.println("에러 데이터 : " + errorRestaurants.size() + "개");
        System.out.println("폐업 데이터 : " + closedCount + "개");
        System.out.println("XXXXXXXXXXXXXXXXX");
        // 데이터 삽입
        boolean check = true;
        System.out.println("데이터베이스 데이터 삽입 시작");
        try{
            // 병렬로하면 프로비저닝 오류 떴을 때 어디부터 다시 해야하는지 모름
            itemList.stream().forEach(dynamoDBMapper::save);
            errorRestaurants.stream().forEach(dynamoDBMapper::save);
        }catch(Exception e){
            System.out.println("데이터베이스 통신 중 오류 발생"+e);
            check= false;
        }
        System.out.println("데이터베이스 데이터 삽입 종료");

        return check;
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
        NodeList countList = doc.getElementsByTagName("SeoulFoodHygieneBizHealthImport");

        // 총 수 출력
        int totalCount = Integer.parseInt(getTagValue("list_total_count",(Element) ((Node)countList.item(0))));
        System.out.println("총 데이터 수 : "+ totalCount);
        return totalCount;
    }
}  // class end