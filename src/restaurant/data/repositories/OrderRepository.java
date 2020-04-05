package restaurant.data.repositories;

import restaurant.appUtils.AppUtilities;
import restaurant.data.services.OrderService;
import restaurant.data.services.Service;
import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;
import restaurant.models.order.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderRepository {

    // This Class provides the querying and data manipulating for the orders objects

    private Service<Order> orderService = new OrderService();
    private AppUtilities appUtilities = new AppUtilities();
    private ArrayList<Order> orders;

    public OrderRepository()
    {
        loadOrders();
    }

    // This function refreshes the orders list from the file  and to listen for new added elements
    private void loadOrders()
    {
        orders = orderService.readData();
    }

    // READING FUNCTIONS

    public ArrayList<Order> getOrders()
    {
        return orders;
    }

    public ArrayList<Order> getOrdersByStatus(OrderStatus orderStatus)
    {
        ArrayList<Order> _orders = new ArrayList<>();
        for (Order order:orders)
        {
            if (order.getOrderStatus() == orderStatus)
                _orders.add(order);
        }

        return _orders;
    }

    public Order getOrderByCustomerName(String customerName)
    {
        Order _order = null;
        for (Order order:orders) {
            if (order.getCustomerName().equals(customerName))
                _order = order;
        }

        return _order;
    }

    public ArrayList<Order> getTodayOrders()
    {
        ArrayList<Order> _orders = new ArrayList<>();
        for (Order order:orders)
        {
            if (appUtilities.isSameDay(order.getDate(), Calendar.getInstance().getTime()))
                _orders.add(order);
        }

        return _orders;
    }

    public BigDecimal getTodayTotalPrice()
    {
        return getTotalPrice(getTodayOrders());
    }

    public BigDecimal getTotalPrice(ArrayList<Order> _orders)
    {
        BigDecimal totalPrice = new BigDecimal("0.00");
        for(Order order:_orders)
        {
            totalPrice = order.calculateTotalPrice().add(totalPrice);
        }

        return totalPrice;
    }

    // WRITING FUNCTIONS

    public void saveOrder(Order order)
    {
        orderService.writeData(order);
    }

    // This function handles the creation of an order object
    public Order makeOrder(Date date, String customerName, int tableNum, ArrayList<OrderDetails> orderDetails) {

        Order order = new Order(date,OrderStatus.WAITING, tableNum, orderDetails);
        order.setCustomerName(customerName);

        return order;
    }
}
