package restaurant.models.dish;

import java.math.BigDecimal;

public class Desert extends Dish {
    public static final double taxes = 0.2;

    public Desert(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price);
    }
}
