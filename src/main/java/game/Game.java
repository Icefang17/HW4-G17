package game;

import map.State;
import map.Tile;
import resource.Mark;

import java.util.ArrayList;
import java.awt.Point;

public class Game {
    private State initialState;
    private ArrayList<Player> players;
    private Player currentPlayer;

    private Game(State initialState){
        this.initialState = initialState;
        this.players = new ArrayList<>();
        players.add(0, new Player(1, Mark.X));
        players.add(1, new Player(2, Mark.O));
        this.currentPlayer = players.get(0);
        initialState.player = currentPlayer;
    }

    // Game factory
    public static Game newXOGame(State initialState, int numPlayers){
        if(initialState.getTiles() != null && numPlayers == 2)
            return new Game(initialState);

        return null;
    }

    private int getNextPlayer(){
        return currentPlayer.getPlayerId() % players.size();
    }

    public void endTurn(){
        this.currentPlayer = players.get(getNextPlayer());
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public static ArrayList<Point> actions(State state) {
        ArrayList<Point> actionList = new ArrayList<>();

        for(int i = 0; i < state.getTiles().length; i++) {
            for(int j = 0; j < state.getTiles()[i].length; j++) {
                if(state.getTiles()[i][j].getValue() == Mark.BLANK) {
                    Point action = new Point(i, j);
                    actionList.add(action);
                }
            }
        }

        return actionList;
    }

    public static boolean terminalTest(State state) {
        // Row Check
        for(int i = 0; i < state.getTiles().length; i++) {
            for(int j = 0; j < state.getTiles()[i].length - 3; j++) {
                Mark curMark = state.getTiles()[i][j].getValue();

                if(curMark == state.getTiles()[i][j + 1].getValue() &&
                   curMark == state.getTiles()[i][j + 2].getValue() &&
                   curMark == state.getTiles()[i][j + 3].getValue()) {
                    return true;
                }           
            }
        }

        // Column Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 0; j < state.getTiles()[i].length; j++) {
                Tile curTile = state.getTiles()[i][j];

                if(curTile == state.getTiles()[i + 1][j] &&
                   curTile == state.getTiles()[i + 2][j] &&
                   curTile == state.getTiles()[i + 3][j]) {
                    return true;
                }           
            }
        }

        // Normal Diagonal Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 0; j < state.getTiles()[i].length - 3; j++) {
                Tile curTile = state.getTiles()[i][j];

                if(curTile == state.getTiles()[i + 1][j + 1] &&
                   curTile == state.getTiles()[i + 2][j + 2] &&
                   curTile == state.getTiles()[i + 3][j + 3]) {
                    return true;
                }  
            }
        }

        // Anti-Normal Diagonal Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 3; j < state.getTiles()[i].length; j++) {
                Tile curTile = state.getTiles()[i][j];

                if(curTile == state.getTiles()[i + 1][j - 1] &&
                   curTile == state.getTiles()[i + 1][j - 2] &&
                   curTile == state.getTiles()[i + 1][j - 3]) {
                    return true;
                }  
            }
        }

        return false;
    }
}
