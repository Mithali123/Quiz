import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class MCQWindow extends JFrame implements ActionListener
{
    private JRadioButton[] options;
    private JButton submitButton;
    private int score;
    private final String username;
    private int[] correctAnswers = {1,3,3,1,3};
    private ButtonGroup[] buttonGroups;

    public MCQWindow(String username)
    {
        this.username = username;
        createAndShowGUI();
    }
    private void createAndShowGUI()
    {
        setTitle("MCQ Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
         gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Adjust spacing here
        // Add a label on top of the quiz
        JLabel quizLabel = new JLabel("Let's start the quiz", SwingConstants.CENTER);
        quizLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(quizLabel, gbc);
        String[] questions =
        {
                "1.What is the purpose of the volatile keyword in Java?",
                "2.  What is the difference between HashMap and Hashtable in Java?",
                "3.Which environment variable is used to set the java path? ",
                "4. Which of these statements is incorrect about Thread?",
                "5. Which class provides system independent server side implementation? "
        };
        String[][] optionsArray =
        {
                {"A) It indicates that a variable cannot be modified", "B) It ensures that a variable is accessed from main memory and not from thread cache", "C) It indicates that a variable can be accessed only within the same package", "D) It indicates that a variable should not be accessed by multiple threads"},
                {"A) HashMap allows null keys and values, while Hashtable does not.", "B) Hashtable is synchronized, while HashMap is not.", "C) HashMap allows duplicate keys, while Hashtable does not.", "D) All of the above"},
                {"A) MAVEN_Path", "B) JavaPATH", "C) JAVA", "D) JAVA_HOME"},
                {"A)start() method is used to begin execution of the thread", "B)run() method is used to begin execution of a thread before start() method in special cases", "C)A thread can be formed by implementing Runnable interface only", "D) A thread can be formed by a class that extends Thread class"},
                {"A)Server", "B)ServerReader", "C)Socket", "D)ServerSocket"}
        };
        options = new JRadioButton[20];

        this.buttonGroups = new ButtonGroup[5];

        gbc.gridy++;
        for (int i = 0; i < 5; i++)
        {
            gbc.gridy++;
            JLabel questionLabel = new JLabel(questions[i]);
            questionLabel.setFont(new Font("Serif", Font.BOLD, 18));
            panel.add(questionLabel, gbc);

            this.buttonGroups[i] = new ButtonGroup();
            for (int j = 0; j < 4; j++)
            {
                gbc.gridy++;
                options[i * 4 + j] = new JRadioButton(optionsArray[i][j]);
                panel.add(options[i * 4 + j], gbc);
                this.buttonGroups[i].add(options[i * 4 + j]);

                // Add an ItemListener to each JRadioButton
                options[i * 4 + j].addItemListener(e -> {
                    if (checkAllQu		estionsAnswered())
                    {
                        submitButton.setEnabled(true);
                    } else
                    {
                        submitButton.setEnabled(false);
                    }
                });
            }
            gbc.gridy++;
        }
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setEnabled(false);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        add(scrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
            int[] selectedAnswers = getSelectedAnswers();
            score = calculateScore(selectedAnswers);
         //Store in database
            storeScoreInDatabase();
            //Display the result
            JOptionPane.showMessageDialog(this, "Your score is: " + score);
            dispose(); // Close the MCQ window
            new ResultDisplayWindow(username, score); //Open the result display window
        }
    }
    private int[] getSelectedAnswers()
    {
        int[] selectedAnswers = new int[5];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++) {
                if (options[i * 4 + j].isSelected())
                {
                    selectedAnswers[i] = j;
                    break;
                }
            }
        }
        return selectedAnswers;
    }
    private int calculateScore(int[] selectedAnswers) {
        int totalScore = 0;
        for (int i = 0; i < 5; i++) {
            if (selectedAnswers[i] == correctAnswers[i]) {
                totalScore++;
            }
        }
        return totalScore;
    }

    private void storeScoreInDatabase()
    {
        String url = "jdbc:mysql://localhost:/proj2";
        String user = "root";
        String passwordDB = "";
        try (Connection connection = DriverManager.getConnection(url, user, passwordDB))
        {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO marks(username, score) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setInt(2, score);

            statement.executeUpdate();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    private boolean checkAllQuestionsAnswered()
    {
        boolean allAnswered = true;
        for (ButtonGroup group : buttonGroups)
        {
            if (group.getSelection() == null)
            {
                allAnswered = false;
                break;
            }
        }
        return allAnswered;
    }
}
