package com.resume;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton, registerButton;

    public LoginPage() {
        setTitle("Resume Screening System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

     
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/resume/images/logo.png"));

        
        Image img = icon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

       
        add(logoLabel, BorderLayout.NORTH);

        
        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if(email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this," Please fill all fields!");
                return;
            }

            try(Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    
                    new Dashboard(email);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this," Invalid email or password!");
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

        } else if(e.getSource() == registerButton) {
            new RegisterPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
