package game;

import map.State;
import resource.Mark;

import java.util.ArrayList;
import java.awt.Point;

public class Game {
    private State gameState;
    private ArrayList<Player> players;
    private Player currentPlayer;

    /**
     * Initial state constructor.
     * @param initialState the initial state of the game
     */ 
    private Game(State initialState) {
        this.gameState = initialState;
        this.players = new ArrayList<>();
        players.add(0, new Player(1, Mark.X));
        players.add(1, new Player(2, Mark.O));
        this.currentPlayer = players.get(0);
        this.gameState.setPlayer(currentPlayer);
    }


    /**
     * Game factory.
     * @param initialState the initial state of the game
     * @param numPlayers the number of players in the game
     * @return a new game on success, null on failure
     */
    public static Game newXOGame(State initialState, int numPlayers) {
        if(initialState.getTiles() != null && numPlayers == 2)
            return new Game(initialState);
        return null;
    }

    /**
     * Returns the index of the player ID (1 indexed)
     * @param player the player to get the index of
     * @return the index of the player ID
     */
    public int getNextPlayer(Player player) {
        return player.getPlayerId() % players.size();
    }

    /**
     * End the current players turn.
     */
    public void endTurn() {
        this.currentPlayer = players.get(getNextPlayer(currentPlayer));
        this.gameState.setPlayer(currentPlayer);
    }

    /**
     * Get the arraylist of players.
     * @return an arraylist of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the current player.
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the current game state.
     * @return the current game state
     */
    public State getGameState() {
        return gameState;
    }

    /**
     * Set the current game state.
     * @param state the state to set the game state to
     */
    public void setGameState(State state) {
        this.gameState = state;
    }

    /**
     * Get the actions available in a state.
     * @param state the state to inspect
     * @return an arraylist of available points to place on.
     */
    public static ArrayList<Point> actions(State state) {
        ArrayList<Point> actionList = new ArrayList<>();

        // For each tile in the game.
        for(int i = 0; i < state.getTiles().length; i++) {
            for(int j = 0; j < state.getTiles()[i].length; j++) {
                // If the tile is blank, it is an available action.
                if(state.getTiles()[i][j].getValue() == Mark.BLANK) {
                    Point action = new Point(i, j);
                    actionList.add(action);
                }
            }
        }

        return actionList;
    }

    /**
     * Test if the game has ended.
     * @param state the state to test
     * @return true on a player victory, false on no victory.
     */
    public static boolean terminalTest(State state) {
        Mark curMark = state.getPlayer().getMark();
        // Check rows for 4 consecutive marks.
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

        // Check columns for 4 consecturive marks.
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

        // Check normal diagonal for 4 consecutive marks.
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

        // Check anti-normal diagonal for 4 consecutive marks.
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
