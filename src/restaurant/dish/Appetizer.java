package restaurant.dish;

import java.util.Currency;

public class Appetizer extends Dish {
    public static final double taxes = 0.1;

    public Appetizer(int id, String name, restaurant.dish.dishType dishType, Currency price) {
        super(id, name, dishType, price);
    }
}
