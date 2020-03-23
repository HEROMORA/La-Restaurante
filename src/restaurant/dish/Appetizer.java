package restaurant.dish;

import java.math.BigDecimal;

public class Appetizer extends Dish {
    public static final double taxes = 0.1;

    public Appetizer(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price);
    }
}
