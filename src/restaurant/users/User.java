package restaurant.users;

public abstract class User {
    private String name;
    private String username;
    private String password;
    private UserRole userRole;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public boolean isEmployee()
    {
        return userRole != UserRole.CUSTOMER;
    }
}
