package restaurant.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Date date;
    private OrderStatus orderStatus;

    private ArrayList<OrderDetails> ordersDetails;

    public Order(Date date, OrderStatus orderStatus, ArrayList<OrderDetails> orderDetails)
    {
        this.date = date;
        this.orderStatus = orderStatus;
        this.ordersDetails = orderDetails;
    }

    public BigDecimal calculateTotalPrice()
    {
        BigDecimal total = new BigDecimal("0.00");
        for(OrderDetails od: ordersDetails)
        {
            total = total.add(od.calculateSubTotal());
        }

        total = total.add(calculateTax());
        return total;
    }

    public BigDecimal calculateTax()
    {
        BigDecimal tax = new BigDecimal("0.00");
        for(OrderDetails od: ordersDetails)
        {
            tax = tax.add(od.calculateTax());
        }
        return tax;
    }
}
