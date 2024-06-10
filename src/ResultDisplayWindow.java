/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mitha
 */
import javax.swing.*;
import java.awt.*;

public class ResultDisplayWindow extends JFrame
{
    private JLabel messageLabel;
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ResultDisplayWindow("username", 10));
	}

    public ResultDisplayWindow(String username, int score)
    {
        createAndShowGUI(username, score);
    }

    private void createAndShowGUI(String username, int score)
    {
        setTitle("Result Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        messageLabel = new JLabel("Hello, " + username + "! Your score is: " + score, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        add(messageLabel);
        setVisible(true);
    }
}
