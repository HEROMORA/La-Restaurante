package restaurant.models.users;

public class Manager extends Employee {
    public Manager()
    {
        super();
    }

    @Override
    protected void setRole() {
        setRole(UserRole.MANAGER);
    }
}
