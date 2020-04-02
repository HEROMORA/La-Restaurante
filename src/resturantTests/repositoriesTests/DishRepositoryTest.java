package resturantTests.repositoriesTests;

import org.junit.jupiter.api.Test;
import restaurant.models.dish.Dish;
import restaurant.models.dish.DishType;
import restaurant.data.repositories.DishRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishRepositoryTest {

    private DishRepository dishRepository = new DishRepository();

    @Test
    public void testGetAllDishes()
    {
        var dishes = dishRepository.getDishes();
        assertEquals(7, dishes.size());
    }

    @Test
    public void getDishByName()
    {
        var dishName = "Apple Pie";
        BigDecimal dishPrice = new BigDecimal("50.0");
        var dish = dishRepository.getDishByName(dishName);
        assertEquals(dishName, dish.getName());
        assertEquals(dishPrice, dish.getPrice());
    }

    @Test
    public void getDishesByType()
    {
        var dishType = DishType.APPETIZER;
        var dishes = dishRepository.getDishesByType(dishType);

        for (Dish dish:dishes) {
            assertEquals(dishType, dish.getDishType());
        }
    }
}
