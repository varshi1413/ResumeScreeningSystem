package com.resume;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    JButton submitResumeButton, logoutButton;
    String userEmail;

    public Dashboard(String email) {
        this.userEmail = email;

        setTitle("Resume Screening System - Dashboard");
        setSize(500,300);
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

        JPanel panel = new JPanel(new GridLayout(2,1,20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(40,100,40,100));

        submitResumeButton = new JButton("Submit Resume");
        submitResumeButton.addActionListener(this);
        panel.add(submitResumeButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        panel.add(logoutButton);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitResumeButton) {
            new ResumeForm(userEmail);
            dispose();
        } else if(e.getSource() == logoutButton) {
            new LoginPage();
            dispose();
        }
    }
}
