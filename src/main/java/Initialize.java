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
        int count = 0;
        long startTime = System.nanoTime();
        while(!Game.terminalTest(game.getGameState())) { 
            currentPlayer = game.getCurrentPlayer();
            Point action = MiniMax.minimaxDecision(game, game.getGameState(), currentPlayer.getPlayerId() * 2);

            if(count == 0)
                System.out.println("First Move: X -> (" + action.x + ", " + action.y + ")");
            count++;
            game.getGameState().setTileToPlayer(action.x, action.y);
            System.out.println("\nTurn: " + count + "( Player " + currentPlayer.getPlayerId() + ")");
            game.getGameState().printState();
            UtilityValue.utility(game.getGameState(), game.getPlayers());
            game.endTurn();
        }
        long endTime = System.nanoTime();
        System.out.println("Game ended with time: " + (((endTime - startTime) / 1000000000) / 60) + ":" + (((endTime - startTime) / 1000000000) % 60));
        System.out.println("Player " + game.getCurrentPlayer().getPlayerId() + " won the game!");
        System.out.println("SOMEBODY WON?");
    }

}
