package restaurant.data.repositories;

import restaurant.data.services.Service;
import restaurant.data.services.UserService;
import restaurant.models.users.*;

import java.util.ArrayList;

public class UserRepository {

    // This Class provides the querying and data manipulating for the users objects

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

    // this function checks if the user's credentials meets any in the xml file
    public User login(String username, String password)
    {
        for (User user:users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }

        return null;
    }

    // Overloading for the Signup method
    public User signUp(String username, String password, String name, UserRole role) {
        return signUp(username, password, name, role.toString());
    }

    // Allows to create new users and add it to the application
    public User signUp(String username, String password, String name, String role) {

        if (getUserByUsername(username) != null) {
            return null;
        }

        User user = userFactory.createUser(role);
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
