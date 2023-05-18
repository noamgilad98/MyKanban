package Presntation;

import User.Factory;
import MyUtil.Response;
import Data.DTOs.ForgetPasswordDTO;
import User.UserService;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgetPasswordWindow extends JFrame {
    private UserService userService;
    private JTextField emailTextField;
    private JComboBox<String> safetyQuestionComboBox;
    private JTextField safetyAnswerTextField;

    public ForgetPasswordWindow(UserService userService) {
        this.userService = userService;

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Forgot Password");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Create labels and text fields for user input
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();

        JLabel safetyQuestionLabel = new JLabel("Safety Question:");
        safetyQuestionComboBox = new JComboBox<>();

        JLabel safetyAnswerLabel = new JLabel("Safety Answer:");
        safetyAnswerTextField = new JTextField();

        // Create a button to submit the data
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear previous error messages
                clearErrorLabels();

                String email = emailTextField.getText();
                String safetyQuestion = (String) safetyQuestionComboBox.getSelectedItem();
                String safetyAnswer = safetyAnswerTextField.getText();

                // Check for empty fields and display error messages
                if (email.isEmpty()) {
                    showErrorLabel(emailLabel, "Please enter your email.");
                }
                if (safetyAnswer.isEmpty()) {
                    showErrorLabel(safetyAnswerLabel, "Please enter the safety answer.");
                }

                // Check if any fields are empty
                if (email.isEmpty() || safetyAnswer.isEmpty()) {
                    return; // Stop further processing if any field is empty
                }

                // Create a ForgetPasswordDTO object
                ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO(email, safetyQuestion, safetyAnswer);
                Gson gson = new Gson();
                // Convert the ForgetPasswordDTO object to JSON data
                String jsonData = gson.toJson(forgetPasswordDTO);
                // Call UserService.getPassword with the JSON data
                String jsonResponse = userService.getPassword(jsonData);
                // Convert the JSON response to a Response object
                Response<String> response = gson.fromJson(jsonResponse, Response.class);
                // Display the password if the request is successful
                if (response.isSuccess()) {
                    JOptionPane.showMessageDialog(null, "Your password is: " + response.getData());
                }
                // Display the error message if the request is unsuccessful
                else {
                    JOptionPane.showMessageDialog(null, "Failed to retrieve password: " + response.getMessage());
                }

                // Reset the fields after processing
                emailTextField.setText("");
                safetyAnswerTextField.setText("");
            }

            private void showErrorLabel(JLabel label, String errorMessage) {
                label.setForeground(Color.RED);
                label.setText(errorMessage);
            }

            private void clearErrorLabels() {
                emailLabel.setForeground(Color.BLACK);
                safetyAnswerLabel.setForeground(Color.BLACK);

                emailLabel.setText("Email:");
                safetyAnswerLabel.setText("Safety Answer:");
            }
        });

        // Create a button to go back to the main login window
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of LoginWindow
                LoginWindow loginWindow = new LoginWindow(userService);
                // Dispose the current ForgetPasswordWindow
                dispose();
            }
        });

        // Add safety question options to the combo box
        String[] safetyQuestions = {
                "Your favorite movie?",
                "Your favorite album?",
                "How's your daddy?",
                "What's your favorite color?"
        };
        for (String question : safetyQuestions) {
            safetyQuestionComboBox.addItem(question);
        }

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(safetyQuestionLabel);
        panel.add(safetyQuestionComboBox);
        panel.add(safetyAnswerLabel);
        panel.add(safetyAnswerTextField);
        panel.add(submitButton);
        panel.add(backButton);


        // Add the panel to the JFrame
        add(panel);

        // Make the JFrame visible
        setVisible(true);
    }



    public static void main() {
        // Create an instance of UserService (replace with your actual implementation)
        UserService userService = Factory.getUserService();

        // Create an instance of ForgetPasswordWindow
        ForgetPasswordWindow forgetPasswordWindow = new ForgetPasswordWindow(userService);
    }
}
