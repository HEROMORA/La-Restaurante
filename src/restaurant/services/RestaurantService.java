package restaurant.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.dish.*;
import restaurant.order.Order;
import restaurant.order.OrderDetails;
import restaurant.reservation.Reservation;
import restaurant.reservation.Table;
import restaurant.users.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantService {
    private File file;
    public RestaurantService(){
        file = new File("data.xml");
    }

    private Document connect() {

        try {

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

    ArrayList<Table> readTables() {

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


    ArrayList<Dish> readDishes() {

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

    ArrayList<User> readUsers() {
        ArrayList<User> usersList = new ArrayList<>();

        Document doc = connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("user");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element) node;
                User user;

                switch (e.getElementsByTagName("role").item(0).getTextContent()) {
                    case "Client":
                        user = new Customer();
                        user.setUserRole(UserRole.CUSTOMER);
                        break;

                    case "Manager":
                        user = new Manager();
                        user.setUserRole(UserRole.MANAGER);
                        break;

                    case "Cooker":
                        user = new Cook();
                        user.setUserRole(UserRole.COOK);
                        break;

                    case "Waiter":
                        user = new Customer();
                        user.setUserRole(UserRole.WAITER);
                        break;

                    default:
                        throw new Error("Invalid role type");
                }

                user.setName(e.getElementsByTagName("name").item(0).getTextContent());
                user.setUsername(e.getElementsByTagName("username").item(0).getTextContent());
                user.setPassword(e.getElementsByTagName("password").item(0).getTextContent());

                usersList.add(user);
            }
        }

        return usersList;
    }

    ArrayList<Reservation> readReservations() {

        ArrayList<Reservation> reservationArrayList = new ArrayList<>();

        Document doc = connect();
        assert doc != null;
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("reservation");

        for(int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element)node;
                Reservation reserv = new Reservation();

                reserv.setCustomerUserName(e.getElementsByTagName("customer_username").item(0).getTextContent());

                Date date = null;
                Date endDate = null;

                try {
                    date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                            .parse(e.getElementsByTagName("start_date").item(0).getTextContent());
                    endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                            .parse(e.getElementsByTagName("end_date").item(0).getTextContent());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                reserv.setReservationDate(date);
                reserv.setEndReservationDate(date);

                reserv.setTableNum(Integer.parseInt(e.getElementsByTagName("table_number").item(0).getTextContent()));

                reservationArrayList.add(reserv);
            }
        }

        return reservationArrayList;
    }


    void writeUser(User user) {
        Document doc = connect();
        assert doc != null;

        String roleString;
        switch (user.getUserRole()){
            case MANAGER:
                roleString = "Manager";
                break;
            case WAITER:
                roleString = "Waiter";
                break;
            case COOK:
                roleString ="Cooker";
                break;
            case CUSTOMER:
                roleString ="Client";
                break;
            default:
                roleString= "";
        }

        NodeList nodeList = doc.getElementsByTagName("users");
        Element root = (Element) nodeList.item(0);

        Element newUser = doc.createElement("user");

        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(user.getName()));
        newUser.appendChild(name);

        Element role = doc.createElement("role");
        role.appendChild(doc.createTextNode(roleString));
        newUser.appendChild(role);

        Element username = doc.createElement("username");
        username.appendChild(doc.createTextNode(user.getUsername()));
        newUser.appendChild(username);

        Element password = doc.createElement("password");
        password.appendChild(doc.createTextNode(user.getPassword()));
        newUser.appendChild(password);

        root.appendChild(newUser);

        DOMSource source = new DOMSource(doc);

        try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(file);

            transformer.transform(source,result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public void writeReservation(Reservation res) {
        Document doc = connect();
        assert doc != null;

        Date date = res.getReservationDate();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String dateString = df.format(date);
        String endDateString = df.format(res.getEndReservationDate());

        NodeList nodeList = doc.getElementsByTagName("reservations");
        Element root = doc.getDocumentElement();

        Element reservations;
        if (nodeList.getLength() == 0)
            reservations = doc.createElement("reservations");
        else
            reservations = (Element) nodeList.item(0);


        Element newReservation = doc.createElement("reservation");

        Element tableNo = doc.createElement("table_number");
        tableNo.appendChild(doc.createTextNode(String.valueOf(res.getTableNum())));
        newReservation.appendChild(tableNo);

        Element username = doc.createElement("customer_username");
        username.appendChild(doc.createTextNode(res.getCustomerUserName()));
        newReservation.appendChild(username);

        Element resDate = doc.createElement("start_date");
        resDate.appendChild(doc.createTextNode(dateString));
        newReservation.appendChild(resDate);

        Element endDate = doc.createElement("end_date");
        endDate.appendChild(doc.createTextNode(endDateString));
        newReservation.appendChild(endDate);


        reservations.appendChild(newReservation);
        root.appendChild(reservations);

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

    public void writeOrder(Order order) throws TransformerException {

        Document doc = connect();
        assert doc != null;

        NodeList nodeList = doc.getElementsByTagName("orders");
        Element root;

        if (nodeList.getLength() == 0)
            root = doc.createElement("orders");
        else
            root = (Element) nodeList.item(0);

        Element newOrder = doc.createElement("order");

        Element dateElement = doc.createElement("date");

        Date date = order.getDate();
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String dateString = df.format(date);

        dateElement.appendChild(doc.createTextNode(dateString));
        newOrder.appendChild(dateElement);

        Element orderStatusElement = doc.createElement("orderStatus");
        orderStatusElement.appendChild(doc.createTextNode(order.getOrderStatus().toString()));
        newOrder.appendChild(orderStatusElement);

        Element totalPriceElement = doc.createElement("totalPrice");
        totalPriceElement.appendChild(doc.createTextNode(String.valueOf(order.calculateTotalPrice())));
        newOrder.appendChild(totalPriceElement);

        Element orderDetails = doc.createElement("orderDetails");

        for(OrderDetails orderDetail:order.getOrdersDetails())
        {
            Element orderDetailElement = doc.createElement("orderDetail");

            Element quantityElement = doc.createElement("quantity");
            quantityElement.appendChild(doc.createTextNode(String.valueOf(orderDetail.getQuantity())));
            orderDetailElement.appendChild(quantityElement);

            Element dishNameElement = doc.createElement("dishName");
            quantityElement.appendChild(doc.createTextNode(orderDetail.getDishName()));
            orderDetailElement.appendChild(dishNameElement);

            orderDetails.appendChild(orderDetailElement);
        }

        newOrder.appendChild(orderDetails);
        root.appendChild(newOrder);

        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(file);
        transformer.transform(source,result);
    }
}
