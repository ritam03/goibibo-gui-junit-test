import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class FlightSearchGUI extends JFrame {
    public JTextField sourceField, destinationField, dateField;
    public JTextArea resultArea;
    public Connection connection;

    public FlightSearchGUI(Connection connection) {
        this.connection = connection;
        initializeGUI();
        connectToDatabase();
    }

    private void initializeGUI() {
        setTitle("Goibibo Flight Search");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel sourceLabel = new JLabel("Source:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel dateLabel = new JLabel("Departure Date (YYYY-MM-DD):");
        sourceField = new JTextField(20);
        destinationField = new JTextField(20);
        dateField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(sourceLabel);
        panel.add(sourceField);
        panel.add(destinationLabel);
        panel.add(destinationField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(new JLabel()); // Placeholder
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });
    }

    public void connectToDatabase() {
        try {
            // Change the database URL, username, and password as per your configuration
            String url = "jdbc:mysql://localhost:3306/goibibo";
            String username = "root";
            String password = "ritam";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void searchFlights() {
        String source = sourceField.getText();
        String destination = destinationField.getText();
        String date = dateField.getText();

        if (source.isEmpty() || destination.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT flight_name FROM flights WHERE source=? AND destination=? AND departure_date=?");
            statement.setString(1, source);
            statement.setString(2, destination);
            statement.setString(3, date);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> flightList = new ArrayList<>();
            while (resultSet.next()) {
                flightList.add(resultSet.getString("flight_name"));
            }

            if (flightList.isEmpty()) {
                resultArea.setText("No flights available for the given source, destination, and date.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Available flights:\n");
                for (String flight : flightList) {
                    sb.append(flight).append("\n");
                }
                resultArea.setText(sb.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Connection finalConnection = connection;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlightSearchGUI(finalConnection).setVisible(true);
            }
        });
    }
}
