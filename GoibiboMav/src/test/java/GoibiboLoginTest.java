import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class GoibiboLoginTest {
    private GoibiboLogin goibiboLogin = new GoibiboLogin(); // Initialize goibiboLogin here
    private Connection connection;
    private Statement statement;

    @BeforeEach
    public void setUp() throws Exception {
        // Establish database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
        statement = connection.createStatement();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Close database connection
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }



    @Test
    public void testLoginFirstValidCredentials() {
        try {
            // Validate login with existing user credentials in the database
            boolean loggedIn = goibiboLogin.validateLogin("arnab98pal@gmail.com", "2000");

            assertTrue(loggedIn, "Login with valid credentials should succeed.");
            System.out.println("Test Case 1 passed for First Login Valid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginSecondValidCredentials() {
        try {
            // Validate login with existing user credentials in the database
            boolean loggedIn = goibiboLogin.validateLogin("Keerthi123@gmail.com", "Welcome");
            assertTrue(loggedIn, "Login with valid credentials should succeed.");
            System.out.println("Test Case 2 passed for Second Login Valid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginThirdValidCredentials() {
        try {
            // Validate login with existing user credentials in the database
            boolean loggedIn = goibiboLogin.validateLogin("ritam03pal@gmail.com", "17122003");

            assertTrue(loggedIn, "Login with valid credentials should succeed.");
            System.out.println("Test Case 3 passed for Third Login Valid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginFourthValidCredentials() {
        try {
            // Validate login with existing user credentials in the database
            boolean loggedIn = goibiboLogin.validateLogin("Sahil21@gmail.com", "Sahil@18");

            assertTrue(loggedIn, "Login with valid credentials should succeed.");
            System.out.println("Test Case 4 passed for Fourth Login Valid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginFirstInvalidCredentials() {
        try {
            // Validate login with invalid credentials
            boolean loggedIn = goibiboLogin.validateLogin("ritampal@gmail.com", "AmiKKR");

            assertFalse(loggedIn, "Login with invalid credentials should fail.");
            System.out.println("Test Case 5 passed for First Login invalid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginSecondInvalidCredentials() {
        try {
            // Validate login with invalid credentials
            boolean loggedIn = goibiboLogin.validateLogin("SahilBhai@gmail.com", "WhistleLodu");

            assertFalse(loggedIn, "Login with invalid credentials should fail.");
            System.out.println("Test Case 6 passed for Second Login invalid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testLoginThirdInvalidCredentials() {
        try {
            // Validate login with invalid credentials
            boolean loggedIn = goibiboLogin.validateLogin("KeerthiBhai@gmail.com", "ESalaCupNamde");

            assertFalse(loggedIn, "Login with invalid credentials should fail.");
            System.out.println("Test Case 7 passed for Third Login invalid credentials");
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }
}
