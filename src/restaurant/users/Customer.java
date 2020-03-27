package restaurant.users;

public class Customer extends User {

    public Customer()
    {
        super();
    }

    @Override
    protected void setRole() {
        setRole(UserRole.CUSTOMER);
    }
}
