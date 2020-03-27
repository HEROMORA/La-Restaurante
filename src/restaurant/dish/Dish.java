package restaurant.dish;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "dish")
@XmlAccessorType(XmlAccessType.FIELD)
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


    @Override
    public String toString()
    {
        return String.format("%s, %s, %s",name, dishType.name(), price.toString());
    }
}
