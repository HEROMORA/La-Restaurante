package restaurant.models.dish;

import java.math.BigDecimal;

public class Desert extends Dish {

    public Desert(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price,0.2);
    }
}
