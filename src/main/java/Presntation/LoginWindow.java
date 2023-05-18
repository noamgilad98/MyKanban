package Presntation;

import Data.DTOs.UserDTO;
import MyUtil.Response;
import User.Factory;
import Data.DTOs.LoginDTO;
import User.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginWindow extends JFrame {
    private UserService userService;
    private JTextField emailTextField;
    private JPasswordField passwordField;

    public LoginWindow(UserService userService) {
        this.userService = userService;

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Login");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Create labels and text fields for user input
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = new String(passwordField.getPassword());

                // Check if any fields are empty
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginWindow.this,
                            "Please enter both email and password.");
                    return;
                }

                Gson gson = new Gson();
                String jsonData = gson.toJson(new LoginDTO(email, password));

// Call UserService.login with the JSON data
                Response<?> response = gson.fromJson(userService.login(jsonData), new TypeToken<Response<UserDTO>>() {}.getType());
                if (response.isSuccess()) {
                    UserDTO userDTO = (UserDTO) response.getData();
                    Presentation.UserWindow.start(userDTO);
                    dispose(); // Close the current LoginWindow
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to log in: " + response.getMessage());
                }


                // Reset the fields after login attempt
                emailTextField.setText("");
                passwordField.setText("");

                // Reset the fields after successful login
                emailTextField.setText("");
                passwordField.setText("");
            }
        });

        // Create forget password button
        JButton forgetPasswordButton = new JButton("Forgot Password");
        forgetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the ForgetPasswordWindow
                ForgetPasswordWindow.main();

                // Note: Do not close the current window here
            }
        });


        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(forgetPasswordButton);

        // Add the panel to the JFrame
        add(panel);

        // Make the JFrame visible
        setVisible(true);
    }

    public static void main() {
        // Create an instance of UserService (replace with your actual implementation)
        UserService userService = Factory.getUserService();

        // Create an instance of LoginWindow
        LoginWindow loginWindow = new LoginWindow(userService);
    }
}
