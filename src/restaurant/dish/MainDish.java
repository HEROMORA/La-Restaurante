package restaurant.dish;

import java.util.Currency;

public class MainDish extends Dish {
    public static final double taxes = 0.15;

    public MainDish(int id, String name, restaurant.dish.dishType dishType, Currency price) {
        super(id, name, dishType, price);
    }
}
