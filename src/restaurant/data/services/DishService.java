package restaurant.data.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.dish.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DishService extends BaseService<Dish> {
    @Override
    public void writeData(Dish dish) {
        // Neogitable if it should add dishes or not
    }

    @Override
    public ArrayList<Dish> readData() {
        ArrayList<Dish> dishesList = new ArrayList<>();

        Document doc = connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("dish");

        for(int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                switch (e.getElementsByTagName("type").item(0).getTextContent()) {
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

                    default: throw new Error("Invalid dish type");
                }
            }
        }

        return dishesList;
    }
}
