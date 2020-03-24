package restaurant.services;

import restaurant.users.*;

import javax.xml.transform.TransformerException;
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

    public User signUp(String username, String password, String name, UserRole role) throws TransformerException {
        if(getUserByUsername(username) == null){
            return null;
        }
        User user;
        switch (role) {
            case CUSTOMER:
                user = new Customer();
                user.setUserRole(UserRole.CUSTOMER);
                break;

            case MANAGER:
                user = new Manager();
                user.setUserRole(UserRole.MANAGER);
                break;

            case COOK:
                user = new Cook();
                user.setUserRole(UserRole.COOK);
                break;

            case WAITER:
                user = new Customer();
                user.setUserRole(UserRole.WAITER);
                break;

            default:
                throw new Error("Invalid role type");
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);

        RestaurantService rs = new RestaurantService();
        rs.writeUser(user);

        populateList();
        return user;
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
