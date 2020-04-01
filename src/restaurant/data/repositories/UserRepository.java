package restaurant.data.repositories;

import restaurant.data.services.Service;
import restaurant.data.services.UserService;
import restaurant.users.*;

import java.util.ArrayList;

public class UserRepository {
    private Service<User> userService = new UserService();
    private UserFactory userFactory = new UserFactory();

    // In Real-World App (Database-wise) this is not a good practice and a direct call should be made to the api
    // or direct call to database due to the sensitivity of user data

    private ArrayList<User> users = new ArrayList<>();

    public UserRepository()
    {
        populateList();
    }

    private void populateList()
    {
        users = userService.readData();
    }

    // READ FUNCTIONS

    public User login(String username, String password)
    {
        for (User user:users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }

        return null;
    }

    public User signUp(String username, String password, String name, UserRole role) {

        if (getUserByUsername(username) != null) {
            return null;
        }

        User user = userFactory.createUser(role.toString());
        user.setUserData(name, username, password);

        Service<User> us = new UserService();
        us.writeData(user);
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
