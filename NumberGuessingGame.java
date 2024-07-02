import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGame extends JFrame implements ActionListener {
    private int numberToGuess;
    private int numberOfTries;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel messageLabel;
    private JButton resetButton;
    private JTextField nameField;
    private JLabel nameLabel;
    private JButton startButton;
    private JPanel gamePanel;
    private JPanel startPanel;
    private String playerName;

    public NumberGuessingGame() {
        numberToGuess = (int) (Math.random() * 100 + 1);
        numberOfTries = 0;

        setTitle("Number Guessing Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Start Panel setup
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3, 2));
        startPanel.setBackground(new Color(230, 230, 250));

        nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(75, 0, 130));
        startPanel.add(nameLabel);

        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        startPanel.add(nameField);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(72, 209, 204));
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        startPanel.add(startButton);

        // Game Panel setup
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(5, 1));
        gamePanel.setBackground(new Color(255, 239, 213));

        JLabel titleLabel = new JLabel("Guess the number between 1 and 100", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 128, 128));
        gamePanel.add(titleLabel);

        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        gamePanel.add(guessField);

        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.setBackground(new Color(60, 179, 113));
        guessButton.setForeground(Color.WHITE);
        guessButton.addActionListener(this);
        gamePanel.add(guessButton);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(new Color(165, 42, 42));
        gamePanel.add(messageLabel);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(255, 69, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        resetButton.setVisible(false);
        gamePanel.add(resetButton);

        add(startPanel);
        add(gamePanel);
        gamePanel.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            playerName = nameField.getText();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name to start the game.");
            } else {
                startPanel.setVisible(false);
                gamePanel.setVisible(true);
                messageLabel.setText("Guess the number between 1 and 100");
                guessField.setText("");
                guessField.requestFocus();
                numberToGuess = (int) (Math.random() * 100 + 1);
                numberOfTries = 0;
            }
        } else if (e.getSource() == guessButton) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                numberOfTries++;
                if (guess < 1 || guess > 100) {
                    messageLabel.setText("Invalid input! Please enter a number between 1 and 100.");
                } else if (guess < numberToGuess) {
                    messageLabel.setText("Too low! Try again.");
                    guessField.setText("");
                } else if (guess > numberToGuess) {
                    messageLabel.setText("Too high! Try again.");
                    guessField.setText("");
                } else {
                    messageLabel.setText("Correct! It took you " + numberOfTries + " tries.");
                    guessButton.setEnabled(false);
                    resetButton.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid input! Please enter a valid number.");
            }
        } else if (e.getSource() == resetButton) {
            startPanel.setVisible(true);
            gamePanel.setVisible(false);
            guessButton.setEnabled(true);
            resetButton.setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
