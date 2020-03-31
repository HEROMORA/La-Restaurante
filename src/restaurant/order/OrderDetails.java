package restaurant.order;

import restaurant.dish.Dish;

import java.math.BigDecimal;

public class OrderDetails {
    private int quantity;
    private Dish dish;

    public int getQuantity() {
        return quantity;
    }

    public String getDishName() {
        return dish.getName();
    }

    public String getDishType() {return dish.getDishType().toString(); }

    public void setDishName(String dish) { this.dish.setName(dish); }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal calculateSubTotal()
    {
        return dish.getPrice().multiply(new BigDecimal(quantity));
    }

    public BigDecimal calculateTax()
    {
        return calculateSubTotal().multiply(new BigDecimal(dish.taxes));
    }

    @Override
    public String toString()
    {
        return String.format("Dish: %s, Type: %s, Quantity: %d, Price: %s", dish.getName(),dish.getDishType(), quantity, dish.getPrice());
    }

}
