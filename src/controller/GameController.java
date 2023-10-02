package controller;

import models.Game;
import models.Player;

public class GameController {
    public Game createGame(Player player, int secretSize) {
        try {
            return new Game(player, secretSize);
        } catch (Exception e) {
            System.out.println("ERROR - " + e.getMessage());
            System.out.println("Could not start the game, something went wrong");
        }
        return null;
    }

    public void checkGuess(Game game) {
        game.checkWinner();
    }


    public void replay(Game game) {
        game.replay();
    }

    public void nextGuess(Game game) {
        game.takeNextGuess(game);
    }
}
