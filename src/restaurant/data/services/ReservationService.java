package restaurant.data.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.models.reservation.Reservation;

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

public class ReservationService extends BaseService<Reservation> {
    @Override
    public void writeData(Reservation res) {
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

    @Override
    public ArrayList<Reservation> readData() {
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
                reserv.setEndReservationDate(endDate);

                reserv.setTableNum(Integer.parseInt(e.getElementsByTagName("table_number").item(0).getTextContent()));

                reservationArrayList.add(reserv);
            }
        }

        return reservationArrayList;
    }
}
