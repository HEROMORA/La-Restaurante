package restaurant.models.users;

public class Cook extends Employee {
    public Cook()
    {
        super();
    }

    @Override
    protected void setRole() {
        setRole(UserRole.COOK);
    }
}
