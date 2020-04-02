package restaurant.models.dish;

import java.math.BigDecimal;

public class Appetizer extends Dish {

    public Appetizer(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price,0.1);
    }
}
