import controller.GameController;
import models.Game;
import models.GameStatus;
import models.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameController gameController = new GameController();
        System.out.println("What is the name of player:");
        String playerName = sc.next();
        int dimension;
        int retryCount = 0;
        while (true) {
            try {
                // Get User Input
                System.out.println("What will be the size of secrete code:");
                int user_input = Integer.parseInt(sc.nextLine());

                // Validate Input
                if (user_input < 2 || user_input > 6) {
                    throw new IllegalArgumentException("Number is out of range. correct range is 2 - 6.");
                }

                // If input is valid, break out of the loop
                retryCount = 0;
                dimension = user_input;
                break;
            } catch (NumberFormatException e) {
                // Step 3: Handle Validation Errors (invalid integer input)
                retryCount++;
                System.out.println("Invalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                // Step 3: Handle Validation Errors
                retryCount++;
                System.out.println("Invalid input: " + e.getMessage());
            }
            if (retryCount > 5) {
                System.out.println("We are initiating with default dimension - 4 as you have given wrong input multiple times.");
                dimension = 4;
                break;
            }
        }
        Player player = new Player(playerName);
        Game game = gameController.createGame(player, dimension);

        while (game.getStatus() == GameStatus.IN_PROGRESS) {
            System.out.println("Total number of guess made : " + game.getGuessNumber());

            gameController.nextGuess(game);

            gameController.checkGuess(game);

            System.out.println(game.getCurrentGuess().toString());

            if (game.getStatus() == GameStatus.FINISHED) {
                System.out.println("YAY!!!... you have guessed it correct. You took " + game.getGuessNumber() +
                        " guesses to find the secret.");
                System.out.println("Do you want a replay of the game?");
                String user_input = sc.nextLine();
                if (user_input.equalsIgnoreCase("Y")) gameController.replay(game);
                break;
            }

            if (game.getGuessNumber() > game.getMaxAttempts()) {
                System.out.println("You have already tried " + game.getGuessNumber() + " times. " +
                        "\n Do you want to give up? Y/N");
                String user_input = sc.nextLine();
                if (user_input.equalsIgnoreCase("Y")) {
                    System.out.println("The secret was " + game.getSecret() + ". better luck next time......");
                    break;
                }
            }
        }

    }
}