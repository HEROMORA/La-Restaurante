package restaurant.data.repositories;


import restaurant.data.services.DishService;
import restaurant.data.services.Service;
import restaurant.models.dish.Dish;
import restaurant.models.dish.DishType;

import java.util.ArrayList;

public class DishRepository {

    // This Class provides the querying and data manipulating for the dishes objects

    private Service<Dish> dishService = new DishService();
    private ArrayList<Dish> dishes;


    public DishRepository()
    {
        populateList();
    }

    private void populateList()
    {
        dishes = dishService.readData();
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
