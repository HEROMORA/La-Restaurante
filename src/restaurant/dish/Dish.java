package restaurant.dish;

import java.util.Currency;

public abstract class Dish {
    private int id;
    private String name;
    private dishType dishType;
    private Currency price;

    public static double taxes;

    public Dish(int id, String name, dishType dishType, Currency price) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.price = price;
    }

    public Currency getPrice()
    {
        return price;
    }

    public restaurant.dish.dishType getDishType() {
        return dishType;
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s",name, dishType.name(), price.toString());
    }
}
