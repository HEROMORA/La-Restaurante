package restaurant.dish;

import java.math.BigDecimal;

public class MainDish extends Dish {
    public static final double taxes = 0.15;

    public MainDish(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price);
    }
}
