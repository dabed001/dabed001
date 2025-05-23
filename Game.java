import java.util.HashSet;
import java.util.Set;

public class Game {
    private StringBuilder guesses;
    private Set <Character> letters;
    private String chosenWord;
    private int guessesRemaining;
    private Set <Character> wrongGuesses;

    public Game(String w)
    {
        this.guesses = new StringBuilder("_".repeat(w.length()));
        this.letters = new HashSet<>();
        this.chosenWord = w;
        this.guessesRemaining = 7;
        this.wrongGuesses = new HashSet<>();
    }

    public boolean guesses (char l)
    {
        if (letters.contains(l))
        {
            return false;
        }

        letters.add(l);
        boolean discovered = false;

        for (int i = 0; i < chosenWord.length(); i++)
        {
            if (chosenWord.charAt(i) == l)
            {
                guesses.setCharAt(i, l);
                discovered = true;
            }
        }

        if (!discovered)
        {
            wrongGuesses.add(l);
            guessesRemaining--;
        }
        return discovered;
    }

    public boolean endOfGame()
    {
        return guessesRemaining <= 0 || guesses.toString().equals(chosenWord);
    }

    public boolean gameWinner()
    {
        return guesses.toString().equals(chosenWord);
    }

    public String getGuesses()
    {
        return guesses.toString();
    }

    public String getWrongGuesses()
    {
        return wrongGuesses.toString().replaceAll("[\\[\\]]", "").replace(", ", ", ");
    }

    public int getGuessesRemaining()
    {
        return guessesRemaining;
    }

    public String getChosenWord()
    {
        return chosenWord;
    }

    public void decreaseGuessesRemaining()
    {
        if (guessesRemaining > 0)
        {
            guessesRemaining--;
        }
    }
}
