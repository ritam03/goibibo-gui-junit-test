import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HotelSearchGUITest {
    private HotelSearchGUI hotelSearchGUI;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        // Establish database connection
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hotelSearchGUI = new HotelSearchGUI();
        setPrivateField(hotelSearchGUI, "connection", connection);
    }

    @AfterEach
    public void tearDown() {
        // Close database connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchHotelsWithFirstValidInput() {
        hotelSearchGUI.placeField.setText("TIRUPATI");
        hotelSearchGUI.checkInField.setText("2024-03-28");
        hotelSearchGUI.checkOutField.setText("2024-03-29");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available hotels"));
        System.out.println("Test Case 1 Success for Valid Input");
    }

    @Test
    public void testSearchHotelsWithSecondValidInput() {
        hotelSearchGUI.placeField.setText("MUMBAI");
        hotelSearchGUI.checkInField.setText("2024-03-30");
        hotelSearchGUI.checkOutField.setText("2024-04-01");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available hotels"));
        System.out.println("Test Case 2 Success for Valid Input");
    }

    @Test
    public void testSearchHotelsWithThirdValidInput() {
        hotelSearchGUI.placeField.setText("CHENNAI");
        hotelSearchGUI.checkInField.setText("2024-04-01");
        hotelSearchGUI.checkOutField.setText("2024-04-02");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available hotels"));
        System.out.println("Test Case 3 Success for Valid Input");
    }

    @Test
    public void testSearchHotelsWithFourthValidInput() {
        hotelSearchGUI.placeField.setText("NEW DELHI");
        hotelSearchGUI.checkInField.setText("2024-03-29");
        hotelSearchGUI.checkOutField.setText("2024-03-30");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available hotels"));
        System.out.println("Test Case 4 Success for Valid Input");
    }

    @Test
    public void testSearchHotelsWithFirstInvalidInput() {
        hotelSearchGUI.placeField.setText("KOLKATA");
        hotelSearchGUI.checkInField.setText("2024-03-25");
        hotelSearchGUI.checkOutField.setText("2024-03-29");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available hotels")) ;
        System.out.println("Test Case 1 Success for Invalid Input");
    }

    @Test
    public void testSearchHotelsWithSecondInvalidInput() {
        hotelSearchGUI.placeField.setText("PUNE");
        hotelSearchGUI.checkInField.setText("2024-04-28");
        hotelSearchGUI.checkOutField.setText("2024-04-29");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available hotels")) ;
        System.out.println("Test Case 2 Success for Invalid Input");
    }

    @Test
    public void testSearchHotelsWithThirdInvalidInput() {
        hotelSearchGUI.placeField.setText("DUBAI");
        hotelSearchGUI.checkInField.setText("2024-03-28");
        hotelSearchGUI.checkOutField.setText("2025-03-29");

        hotelSearchGUI.searchHotels();

        String resultText = hotelSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available hotels")) ;
        System.out.println("Test Case 3 Success for Invalid Input");
    }


    // Utility method to set private fields using reflection
    private static void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
