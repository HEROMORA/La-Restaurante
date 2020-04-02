package restaurant.models.dish;

import java.math.BigDecimal;

public class MainDish extends Dish {

    public MainDish(String name, DishType dishType, BigDecimal price) {
        super(name, dishType, price,0.15);
    }
}
