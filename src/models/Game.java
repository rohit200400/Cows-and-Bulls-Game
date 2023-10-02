package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Player player;
    private final int secretSize;
    private final String secret;
    private final int maxAttempts;
    private final List<Guess> guessList;
    private final Guess currentGuess;
    private GameStatus status;
    private int guessNumber;
    public Game(Player player, int secretSize) {
        this.player = player;
        this.secretSize = secretSize;
        this.status = GameStatus.IN_PROGRESS;
        this.secret = generateSecret(secretSize);
        this.maxAttempts = 20;
        this.guessList = new ArrayList<>();
        this.currentGuess = new Guess();
        this.guessNumber = 0;
    }

    public String getSecret() {
        return secret;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getSecretSize() {
        return secretSize;
    }

    public Guess getCurrentGuess() {
        return currentGuess;
    }

    public int getGuessNumber() {
        return guessNumber;
    }

    public void setGuessNumber(int guessNumber) {
        this.guessNumber = guessNumber;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void checkWinner() {
        if (secret.length() != currentGuess.getGuess().length()) {
            throw new IllegalArgumentException("The lengths of the secret code and guess must be the same.");
        }

        int bulls = 0;
        int cows = 0;
        int[] frequency = new int[10]; // To keep track of digit occurrences

        // Calculate bulls
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == currentGuess.getGuess().charAt(i)) {
                bulls++;
            } else {
                int secretDigit = Character.getNumericValue(secret.charAt(i));
                int guessDigit = Character.getNumericValue(currentGuess.getGuess().charAt(i));

                if (frequency[secretDigit] < 0) {
                    cows++; // Increment cows if we've seen this digit in the guess before
                }
                if (frequency[guessDigit] > 0) {
                    cows++; // Increment cows if we've seen this digit in the secret code before
                }

                frequency[secretDigit]++;
                frequency[guessDigit]--;
            }
        }
        currentGuess.setBulls(bulls);
        currentGuess.setCows(cows);
        guessList.add(new Guess(currentGuess));
        if (currentGuess.getBulls() == secretSize) status = GameStatus.FINISHED;
    }

    private String generateSecret(int size) {
        if (size <= 1) {
            throw new IllegalArgumentException("Size must be greater than one.");
        }
        if (size > 6) {
            throw new IllegalArgumentException("Size cannot exceed 6.");
        }

        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }

        Collections.shuffle(digits);

        StringBuilder numberBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            numberBuilder.append(digits.get(i));
        }

        return numberBuilder.toString();
    }

    public void replay() {
        for (Guess g : guessList) {
            System.out.println(g.toString());
        }
    }

    public void takeNextGuess(Game game) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                // Get User Input
                System.out.println("What will be your next guess?");
                String user_input = sc.nextLine();

                // Validate Input
                int user_input_int = Integer.parseInt(user_input);
                if (user_input.length() != game.getSecretSize()) {
                    throw new IllegalArgumentException("Number is not of the same size of secret.Correct size is "
                            + game.getSecretSize());
                }

                // If input is valid, break out of the loop
                game.getCurrentGuess().setGuess(user_input);
                game.setGuessNumber(game.getGuessNumber() + 1);
                break;
            } catch (NumberFormatException e) {
                // Step 3: Handle Validation Errors (invalid integer input)
                System.out.println("Invalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                // Step 3: Handle Validation Errors
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }
}
