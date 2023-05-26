package com.contest.seoul.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.contest.seoul.domain.model.ErrorRestaurant;
import com.contest.seoul.domain.model.RestaurantItem;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CggCode {

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
        boolean check = true;


        for(String guName : guMap.keySet()) {
            try {
                String tempUrl = guMap.get(guName);
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(tempUrl);

                // root tag
                doc.getDocumentElement().normalize();
//                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("row");
                System.out.print(guName + " : ");
                String cgg = "";

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        RestaurantItem restaurantItem = new RestaurantItem();
                        if(!cgg.equals(getTagValue("CGG_CODE", eElement))) {
                            System.out.println(getTagValue("CGG_CODE", eElement));
                            cgg = getTagValue("CGG_CODE", eElement);

                        }
                    }

                }  // for end


            } catch (Exception e) {
                e.printStackTrace();
            }  // try~catch end


        }

        return check;
    }  // main end

}  // class end