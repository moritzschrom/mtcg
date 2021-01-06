package co.schrom.user;

import co.schrom.database.DatabaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    static UserService userService;

    @BeforeAll
    static void beforeAll() {
        userService = UserService.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        // Delete user with id -1 before every test case
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM users WHERE id = -1;");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with a valid ID")
    void testGetUser__validId() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");

            // act
            User user = (User) userService.getUser(-1);

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE id = -1");
            sm.close();
            conn.close();

            // assert
            assertEquals("user", user.getUsername());
            assertEquals(-1, user.getId());
            assertEquals("password", user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with an invalid ID")
    void testGetUser__invalidId() {
        // act
        User user = (User) userService.getUser(-1);

        // assert
        assertNull(user);
    }

    @Test
    @DisplayName("Get a user with a valid username")
    void testGetUserByUsername__validUsername() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");

            // act
            User user = (User) userService.getUserByUsername("user");

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE id = -1");
            sm.close();
            conn.close();

            // assert
            assertNotNull(user);
            assertEquals("user", user.getUsername());
            assertEquals(-1, user.getId());
            assertEquals("password", user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with an invalid ID")
    void testGetUserByUsername__invalidUsername() {
        // arrange
        String username = "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb";

        // act
        User user = (User) userService.getUserByUsername(username);

        // assert
        assertNull(user);
    }

    @Test
    @DisplayName("Get all users")
    void testGetUsers() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");

            // act
            List<UserInterface> users = userService.getUsers();

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE id = -1");
            sm.close();
            conn.close();

            // assert
            assertNotNull(users);
            assertTrue(users.size() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Add a user")
    void testAddUser() {
        try {
            // arrange
            // use hashes as username and password to prevent duplicates
            String username = "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb";
            String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

            // act
            User user = (User) userService.addUser(User.builder().username(username).password(password).build());

            // assert
            assertNotNull(user.getUsername());
            assertNotNull(user.getPassword());
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, username, password FROM users WHERE username = '" + user.getUsername() + "';");
            assertTrue(rs.next());
            assertTrue(rs.getInt(1) >= 0); // auto incremented column
            assertEquals(username, rs.getString(2));
            assertEquals(password, rs.getString(3));

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE username = '" + username + "';");
            rs.close();
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Try adding a user with an existing username")
    void testAddUser__duplicateUsername() {
        try {
            // arrange
            // use hashes as username and password to prevent duplicates
            String username = "14f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb";
            String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(username, password) VALUES('" + username + "', '" + password + "');");

            // act
            User user = (User) userService.addUser(User.builder().username(username).password(password).build());

            // assert
            assertNull(user);

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE username = '" + username + "';");
            sm.close();
            conn.close();
        } catch (SQLException ignored) {

        }
    }

    @Test
    @DisplayName("Update a user")
    void testUpdateUser() {
        try {
            // arrange
            String username = "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb";
            String username2 = "bbc2711bf1d1c152b21b00cdadf89fa637c76238ffc871b17e1bb5ca9908a104";
            String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1,'" + username + "', '" + password + "');");

            // act
            User user = (User) userService.updateUser(-1, User.builder().username(username2).build());

            // assert
            assertNotNull(user);
            assertEquals(user.getPassword(), password);
            assertEquals(user.getUsername(), username2);

            // cleanup
            sm.executeUpdate("DELETE FROM users WHERE id = -1;");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete a user")
    void testDeleteUser() {
        try {
            // arrange
            String username = "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb";
            String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1,'" + username + "', '" + password + "');");

            // act
            boolean result = userService.deleteUser(-1);

            // assert
            assertTrue(result);
            ResultSet rs = sm.executeQuery("SELECT id FROM users WHERE id = -1;");
            assertFalse(rs.next());

            // cleanup
            rs.close();
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
