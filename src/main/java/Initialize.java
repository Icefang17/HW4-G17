import game.Game;
import game.Player;
import map.State;
import search.MiniMax;

import java.awt.Point;

public class Initialize {

    private static final int xBound = 5;
    private static final int yBound = 6;

    public static void main(String argc[]){
        Player currentPlayer;
        State initialState = new State(xBound, yBound);
        Game game = Game.newXOGame(initialState, 2);
        int count = 1;

        // Begin the game. Note the start time.
        long startTime = System.nanoTime();

        // Place first X at the center of the board.
        System.out.println("\nTurn: " + count + " (Player " + game.getCurrentPlayer().getPlayerId() + ")");
        game.getGameState().setTileToPlayer(2, 3);
        count++;
        System.out.println("Move: " + game.getCurrentPlayer().getMark() + " -> (3, 4)");
        game.getGameState().printState();
        game.endTurn();

        while(true) {
            // Set the current player shall be X.
            currentPlayer = game.getCurrentPlayer();

            // Print the turn number.
            System.out.println("\nTurn: " + count + " (Player " + currentPlayer.getPlayerId() + ")");

            // Agent calculates and performs it's turn.
            Point action = MiniMax.minimaxDecision(game, game.getGameState(), currentPlayer.getPlayerId() * 2);
            game.getGameState().setTileToPlayer(action.x, action.y);
            count++;

            // Print the new state of the game.
            System.out.println("Move: " + currentPlayer.getMark() + " -> (" + (action.x + 1) + ", " + (action.y + 1)  + ")");
            game.getGameState().printState();

            // Check that the game has not ended.
            if(Game.terminalTest(game.getGameState()))
                break;

            // Switch players.
            game.endTurn();
        }

        // Print the time elapsed and victor.
        long secondsElapsed = (System.nanoTime() - startTime) / 1000000000;
        System.out.println("\nRuntime: " + (secondsElapsed / 60) + ":" + String.format("%02d", (secondsElapsed % 60)));
        System.out.println("Player " + game.getCurrentPlayer().getPlayerId() + " won the game!");
    }

}
