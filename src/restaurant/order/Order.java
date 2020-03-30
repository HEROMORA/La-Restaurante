package restaurant.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Date date;
    private OrderStatus orderStatus;
    private int tableNumber;

    private ArrayList<OrderDetails> ordersDetails;

    public Order()
    {

    }

    public Order(Date date, OrderStatus orderStatus, int tableNumber, ArrayList<OrderDetails> orderDetails)
    {
        this.date = date;
        this.orderStatus = orderStatus;
        this.tableNumber = tableNumber;
        this.ordersDetails = orderDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        OrderStatus result;
        switch (orderStatus.toLowerCase())
        {
            case "waiting":
                result = OrderStatus.WAITING;
                break;
            case "ongoing":
                result = OrderStatus.ONGOING;
                break;
            case "finished":
                result = OrderStatus.FINISHED;
                break;
            default:
                result = null;
        }

        setOrderStatus(result);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<OrderDetails> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(ArrayList<OrderDetails> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }


    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
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
