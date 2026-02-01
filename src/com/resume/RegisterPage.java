package com.resume;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame implements ActionListener {

    JTextField nameField, emailField, phoneField;
    JPasswordField passwordField;
    JButton registerButton, loginButton;

    public RegisterPage() {
        setTitle("Resume Screening System - Register");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
         
     // Load the image
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/resume/images/logo.png"));

        // Scale the image to a smaller size (e.g., width=200, height=80)
        Image img = icon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        // Create JLabel with scaled image
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add to top of frame
        add(logoLabel, BorderLayout.NORTH);
        
        JPanel panel = new JPanel(new GridLayout(5,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);

        loginButton = new JButton("Go to Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please fill all required fields!");
                return;
            }

            try(Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(name,email,password,phone) VALUES(?,?,?,?)"
                );
                ps.setString(1,name);
                ps.setString(2,email);
                ps.setString(3,password);
                ps.setString(4,phone);

                int result = ps.executeUpdate();
                if(result>0) {
                    JOptionPane.showMessageDialog(this," Registered Successfully!");
                    new LoginPage();
                    dispose();
                }
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this," Error: "+ex.getMessage());
            }

        } else if(e.getSource() == loginButton) {
            new LoginPage();
            dispose();
        }
    }
}
