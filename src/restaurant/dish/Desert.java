package restaurant.dish;

import java.util.Currency;

public class Desert extends Dish {
    public static final double taxes = 0.2;

    public Desert(int id, String name, restaurant.dish.dishType dishType, Currency price) {
        super(id, name, dishType, price);
    }
}
