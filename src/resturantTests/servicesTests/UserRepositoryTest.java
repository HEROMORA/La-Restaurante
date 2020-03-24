package resturantTests.servicesTests;

import org.junit.jupiter.api.Test;
import restaurant.services.UserRepository;
import restaurant.users.UserRole;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void testLogin()
    {
        var username1 = "brian";
        var password1 = "mdir@admj%ar5qX2";

        var username2 = "invalidUser";
        var password2 = "invalidPassword";

        var result1 = userRepository.login(username1, password1);
        var result2 = userRepository.login(username2, password2);
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void getUserByUsername()
    {
        var username = "brian";
        var user = userRepository.getUserByUsername(username);

        assertEquals(UserRole.CUSTOMER ,user.getUserRole());
    }
}
