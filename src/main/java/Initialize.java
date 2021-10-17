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
        while(!Game.terminalTest(game.getGameState())){
            currentPlayer = game.getCurrentPlayer();
            game.getGameState().printState();
            Point action = MiniMax.minimaxDecision(game, game.getGameState(), currentPlayer.getPlayerId() * 2);
            game.getGameState().setTileToPlayer(action.x, action.y);
            game.endTurn();
        }
        System.out.println("SOMEBODY WON?");
    }

}
