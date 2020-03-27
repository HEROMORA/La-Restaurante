package restaurant.users;

public abstract class User {
    private String name;
    private String username;
    private String password;
    private UserRole userRole;

    protected abstract void setRole();

    public User()
    {
        setRole();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isEmployee()
    {
        return userRole != UserRole.CUSTOMER;
    }


    protected void setRole(UserRole userRole)
    {
        this.userRole = userRole;
    }

    public void setUserData(String name, String username, String password)
    {
        setName(name);
        setUsername(username);
        setPassword(password);
    }
}
