package game;

import map.State;
import resource.Mark;

import java.util.ArrayList;
import java.awt.Point;

public class Game {
    private State gameState;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int turn;

    private Game(State initialState){
        this.gameState = initialState;
        this.turn = initialState.getTurn();
        this.players = new ArrayList<>();
        players.add(0, new Player(1, Mark.X));
        players.add(1, new Player(2, Mark.O));
        this.currentPlayer = players.get(0);
        this.gameState.setPlayer(currentPlayer);
    }

    // Game factory
    public static Game newXOGame(State initialState, int numPlayers){
        if(initialState.getTiles() != null && numPlayers == 2)
            return new Game(initialState);
        return null;
    }

    // Returns index (player ID is index + 1)
    public int getNextPlayer(Player player){
        return player.getPlayerId() % players.size();
    }

    public void endTurn(){
        this.currentPlayer = players.get(getNextPlayer(currentPlayer));
        this.gameState.setPlayer(currentPlayer);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public State getGameState(){
        return gameState;
    }

    public void setGameState(State state){
        this.gameState = state;
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
        Mark curMark = state.getPlayer().getMark();
        // Row Check
        for(int i = 0; i < state.getTiles().length; i++) {
            for(int j = 0; j < state.getTiles()[i].length - 3; j++) {
                if(curMark == state.getTiles()[i][j].getValue() &&
                        curMark == state.getTiles()[i][j + 1].getValue() &&
                        curMark == state.getTiles()[i][j + 2].getValue() &&
                        curMark == state.getTiles()[i][j + 3].getValue()) {
                    return true;
                }           
            }
        }

        // Column Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 0; j < state.getTiles()[i].length; j++) {
                if(curMark == state.getTiles()[i][j].getValue() &&
                        curMark == state.getTiles()[i + 1][j].getValue() &&
                        curMark == state.getTiles()[i + 2][j].getValue() &&
                        curMark == state.getTiles()[i + 3][j].getValue()) {
                    return true;
                }           
            }
        }

        // Normal Diagonal Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 0; j < state.getTiles()[i].length - 3; j++) {
                if(curMark == state.getTiles()[i][j].getValue() &&
                        curMark == state.getTiles()[i + 1][j + 1].getValue() &&
                        curMark == state.getTiles()[i + 2][j + 2].getValue() &&
                        curMark == state.getTiles()[i + 3][j + 3].getValue()) {
                    return true;
                }  
            }
        }

        // Anti-Normal Diagonal Check
        for(int i = 0; i < state.getTiles().length - 3; i++) {
            for(int j = 3; j < state.getTiles()[i].length; j++) {
                if(curMark == state.getTiles()[i][j].getValue() &&
                        curMark == state.getTiles()[i + 1][j - 1].getValue() &&
                        curMark == state.getTiles()[i + 2][j - 2].getValue() &&
                        curMark == state.getTiles()[i + 3][j - 3].getValue()) {
                    return true;
                }  
            }
        }

        return false;
    }
}
