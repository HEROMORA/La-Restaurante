package restaurant.models.dish;


import java.math.BigDecimal;

public abstract class Dish {
    private String name;
    private DishType dishType;
    private BigDecimal price;

    public static double taxes;

    public Dish(String name, DishType dishType, BigDecimal price) {
        this.name = name;
        this.dishType = dishType;
        this.price = price;
    }
    public Dish(){

    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s",name, dishType.name(), price.toString());
    }
}
