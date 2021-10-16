package game;

import map.State;

import java.util.ArrayList;

public class Game {

    private State initialState;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int numPlayers;

    public Game(State initialState, int numPlayers){
        this.numPlayers = numPlayers;
        this.initialState = initialState;
        this.players = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++)
            players.add(new Player(i + 1));
        this.currentPlayer = players.get(0);
    }

    public int getNextPlayer(){
        return (currentPlayer.getPlayerId() + 1) % players.size();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
}
