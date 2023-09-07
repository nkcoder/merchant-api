package my.playground.merchantapi.repository;

import my.playground.merchantapi.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldFindUserByEmail() {
        User newUser = new User(
                "Daniel",
                "daniel@gmail.com",
                "PassW0rd!",
                "Admin",
                LocalDateTime.now()
        );
        userRepository.save(newUser);

        Optional<User> maybeUser = userRepository.findByEmail("daniel@gmail.com");
        assertTrue(maybeUser.isPresent());

        User userFound = maybeUser.get();
        assertEquals("Daniel", userFound.getUserName());
        assertEquals("Admin", userFound.getUserType());
    }

    @AfterEach
    public void teardown() {

    }
}
