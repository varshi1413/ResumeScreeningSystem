package com.resume;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResumeForm extends JFrame implements ActionListener {

    JTextField educationField, experienceField, skillsField;
    JButton submitButton, backButton;
    String userEmail;

    public ResumeForm(String email) {
        this.userEmail = email;

        setTitle("Submit Resume");
        setSize(500,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

     
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/resume/images/logo.png"));

        
        Image img = icon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        // Create JLabel with scaled image
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add to top of frame
        add(logoLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        panel.add(new JLabel("Education:"));
        educationField = new JTextField();
        panel.add(educationField);

        panel.add(new JLabel("Experience:"));
        experienceField = new JTextField();
        panel.add(experienceField);

        panel.add(new JLabel("Skills:"));
        skillsField = new JTextField();
        panel.add(skillsField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        panel.add(submitButton);

        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {
            String education = educationField.getText();
            String experience = experienceField.getText();
            String skills = skillsField.getText();

            if(education.isEmpty() || experience.isEmpty() || skills.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Fill all fields!");
                return;
            }

            try(Connection conn = DBConnection.getConnection()) {
                // Get user_id
                PreparedStatement ps1 = conn.prepareStatement("SELECT user_id FROM users WHERE email=?");
                ps1.setString(1,userEmail);
                ResultSet rs = ps1.executeQuery();
                int userId = 0;
                if(rs.next()) userId = rs.getInt("user_id");

                PreparedStatement ps2 = conn.prepareStatement(
                        "INSERT INTO resumes(user_id,education,experience,skills) VALUES(?,?,?,?)"
                );
                ps2.setInt(1,userId);
                ps2.setString(2,education);
                ps2.setString(3,experience);
                ps2.setString(4,skills);

                int result = ps2.executeUpdate();
                if(result>0) {
                    JOptionPane.showMessageDialog(this," Resume Submitted Successfully!");
                    new Dashboard(userEmail);
                    dispose();
                }

            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this," Error: "+ex.getMessage());
            }

        } else if(e.getSource() == backButton) {
            new Dashboard(userEmail);
            dispose();
        }
    }
}
