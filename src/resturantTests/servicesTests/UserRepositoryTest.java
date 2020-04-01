package resturantTests.servicesTests;

import org.junit.jupiter.api.Test;
import restaurant.data.repositories.UserRepository;
import restaurant.users.User;
import restaurant.users.UserRole;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @Test
    public void testLogin()
    {
        userRepository = new UserRepository();
        var username = "brian";
        var password = "mdir@admj%ar5qX2";

        var result = userRepository.login(username, password);

        assertEquals(result.getUsername(),username);
        assertEquals(result.getPassword(),password);

    }
    @Test
    public void testFalseLogin(){
        userRepository = new UserRepository();
        var username = "invalidUser";
        var password = "invalidPassword";

        var result = userRepository.login(username, password);

        assertNull(result.getUsername());
        assertNull(result.getPassword());
    }

    @Test
    public void testSignUp() {
        userRepository = new UserRepository();
        var username = "amostafa";
        var password = "123456";
        var name = "mostafaadell";
        var role = UserRole.CUSTOMER;

        User user = userRepository.signUp(username,password,name,role);
        User userAdded = userRepository.getUserByUsername(username);

        assertEquals(user.getUsername(),userAdded.getUsername());
        assertEquals(user.getPassword(),userAdded.getPassword());
    }

    @Test
    public void getUserByUsername()
    {
        userRepository = new UserRepository();
        var username = "brian";
        var user = userRepository.getUserByUsername(username);

        assertEquals(UserRole.CUSTOMER ,user.getUserRole());
    }
}
