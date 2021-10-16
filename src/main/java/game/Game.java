package game;

import map.State;
import map.Tile;

import java.util.ArrayList;

public class Game {

    private State initialState;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int numPlayers;

    private Game(State initialState, int numPlayers){
        this.numPlayers = numPlayers;
        this.initialState = initialState;
        this.players = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++)
            players.add(new Player(i + 1));
        this.currentPlayer = players.get(0);
        initialState.player = currentPlayer;
    }

    // Game factory
    public static Game newXOGame(State initialState, int numPlayers){
        if (initialState.getTiles() != null && numPlayers == 2)
            return new Game(initialState, numPlayers);
        return null;
    }

    private int getNextPlayer(){
        return currentPlayer.getPlayerId() % numPlayers;
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

    public ArrayList<Action> actions(State state) {
        ArrayList<Action> actions = new ArrayList<>();

        // Each action available in the state (ie: Fill - 2,3)
        for(all possible actions) {
            if(action is available)
                actions.add(action);
        }

        return actions;
    }

    public State result(State state, Action action) {
        State newState = new State(state);

        newState.setTile(action.x, action.y, player); // Player = 'x' or 'o'

        return newState;
    }

    public boolean terminalTest(State state) {
        // Row Check
        for(int i = 0; i < state.getTiles().length; i++) {
            for(int j = 0; j < state.getTiles()[i].length - 3; j++) {
                Tile curTile = state.getTiles()[i][j];

                if(curTile == state.getTiles()[i][j + 1] &&
                   curTile == state.getTiles()[i][j + 2] &&
                   curTile == state.getTiles()[i][j + 3]) {
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
