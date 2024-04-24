import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class HotelSearchGUI extends JFrame {
    public JTextField placeField, checkInField, checkOutField;
    public JTextArea resultArea;
    public Connection connection;

    public HotelSearchGUI() {
        setTitle("Goibibo Hotel Search");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Goibibo Hotel Search");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        inputPanel.add(new JLabel("Place:"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        placeField = new JTextField(20);
        inputPanel.add(placeField, gbc);
        gbc.gridy++;
        checkInField = new JTextField(20);
        inputPanel.add(checkInField, gbc);
        gbc.gridy++;
        checkOutField = new JTextField(20);
        inputPanel.add(checkOutField, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton searchButton = new JButton("Search");
        buttonPanel.add(searchButton);

        // Result Panel
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchHotels();
            }
        });

        // Connect to the database
        connectToDatabase();
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

    void searchHotels() {
        String place = placeField.getText();
        String checkInDate = checkInField.getText();
        String checkOutDate = checkOutField.getText();

        if (place.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT hotel_name FROM hotels WHERE place=? AND check_in_date=? AND check_out_date=?");
            statement.setString(1, place);
            statement.setString(2, checkInDate);
            statement.setString(3, checkOutDate);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> hotelList = new ArrayList<>();
            while (resultSet.next()) {
                hotelList.add(resultSet.getString("hotel_name"));
            }

            if (hotelList.isEmpty()) {
                resultArea.setText("No hotels available for the given place, check-in and check-out date.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Available hotels:\n");
                for (String hotel : hotelList) {
                    sb.append(hotel).append("\n");
                }
                resultArea.setText(sb.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelSearchGUI().setVisible(true);
            }
        });
    }
}
