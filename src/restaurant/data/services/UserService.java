package restaurant.data.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import restaurant.models.users.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;

public class UserService extends BaseService<User> {

    @Override
    public void writeData(User user) {
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

    @Override
    public ArrayList<User> readData() {
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
}
