import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class GUI extends JFrame {
    private Game HMGame;
    private JTextField fieldForGuesses;
    private JLabel labelForWords;
    private JLabel labelForIncorrectGuesses;
    private JLabel labelForGuessesRemaining;
    private JLabel labelForTimerCountdown;
    private JButton buttonForGuesses;
    private int timerCountdown;
    private Timer timerForGuesses;

    public GUI(String w)
    {
        HMGame = new Game(w);
        configureGUI();
    }

    private void configureGUI() {

        JPanel panelForTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelForWords = new JLabel(HMGame.getGuesses(), JLabel.CENTER);
        panelForTop.add(labelForWords);
        add(panelForTop, BorderLayout.NORTH);

        JPanel panelForCentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fieldForGuesses = new JTextField(2);
        buttonForGuesses = new JButton("Guess");
        buttonForGuesses.addActionListener(this::guessHandler);
        panelForCentre.add(fieldForGuesses);
        panelForCentre.add(buttonForGuesses);
        add(panelForCentre, BorderLayout.CENTER);

        JPanel panelForBottom = new JPanel(new GridLayout(3, 1, 10, 5));
        labelForIncorrectGuesses = new JLabel("Incorrect Guesses: " + HMGame.getWrongGuesses(), JLabel.CENTER);
        labelForGuessesRemaining = new JLabel("Guesses Remaining: " + HMGame.getGuessesRemaining(), JLabel.CENTER);
        labelForTimerCountdown = new JLabel("Time Remaining: 10s", JLabel.CENTER);
        panelForBottom.add(labelForIncorrectGuesses);
        panelForBottom.add(labelForGuessesRemaining);
        panelForBottom.add(labelForTimerCountdown);
        add(panelForBottom, BorderLayout.SOUTH);

        timerCountdown = 10;
        timerForGuesses = new Timer(1000, (e) ->
        {
            labelForTimerCountdown.setText("Time Remaining: " + timerCountdown + "s");
            timerCountdown--;

            if (timerCountdown <= 0)
            {
                termination();
            }
        });
        timerForGuesses.start();

        pack();
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void guessHandler(ActionEvent e)
    {
        timerCountdown = 10;
        timerForGuesses.stop();

        String input = fieldForGuesses.getText().trim().toLowerCase();
        if (input.length() == 1 && input.matches("[a-z]"))
        {
            char guess = input.charAt(0);
            if (!HMGame.guesses(guess))
            {
                JOptionPane.showMessageDialog(this, "This Letter Is Either Invalid or Has Already Been Guessed. Please Try Again.");
            }
            update();
            timerForGuesses.start();

            if (HMGame.endOfGame())
            {
                timerForGuesses.stop();
                if (HMGame.gameWinner())
                {
                    JOptionPane.showMessageDialog(this, "Winner!");
                }
                else {
                    JOptionPane.showMessageDialog(this, "You Lost! The word was " + HMGame.getChosenWord());
                }
                dispose();
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Letter");
            timerForGuesses.start();
        }
        fieldForGuesses.setText("");
    }

    private void termination()
    {
        timerForGuesses.stop();
        HMGame.decreaseGuessesRemaining();
        update();

        if (HMGame.endOfGame())
        {
            JOptionPane.showMessageDialog(this, "The Time Has Run Out, Game Over! The word was " + HMGame.getChosenWord());
            dispose();
        }
        else {
            timerCountdown = 10;
            labelForTimerCountdown.setText("Time Remaining: 10s");
            timerForGuesses.start();
        }
    }

    private void update() {
        labelForWords.setText(HMGame.getGuesses());
        labelForIncorrectGuesses.setText("Incorrect Guesses: " + HMGame.getWrongGuesses());
        labelForGuessesRemaining.setText("Guesses Remaining: " + HMGame.getGuessesRemaining());
    }
}
