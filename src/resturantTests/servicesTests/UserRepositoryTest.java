package resturantTests.servicesTests;

import org.junit.jupiter.api.Test;
import restaurant.services.UserRepository;
import restaurant.users.Customer;
import restaurant.users.User;
import restaurant.users.UserRole;

import javax.xml.transform.TransformerException;

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

        assertTrue(result);

    }
    @Test
    public void testFalseLogin(){
        userRepository = new UserRepository();
        var username = "invalidUser";
        var password = "invalidPassword";

        var result = userRepository.login(username, password);

        assertFalse(result);
    }

    @Test
    public void testSignUp() throws TransformerException {
        userRepository = new UserRepository();
        var username = "mostafa";
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
