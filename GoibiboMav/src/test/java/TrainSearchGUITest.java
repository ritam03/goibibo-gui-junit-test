import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainSearchGUITest {
    private TrainSearchGUI trainSearchGUI;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Establish database connection
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
        trainSearchGUI = new TrainSearchGUI(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close database connection
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testSearchTrainsWithFirstValidInput() {
        trainSearchGUI.sourceField.setText("JANGIPUR");
        trainSearchGUI.destinationField.setText("HOWRAH");
        trainSearchGUI.dateField.setText("2024-04-01");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available trains"));
        System.out.println("Test Case 1 Success for Valid Input");
    }

    @Test
    public void testSearchTrainsWithSecondValidInput() {
        trainSearchGUI.sourceField.setText("NAGPUR");
        trainSearchGUI.destinationField.setText("KATPADI");
        trainSearchGUI.dateField.setText("2024-04-01");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available trains"));
        System.out.println("Test Case 2 Success for Valid Input");
    }

    @Test
    public void testSearchTrainsWithThirdValidInput() {
        trainSearchGUI.sourceField.setText("NANDED");
        trainSearchGUI.destinationField.setText("KATPADI");
        trainSearchGUI.dateField.setText("2024-05-12");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available trains"));
        System.out.println("Test Case 3 Success for Valid Input");
    }

    @Test
    public void testSearchTrainsWithFourthValidInput() {
        trainSearchGUI.sourceField.setText("BENGALURU");
        trainSearchGUI.destinationField.setText("CHENNAI");
        trainSearchGUI.dateField.setText("2024-07-18");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertTrue(resultText.contains("Available trains"));
        System.out.println("Test Case 4 Success for Valid Input");
    }

    @Test
    public void testSearchTrainsWithFirstInvalidInput() {
        trainSearchGUI.sourceField.setText("CHENNAI");
        trainSearchGUI.destinationField.setText("NEW DELHI");
        trainSearchGUI.dateField.setText("2024-04-25");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available trains"));
        System.out.println("Test Case 5 Success for Invalid Input");
    }

    @Test
    public void testSearchTrainsWithSecondInvalidInput() {
        trainSearchGUI.sourceField.setText("PUNE");
        trainSearchGUI.destinationField.setText("MUMBAI");
        trainSearchGUI.dateField.setText("2024-06-01");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available trains"));
        System.out.println("Test Case 6 Success for Invalid Input");
    }

    @Test
    public void testSearchTrainsWithThirdInvalidInput() {
        trainSearchGUI.sourceField.setText("NEW DELHI");
        trainSearchGUI.destinationField.setText("HOWRAH");
        trainSearchGUI.dateField.setText("2024-04-01");

        trainSearchGUI.searchTrains();

        String resultText = trainSearchGUI.resultArea.getText();
        assertFalse(resultText.contains("Available trains")) ;
        System.out.println("Test Case 7 Success for Invalid Input");
    }
}
