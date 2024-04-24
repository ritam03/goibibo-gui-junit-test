import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightSearchGUITest {
    private FlightSearchGUI flightSearchGUI;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Establish database connection
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
        flightSearchGUI = new FlightSearchGUI(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close database connection
        if (connection != null) {
            connection.close();
        }
    }


    @Test
    public void testSearchFlightsWithFirstValidInput() {
        flightSearchGUI.sourceField.setText("PUNE");
        flightSearchGUI.destinationField.setText("GOA");
        flightSearchGUI.dateField.setText("2024-06-25");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available flights"));
        System.out.println("Test Case 1 Success for Valid Input");
    }

    @Test
    public void testSearchFlightsWithSecondValidInput() {
        flightSearchGUI.sourceField.setText("CHENNAI");
        flightSearchGUI.destinationField.setText("KOLKATA");
        flightSearchGUI.dateField.setText("2024-04-02");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available flights"));
        System.out.println("Test Case 2 Success for Valid Input");
    }

    @Test
    public void testSearchFlightsWithThirdValidInput() {
        flightSearchGUI.sourceField.setText("NANDED");
        flightSearchGUI.destinationField.setText("CHENNAI");
        flightSearchGUI.dateField.setText("2024-08-29");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available flights"));
        System.out.println("Test Case 3 Success for Valid Input");
    }

    @Test
    public void testSearchFlightsWithFourthValidInput() {
        flightSearchGUI.sourceField.setText("BIRMINGHAM");
        flightSearchGUI.destinationField.setText("LONDON");
        flightSearchGUI.dateField.setText("2024-07-10");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available flights"));
        System.out.println("Test Case 4 Success for Valid Input");
    }

    @Test
    public void testSearchFlightsWithFirstInvalidInput() {
        flightSearchGUI.sourceField.setText("GUWAHATI");
        flightSearchGUI.destinationField.setText("JAIPUR");
        flightSearchGUI.dateField.setText("2024-08-21");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available flights")) ;
        System.out.println("Test Case 5 Success for Invalid Input");
    }

    @Test
    public void testSearchFlightsWithSecondInvalidInput() {
        flightSearchGUI.sourceField.setText("NEW DELHI");
        flightSearchGUI.destinationField.setText("BENGALURU");
        flightSearchGUI.dateField.setText("2024-06-12");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available flights")) ;
        System.out.println("Test Case 6 Success for Invalid Input");
    }

    @Test
    public void testSearchFlightsWithThirdInvalidInput() {
        flightSearchGUI.sourceField.setText("KOLKATA");
        flightSearchGUI.destinationField.setText("GOA");
        flightSearchGUI.dateField.setText("2024-07-21");

        flightSearchGUI.searchFlights();

        String resultText = flightSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available flights")) ;
        System.out.println("Test Case 7 Success for Invalid Input");
    }
}
