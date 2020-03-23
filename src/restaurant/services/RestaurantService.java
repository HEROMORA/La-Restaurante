package restaurant.services;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import restaurant.dish.*;
import restaurant.reservation.Table;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class RestaurantService {
    private Document connect(){
        try{
            File file = new File("data.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            return doc;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList readTables(){
        ArrayList tableList = new ArrayList();
        Document doc = this.connect();
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("table");
        for(int i = 0; i < nodeList.getLength();i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element)node;
                Table table = new Table();
                table.setTableNumber(Integer.parseInt(e.getElementsByTagName("number").item(0).getTextContent()));
                table.setNumberOfSeats(Integer.parseInt(e.getElementsByTagName("number_of_seats").item(0).getTextContent()));
                table.setSmoking(Boolean.parseBoolean(e.getElementsByTagName("smoking").item(0).getTextContent()));
                tableList.add(table);
            }
        }
        return tableList;
    }
    public ArrayList<Dish> readDishes(){
        ArrayList<Dish> dishesList = new ArrayList<>();
        Document doc = this.connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("dish");
        for(int i = 0; i < nodeList.getLength();i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) node;
                switch (e.getElementsByTagName("type").item(0).getTextContent()){
                    case "main_course":
                        MainDish mainDish = new MainDish(e.getElementsByTagName("name").item(0).getTextContent(), DishType.MAINDISH, BigDecimal.valueOf(Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent())));
                        dishesList.add(mainDish);
                        break;
                    case "appetizer":
                        Appetizer appetizer = new Appetizer(e.getElementsByTagName("name").item(0).getTextContent(), DishType.APPETIZER, BigDecimal.valueOf(Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent())));
                        dishesList.add(appetizer);
                        break;
                    case "desert":
                        Desert desert = new Desert(e.getElementsByTagName("name").item(0).getTextContent(), DishType.DESERT, BigDecimal.valueOf(Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent())));
                        dishesList.add(desert);
                        break;
                    default:
                }
            }
        }
        return dishesList;
    }
}
