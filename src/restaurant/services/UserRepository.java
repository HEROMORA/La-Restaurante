package restaurant.services;

import restaurant.users.User;

import java.util.ArrayList;

public class UserRepository {
    private RestaurantService restaurantService = new RestaurantService();

    // In Real-World App (Database-wise) this is not a good practice and a direct call should be made to the api
    // or direct call to database due to the sensitivity of user data

    private ArrayList<User> users = new ArrayList<>();

    public UserRepository()
    {
        populateList();
    }

    private void populateList()
    {
        users = restaurantService.readUsers();
    }

    // READ FUNCTIONS

    public boolean login(String username, String password)
    {
        for (User user:users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }

        return false;
    }

    public User getUserByUsername(String username)
    {
        for(User user:users)
        {
            if (user.getUsername().equals(username))
                return user;
        }

        return null;
    }
}
