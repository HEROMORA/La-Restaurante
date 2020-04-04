package restaurant.data.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.data.repositories.DishRepository;
import restaurant.models.dish.Dish;
import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderService extends BaseService<Order> {

    private DishRepository dishRepository = new DishRepository();

    @Override
    public void writeData(Order order) {
        Document doc = connect();
        assert doc != null;

        NodeList nodeList = doc.getElementsByTagName("orders");
        Element root = doc.getDocumentElement();

        Element orders;

        if (nodeList.getLength() == 0)
            orders = doc.createElement("orders");
        else
            orders = (Element) nodeList.item(0);

        Element newOrder = doc.createElement("order");

        Element dateElement = doc.createElement("date");

        Date date = order.getDate();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateString = df.format(date);

        dateElement.appendChild(doc.createTextNode(dateString));
        newOrder.appendChild(dateElement);

        Element orderStatusElement = doc.createElement("orderStatus");
        orderStatusElement.appendChild(doc.createTextNode(order.getOrderStatus().toString()));
        newOrder.appendChild(orderStatusElement);

        Element customerNameElement = doc.createElement("customerName");
        customerNameElement.appendChild(doc.createTextNode(order.getCustomerName()));
        newOrder.appendChild(customerNameElement);

        Element totalPriceElement = doc.createElement("totalPrice");
        totalPriceElement.appendChild(doc.createTextNode(String.valueOf(order.calculateTotalPrice())));
        newOrder.appendChild(totalPriceElement);

        Element tableNumberElement = doc.createElement("tableNumber");
        tableNumberElement.appendChild(doc.createTextNode(String.valueOf(order.getTableNumber())));
        newOrder.appendChild(tableNumberElement);

        Element orderDetails = doc.createElement("orderDetails");

        for(OrderDetails orderDetail:order.getOrdersDetails())
        {
            Element orderDetailElement = doc.createElement("orderDetail");

            Element quantityElement = doc.createElement("quantity");
            quantityElement.appendChild(doc.createTextNode(String.valueOf(orderDetail.getQuantity())));
            orderDetailElement.appendChild(quantityElement);

            Element dishNameElement = doc.createElement("dishName");
            dishNameElement.appendChild(doc.createTextNode(orderDetail.getDishName()));
            orderDetailElement.appendChild(dishNameElement);

            orderDetails.appendChild(orderDetailElement);
        }

        newOrder.appendChild(orderDetails);
        orders.appendChild(newOrder);

        root.appendChild(orders);

        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source,result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> readData() {
        ArrayList<Order> ordersArrayList = new ArrayList<>();

        Document doc = connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("order");

        for(int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element) node;
                Order order = new Order();

                order.setOrderStatus(e.getElementsByTagName("orderStatus").item(0).getTextContent());
                order.setTableNumber(Integer.parseInt(e.getElementsByTagName("tableNumber").item(0).getTextContent()));
                order.setCustomerName(e.getElementsByTagName("customerName").item(0).getTextContent());

                Date date = null;

                try {
                    date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                            .parse(e.getElementsByTagName("date").item(0).getTextContent());

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                order.setDate(date);

                ArrayList<OrderDetails> orderDetails = new ArrayList<>();
                NodeList detailsNodeList = e.getElementsByTagName("orderDetail");
                for (int j = 0; j < detailsNodeList.getLength(); j++) {

                    Node detailsNode = detailsNodeList.item(j);
                    if (detailsNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element orderDetailElement = (Element)detailsNode;

                        OrderDetails orderDetail = new OrderDetails();

                        orderDetail.setQuantity(Integer.parseInt(orderDetailElement.getElementsByTagName("quantity").item(0).getTextContent()));
                        String dishName = orderDetailElement.getElementsByTagName("dishName").item(0).getTextContent();
                        Dish dish = dishRepository.getDishByName(dishName);

                        orderDetail.setDish(dish);
                        orderDetail.setDishName(dishName);

                        orderDetails.add(orderDetail);
                    }
                }

                order.setOrdersDetails(orderDetails);

                ordersArrayList.add(order);
            }
        }

        return ordersArrayList;
    }
}
