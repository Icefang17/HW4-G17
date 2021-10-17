package game;

import map.State;
import map.Tile;
import resource.Mark;

import java.util.ArrayList;
import java.awt.Point;

public class Game {
    private State gameState;
    private ArrayList<Player> players;
    private Player currentPlayer;

    private Game(State initialState){
        this.gameState = initialState;
        this.players = new ArrayList<>();
        players.add(0, new Player(1, Mark.X));
        players.add(1, new Player(2, Mark.O));
        this.currentPlayer = players.get(0);
        this.gameState.player = currentPlayer;
    }

    // Game factory
    public static Game newXOGame(State initialState, int numPlayers){
        if(initialState.getTiles() != null && numPlayers == 2)
            return new Game(initialState);
        return null;
    }

    // Returns index (player ID is index + 1)
    private int getNextPlayer(){
        return currentPlayer.getPlayerId() % players.size();
    }

    public void endTurn(){
        this.currentPlayer = players.get(getNextPlayer());
        this.gameState.player = currentPlayer;
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

        for(int i = 0; i < state.getTiles().size(); i++) {
            for(int j = 0; j < state.getTiles().get(i).size(); j++) {
                if(state.getTiles().get(i).get(j).getValue() == Mark.BLANK) {
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
        for(int i = 0; i < state.getTiles().size(); i++) {
            for(int j = 0; j < state.getTiles().get(i).size() - 3; j++) {
                if(curMark == state.getTiles().get(i).get(j).getValue() &&
                        curMark == state.getTiles().get(i).get(j + 1).getValue() &&
                        curMark == state.getTiles().get(i).get(j + 2).getValue() &&
                        curMark == state.getTiles().get(i).get(j + 3).getValue()) {
                    return true;
                }           
            }
        }

        // Column Check
        for(int i = 0; i < state.getTiles().size() - 3; i++) {
            for(int j = 0; j < state.getTiles().get(i).size(); j++) {
                if(curMark == state.getTiles().get(i).get(j).getValue() &&
                        curMark == state.getTiles().get(i + 1).get(j).getValue() &&
                        curMark == state.getTiles().get(i + 2).get(j).getValue() &&
                        curMark == state.getTiles().get(i + 3).get(j).getValue()) {
                    return true;
                }           
            }
        }

        // Normal Diagonal Check
        for(int i = 0; i < state.getTiles().size() - 3; i++) {
            for(int j = 0; j < state.getTiles().get(i).size() - 3; j++) {
                if(curMark == state.getTiles().get(i).get(j).getValue() &&
                        curMark == state.getTiles().get(i + 1).get(j + 1).getValue() &&
                        curMark == state.getTiles().get(i + 2).get(j + 2).getValue() &&
                        curMark == state.getTiles().get(i + 3).get(j + 3).getValue()) {
                    return true;
                }  
            }
        }

        // Anti-Normal Diagonal Check
        for(int i = 0; i < state.getTiles().size() - 3; i++) {
            for(int j = 3; j < state.getTiles().get(i).size(); j++) {
                if(curMark == state.getTiles().get(i).get(j).getValue() &&
                        curMark == state.getTiles().get(i + 1).get(j - 1).getValue() &&
                        curMark == state.getTiles().get(i + 2).get(j - 2).getValue() &&
                        curMark == state.getTiles().get(i + 3).get(j - 3).getValue()) {
                    return true;
                }  
            }
        }

        return false;
    }
}
