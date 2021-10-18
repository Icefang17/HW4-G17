import game.Game;
import game.Player;
import map.State;
import resource.UtilityValue;
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
        long startTime = System.nanoTime();
        while(true) {



            currentPlayer = game.getCurrentPlayer();
            System.out.println("\nTurn: " + count + " (Player " + currentPlayer.getPlayerId() + ")");
            Point action = MiniMax.minimaxDecision(game, game.getGameState(), currentPlayer.getPlayerId() * 2);
            count++;
            game.getGameState().setTileToPlayer(action.x, action.y);
            System.out.println("Move: " + currentPlayer.getMark() + " -> (" + (action.x + 1) + ", " + (action.y + 1)  + ")");
            game.getGameState().printState();
            if(Game.terminalTest(game.getGameState()))
                break;
            game.endTurn();
        }
        long totalSeconds = (System.nanoTime() - startTime) / 1000000000;
        System.out.println("\nRuntime: " + (totalSeconds / 60) + ":" + String.format("%02d", (totalSeconds % 60)));
        System.out.println("Player " + game.getCurrentPlayer().getPlayerId() + " won the game!");
    }

}
