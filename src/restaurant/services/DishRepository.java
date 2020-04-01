package restaurant.services;


import restaurant.dish.Dish;
import restaurant.dish.DishType;

import java.util.ArrayList;

public class DishRepository {

    private RestaurantService restaurantService = new RestaurantService();
    private OrderRepository orderRepository = new OrderRepository();
    private ArrayList<Dish> dishes;


    public DishRepository()
    {
        populateList();
    }

    private void populateList()
    {
        dishes = restaurantService.readDishes();
    }

    // READING FUNCTIONS

    public ArrayList<Dish> getDishes()
    {
        return dishes;
    }

    public Dish getDishByName(String dishName)
    {
        for (Dish dish:dishes)
        {
            if (dish.getName().equals(dishName))
                return dish;
        }

        return null;
    }

    public ArrayList<Dish> getDishesByType(DishType dishType)
    {
        ArrayList<Dish> _dishes = new ArrayList<>();

        for(Dish dish:dishes)
        {
            if (dish.getDishType() == dishType)
                _dishes.add(dish);
        }

        return _dishes;
    }
}
