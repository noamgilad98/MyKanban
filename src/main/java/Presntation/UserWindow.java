package Presentation;

import Data.DTOs.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class UserWindow extends JFrame {
    private String responseData;

    public UserWindow(UserDTO  userDTO) {
        this.responseData = responseData;

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Window");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a label for the greeting
        JLabel greetingLabel = new JLabel(getGreetingBasedOnTime());
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a panel for boards and tables
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));

        // Create a panel for boards
        JPanel boardsPanel = new JPanel();
        boardsPanel.setBorder(BorderFactory.createTitledBorder("Boards"));
        // Add board components to the boardsPanel

        // Create a panel for tables
        JPanel tablesPanel = new JPanel();
        tablesPanel.setBorder(BorderFactory.createTitledBorder("Tables"));
        // Add table components to the tablesPanel

        // Add boardsPanel and tablesPanel to contentPanel
        contentPanel.add(boardsPanel);
        contentPanel.add(tablesPanel);

        // Add greetingLabel and contentPanel to the main panel
        panel.add(greetingLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Add the main panel to the JFrame
        add(panel);

        // Make the JFrame visible
        setVisible(true);
    }

    private String getGreetingBasedOnTime() {
        LocalTime currentTime = LocalTime.now();
        String greeting;

        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.NOON)) {
            greeting = "Good Morning!";
        } else if (currentTime.isAfter(LocalTime.NOON) && currentTime.isBefore(LocalTime.of(18, 0))) {
            greeting = "Good Afternoon!";
        } else if (currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.MIDNIGHT)) {
            greeting = "Good Evening!";
        } else {
            greeting = "Good Night!";
        }

        return greeting;
    }

    public static void start(UserDTO data) {
        // Assuming the "data" parameter contains the response data from login

        // Create an instance of UserWindow
        UserWindow userWindow = new UserWindow( data);
    }

    // Rest of your UserWindow code...
}
