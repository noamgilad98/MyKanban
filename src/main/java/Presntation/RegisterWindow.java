package Presntation;
import User.Factory;
import MyUtil.Response;
import User.UserService;
import com.google.gson.Gson;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JFrame {

    private final UserService userService;

    public RegisterWindow(UserService userService) {
        this.userService = userService;

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Registration");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        // Create labels and text fields for user input

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordTextField = new JTextField();

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameTextField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameTextField = new JTextField();

        JLabel safetyQuestionLabel = new JLabel("Safety Question:");
        JComboBox<String> safetyQuestionComboBox = new JComboBox<>();

        // Add safety question options to the combo box
        String[] safetyQuestions = {
                "Your favorite movie?",
                "Your favorite Album?",
                "How's your daddy?",
                "What's your favorite color?",
        };
        for (String question : safetyQuestions) {
            safetyQuestionComboBox.addItem(question);
        }

        JLabel safetyAnswerLabel = new JLabel("Safety Answer:");
        JTextField safetyAnswerTextField = new JTextField();

        // Create a button to register the user
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear previous error messages
                clearErrorLabels();



                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String safetyQuestion = (String) safetyQuestionComboBox.getSelectedItem();
                String safetyAnswer = safetyAnswerTextField.getText();

                // Check for empty fields and display error messages
                if (email.isEmpty()) {
                    showErrorLabel(emailLabel, "Please enter your email.");
                }
                if (password.isEmpty()) {
                    showErrorLabel(passwordLabel, "Please enter your password.");
                }
                if (firstName.isEmpty()) {
                    showErrorLabel(firstNameLabel, "Please enter your first name.");
                }
                if (lastName.isEmpty()) {
                    showErrorLabel(lastNameLabel, "Please enter your last name.");
                }
                if (safetyAnswer.isEmpty()) {
                    showErrorLabel(safetyAnswerLabel, "Please enter the safety answer.");
                }

                // Check if any fields are empty
                if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || safetyAnswer.isEmpty()) {
                    return; // Stop further processing if any field is empty
                }
                // Retrieve user input values


                // Create a Gson object to serialize the data
                Gson gson = new Gson();
                String jsonData = (createUserJson( email, password, firstName, lastName, safetyQuestion, safetyAnswer));

                // Call UserService.addNewUser with the JSON data
                Response<?> response = gson.fromJson(userService.addNewUser(jsonData), Response.class);
                if (response.isSuccess()) {
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to register user: " + response.getMessage());
                }
            }
            private void showErrorLabel(JLabel label, String errorMessage) {
                label.setForeground(Color.RED);
                label.setText(errorMessage);
            }

            private void clearErrorLabels() {

                emailLabel.setForeground(Color.BLACK);
                passwordLabel.setForeground(Color.BLACK);
                firstNameLabel.setForeground(Color.BLACK);
                lastNameLabel.setForeground(Color.BLACK);
                safetyAnswerLabel.setForeground(Color.BLACK);


                emailLabel.setText("Email:");
                passwordLabel.setText("Password:");
                firstNameLabel.setText("First Name:");
                lastNameLabel.setText("Last Name:");
                safetyAnswerLabel.setText("Safety Answer:");
            }
        });

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(lastNameLabel);
        panel.add(lastNameTextField);
        panel.add(safetyQuestionLabel);
        panel.add(safetyQuestionComboBox);
        panel.add(safetyAnswerLabel);
        panel.add(safetyAnswerTextField);
        panel.add(registerButton);

        // Add the panel to the JFrame
        add(panel);

        // Make the JFrame visible
        setVisible(true);
    }

    private String createUserJson(String email, String password, String firstName, String lastName, String safetyQuestion, String safetyAnswer) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"email\": \"").append(email).append("\",");
        jsonBuilder.append("\"password\": \"").append(password).append("\",");
        jsonBuilder.append("\"firstName\": \"").append(firstName).append("\",");
        jsonBuilder.append("\"lastName\": \"").append(lastName).append("\",");
        jsonBuilder.append("\"safetyQuestion\": \"").append(safetyQuestion).append("\",");
        jsonBuilder.append("\"safetyAnswer\": \"").append(safetyAnswer).append("\"");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
    //get list of objects and return json


    public static void main() {
        // Create an instance of UserService (replace with your actual implementation)
        UserService userService = Factory.getUserService();

        // Create an instance of UserInterface
        RegisterWindow userInterface = new RegisterWindow(userService);
    }
}
