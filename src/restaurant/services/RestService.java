package restaurant.services;

import restaurant.dish.Dish;
import restaurant.services.wrappers.Dishes;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class RestService {

    ArrayList<Dish> readDishes() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Dishes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Dishes dishes = (Dishes) jaxbUnmarshaller.unmarshal( new File("data.xml") );

            return dishes.getDishes();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
