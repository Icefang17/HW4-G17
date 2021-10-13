package game;

import map.State;

import java.util.ArrayList;

public class Game {

    public ArrayList<Player> players;

    public Game(State initialState, ){
    }

    public int getNextPlayer(State state){
        return (state.player.getPlayerId() % players.size()) + 1;
    }

    public Player getPlayer(State state){
        return state.getPlayer();
    }
}
