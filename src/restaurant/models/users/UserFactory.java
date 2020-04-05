package restaurant.models.users;

public class UserFactory {

    // Implementing the factory design pattern to ease the process of creating the user

    public User createUser(String userType)
    {
        if (userType == null || userType.isEmpty())
            return null;

        switch (userType.toLowerCase())
        {
            case ("client"):
            case("customer"):
                return new Customer();

            case("cook"):
            case("cooker"):
                return new Cook();

            case("waiter"): return new Waiter();

            case("manager"): return new Manager();

            default: return null;
        }
    }
}
