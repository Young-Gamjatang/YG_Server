package com.contest.seoul.api;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ModelRestaurant {
    private final DynamoDBMapper dynamoDBMapper;

    // tag값의 정보를 가져오는 메소드
    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public Boolean getAPIList() throws ParserConfigurationException, IOException, SAXException {
        Map<String, String> guMap = ModelUrl.getMap();
        int page = 1;  // 페이지 초기값
        // 총 개수 가져오기
        int totalCount = 0;
        int successCount=0;
        int errorCount=0;
        boolean check = true;

        List<RestaurantItem> itemList = new ArrayList<>();
        List<ErrorRestaurant> errorRestaurants = new ArrayList<>();

        for(String guName : guMap.keySet()) {
            try {
                String tempUrl = guMap.get(guName);
                System.out.println(guName+" 주소 : " +tempUrl);
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
                        RestaurantItem restaurantItem = new RestaurantItem();
                        restaurantItem.setCggCode(getTagValue("CGG_CODE", eElement));
                        restaurantItem.setAsgnYear(getTagValue("ASGN_YY", eElement));
                        restaurantItem.setAsgnDate(getTagValue("ASGN_YMD", eElement));
                        restaurantItem.setUpsoName(getTagValue("UPSO_NM", eElement));
                        restaurantItem.setSiteAddressRd(getTagValue("SITE_ADDR_RD", eElement));
                        restaurantItem.setSiteAddress(getTagValue("SITE_ADDR", eElement));
                        restaurantItem.setMainFood(getTagValue("MAIN_EDF", eElement));
                        restaurantItem.setAdmdngName(getTagValue("ADMDNG_NM", eElement));
                        restaurantItem.setSitePhoneNumber(getTagValue("UPSO_SITE_TELNO", eElement));
                        try {
                            Double[] coords = LocationToLatiLongi.findGeoPoint(restaurantItem.getSiteAddress());
                            restaurantItem.setLatitude(coords[0]);
                            restaurantItem.setLongitude(coords[1]);
                            itemList.add(restaurantItem);
                        } catch (Exception e) {
                            ErrorRestaurant errorRestaurant = new ErrorRestaurant();
                            errorRestaurant.setCggCode(getTagValue("CGG_CODE", eElement));
                            errorRestaurant.setAsgnYear(getTagValue("ASGN_YY", eElement));
                            errorRestaurant.setAsgnDate(getTagValue("ASGN_YMD", eElement));
                            errorRestaurant.setUpsoName(getTagValue("UPSO_NM", eElement));
                            errorRestaurant.setSiteAddressRd(getTagValue("SITE_ADDR_RD", eElement));
                            errorRestaurant.setSiteAddress(getTagValue("SITE_ADDR", eElement));
                            errorRestaurant.setMainFood(getTagValue("MAIN_EDF", eElement));
                            errorRestaurant.setAdmdngName(getTagValue("ADMDNG_NM", eElement));
                            errorRestaurant.setSitePhoneNumber(getTagValue("UPSO_SITE_TELNO", eElement));
                            errorRestaurants.add(errorRestaurant);
                        }
                        totalCount++;

                    }
                    System.out.println("정상:" + itemList.size() + ", 주소에러:" + errorRestaurants.size());

                }  // for end


            } catch (Exception e) {
                e.printStackTrace();
            }  // try~catch end
            System.out.println(guName + " 데이터베이스 데이터 삽입 시작");
            try{
                // 병렬로하면 프로비저닝 오류 떴을 때 어디부터 다시 해야하는지 모름
                successCount+= itemList.size();
                errorCount+=errorRestaurants.size();
                itemList.stream().parallel().forEach(dynamoDBMapper::save);
                errorRestaurants.stream().parallel().forEach(dynamoDBMapper::save);
            }catch(Exception e){
                System.out.println(guName + " 데이터베이스 통신 중 오류 발생"+e);
                check = false;
            }

        }

        System.out.println("XXXXXXXXXXXXXXXXX");
        System.out.println("총 데이터 : " + totalCount + "개");
        System.out.println("삽입 가능 데이터 : " + successCount + "개");
        System.out.println("에러 데이터 : " + errorCount + "개");
        System.out.println("XXXXXXXXXXXXXXXXX");
        // 데이터 삽입

        System.out.println("데이터베이스 데이터 삽입 종료");

        return check;
    }  // main end

}  // class end