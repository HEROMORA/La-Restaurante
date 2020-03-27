package restaurant.services.wrappers;

import restaurant.dish.Dish;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "dishes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dishes {

    @XmlElement(name = "dish")
    private ArrayList<Dish> dishes = null;

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> employees) {
        this.dishes = employees;
    }

}
