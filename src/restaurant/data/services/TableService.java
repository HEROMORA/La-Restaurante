package restaurant.data.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.reservation.Table;

import java.util.ArrayList;

public class TableService extends BaseService<Table> {

    @Override
    public void writeData(Table table) {
        // Negoitable if table should be added or not
    }

    @Override
    public ArrayList<Table> readData() {
        ArrayList<Table> tableList = new ArrayList<Table>();

        Document doc = connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("table");

        for(int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

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
}
