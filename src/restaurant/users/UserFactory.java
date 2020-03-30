package restaurant.users;

public class UserFactory {

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
