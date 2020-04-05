package restaurant.models.order;

import restaurant.models.dish.Dish;

import java.math.BigDecimal;

public class OrderDetails {
    private int quantity;
    private Dish dish ;
    private String dishName = null;
    private BigDecimal orderDetailPrice;

    public int getQuantity() {
        return quantity;
    }

    public String getDishName() {
        return dish.getName();
    }

    public String getDishType() {return dish.getDishType().toString(); }

    public void setDishName(String dish) {
        this.dish.setName(dish);
        this.dishName = dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        this.dishName = dish.getName();
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Calculates the money for this order details without taxes
    public BigDecimal calculateSubTotal()
    {
        return dish.getPrice().multiply(new BigDecimal(quantity));
    }

    // Calculates the taxes for this orderDetail
    public BigDecimal calculateTax()
    {
        return calculateSubTotal().multiply(BigDecimal.valueOf(this.dish.getTaxes()));
    }

    // Calculate the total money for this OrderDetail including taxes
    public BigDecimal getOrderDetailPrice() {
        return calculateSubTotal().add(calculateTax());
    }
/*
    @Override
    public String toString()
    {
        return String.format("Dish: %s, Type: %s, Quantity: %d, Price: %s", dish.getName(),dish.getDishType(), quantity, dish.getPrice());
    }
*/
}
