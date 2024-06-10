
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LoginWindow extends JFrame implements  ActionListener
{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    public LoginWindow()
    {
        createAndShowGUI();
    }
    private void createAndShowGUI()
    {
        setTitle("Login Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        JLabel welcomeLabel = new JLabel("Welcome Admission Entrance Test", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(135, 206, 250));
        panel.add(welcomeLabel);
        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);
        usernameField = new JTextField(15);
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordField = new JPasswordField(15);
        panel.add(passwordField);
    	loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);
        add(panel);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == loginButton)
        {
            if (usernameField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
                return;
            }
            if (passwordField.getPassword().length == 0)
            {
                JOptionPane.showMessageDialog(this, "Please enter a password.");
                return;
            }
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateCredentials(username, password))
            {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose(); // Close the LoginWindow
                new MCQWindow(username); // Open the MCQWindow
            } else

            {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        }
    }
  private boolean validateCredentials(String username, String password)
  {
    String url = "jdbc:mysql://localhost:/proj2";
    String user = "root";
    String passwordDB = "";
    SQLException ex = null;
    try (Connection connection = DriverManager.getConnection(url, user, passwordDB))
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    catch (SQLException e)
    {
        ex = e;
    }
    if (ex != null)
    {
        ex.printStackTrace();
        return false;
    }
    return false;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }
}