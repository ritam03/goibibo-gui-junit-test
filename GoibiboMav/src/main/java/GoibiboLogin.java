import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GoibiboLogin extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection connection;
    private Statement statement;

    public GoibiboLogin() {
        setTitle("Goibibo Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);
        JLabel goLabel = new JLabel("Go");
        goLabel.setForeground(Color.ORANGE);
        goLabel.setFont(new Font("Arial", Font.BOLD, 32));
        JLabel ibiboLabel = new JLabel("ibibo");
        ibiboLabel.setForeground(new Color(33, 150, 243)); // Goibibo blue color
        ibiboLabel.setFont(new Font("Arial", Font.BOLD, 32));
        JLabel dotComLabel = new JLabel(".com");
        dotComLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        headerPanel.add(goLabel);
        headerPanel.add(ibiboLabel);
        headerPanel.add(dotComLabel);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        centerPanel.add(emailLabel);
        centerPanel.add(emailField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setBackground(new Color(33, 150, 243)); // Goibibo blue color
        loginButton.setForeground(Color.WHITE);
        buttonPanel.add(loginButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/goibibo", "root", "ritam");
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            } else {
                System.err.println("Failed to establish database connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception occurred while establishing database connection: " + e.getMessage());
        }

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                boolean loggedIn = validateLogin(email, password);
                if (loggedIn) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
                }
            }
        });
    }

    boolean validateLogin(String email, String password) {
        try {
            String query = "SELECT * FROM goibibo_users WHERE email='" + email + "' AND password='" + password + "'";
            System.out.println("Executing query: " + query);
            ResultSet resultSet = statement.executeQuery(query);

            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            System.out.println("Result set size: " + count);

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GoibiboLogin().setVisible(true);
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
