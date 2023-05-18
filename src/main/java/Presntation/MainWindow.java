package Presntation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call LoginWindow main method
                LoginWindow.main();
            }
        });
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call RegisterWindow main method
                RegisterWindow.main();
            }
        });
        add(registerButton);

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public static void main() {
        // Create an instance of MainWindow
        MainWindow mainWindow = new MainWindow();
    }
}
