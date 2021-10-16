package map;

import game.Game;
import game.Player;
import resource.Mark;

import java.awt.*;

public class State {

    private State parentState;
    private Tile tiles[][];
    public Player player;


    public State(int xBound, int yBound){
        this.parentState = null;
        for(int x = 0; x < xBound; x++){
            for(int y = 0; y < yBound; y++){
                this.tiles[x][y] = new Tile(Mark.BLANK);
            }
        }
    }
    public State(Game game, State parentState, Point action){
        this.parentState = parentState;
        this.player = game.getCurrentPlayer();
        for(int i = 0; i < parentState.tiles.length; i++){
            for(int j = 0; j < parentState.tiles[0].length; j++){
                this.tiles[i][j] = new Tile(parentState.tiles[i][j].getValue());
            }
        }
    }

    public Player getPlayer(){return player;}

    public Tile[][] getTiles(){return tiles;}
}
