package restaurant.models.users;

public class Waiter extends Employee {
    public Waiter()
    {
        super();
    }

    @Override
    protected void setRole() {
        setRole(UserRole.WAITER);
    }
}
