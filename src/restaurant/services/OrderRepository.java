package restaurant.services;

import restaurant.appUtils.AppUtilities;
import restaurant.order.Order;
import restaurant.order.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderRepository {

    private RestaurantService restaurantService = new RestaurantService();
    private AppUtilities appUtilities = new AppUtilities();
    private ArrayList<Order> orders;

    public OrderRepository()
    {
        loadOrders();
    }

    private void loadOrders()
    {
        orders = restaurantService.readOrders();
    }

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
        for (Order order:orders) {
            if (order.getCustomerName().equals(customerName))
                return order;
        }

        return null;
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

    public void saveOrder(Order order)
    {
        restaurantService.writeOrder(order);
    }
}
