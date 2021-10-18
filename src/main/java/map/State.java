package map;

import game.Game;
import game.Player;
import resource.Mark;

import java.awt.Point;

public class State {
    private Tile tiles[][];
    private Player player;
    private int turn;

    // Initial State Constructor
    public State(int xBound, int yBound){
        this.player = null;
        this.tiles = new Tile[xBound][yBound];
        this.turn = 0;

        for(int x = 0; x < xBound; x++)
            for(int y = 0; y < yBound; y++)
                this.tiles[x][y] = new Tile(Mark.BLANK);
    }

    // Successor State Constructor
    public State(Game game, State parentState, Point action){
        int xBound = parentState.getTiles().length;
        int yBound = parentState.getTiles()[0].length;

        this.turn = parentState.getTurn() + 1;
        this.tiles = new Tile[xBound][yBound];

        for(int i = 0; i < xBound; i++)
            for(int j = 0; j < yBound; j++)
                this.tiles[i][j] = new Tile(parentState.getTiles()[i][j].getValue());

        this.player = game.getPlayers().get(game.getNextPlayer(parentState.getPlayer()));
        this.tiles[action.x][action.y].setValue(player.getMark());
    }

    public Player getPlayer(){return player;}

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Tile[][] getTiles(){return tiles;}

    public void setTileToBlank(int x, int y) {
        tiles[x][y].setValue(Mark.BLANK);
    }

    public void setTileToPlayer(int x, int y) {
        tiles[x][y].setValue(player.getMark());
    }

    public State result(Game game, Point action) {
        return new State(game, this, action);
    }

    public int getTurn(){
        return turn;
    }
    public void printState() {
        System.out.println("---------------------");
        for(int y = this.getTiles()[0].length - 1;  y >= 0; y--) {
            for(int x = 0; x < this.getTiles().length; x++) {
                if(this.getTiles()[x][y].getValue() == Mark.BLANK)
                    System.out.printf("|   ", this.getTiles()[x][y].getValue());
                else
                    System.out.printf("| %s ", this.getTiles()[x][y].getValue());
            }

            System.out.println("|\n---------------------");
        }
    }
}
