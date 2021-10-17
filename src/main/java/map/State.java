package map;

import game.Game;
import game.Player;
import resource.Mark;

import java.awt.Point;
import java.util.ArrayList;

public class State {
    //private Tile tiles[][];
    public Player player;
    private ArrayList<ArrayList<Tile>> tiles;

    // Initial State Constructor
    public State(int xBound, int yBound){
        this.player = null;
        this.tiles = new ArrayList<>();
        for(int x = 0; x < xBound; x++){
            this.tiles.add(new ArrayList<Tile>());
            for(int y = 0; y < yBound; y++){
                this.tiles.get(x).add(new Tile(Mark.BLANK));
            }
        }

       /* for(int x = 0; x < xBound; x++){
            for(int y = 0; y < yBound; y++){
                tiles[x][y] = new Tile(Mark.BLANK);
            }
        }*/
    }
    // Successor State Constructor
    public State(Game game, State parentState, Point action){
        // this.parentState = parentState;
        this.tiles = new ArrayList<>();
        this.player = game.getPlayers().get(game.getNextPlayer(parentState.player));
        for(int i = 0; i < parentState.tiles.size(); i++){
            this.tiles.add(new ArrayList<Tile>());
            for(int j = 0; j < parentState.tiles.get(i).size(); j++){
                this.tiles.get(i).add(j, new Tile(parentState.tiles.get(i).get(j).getValue()));
            }
        }

        // This needs actions. Set new tile from player mark.
        this.tiles.get(action.x).get(action.y).setValue(player.getMark());
    }

    public Player getPlayer(){return player;}

    public ArrayList<ArrayList<Tile>> getTiles(){return tiles;}

    public void setTileToBlank(int x, int y) {
        tiles.get(x).get(y).setValue(Mark.BLANK);
    }

    public void setTileToPlayer(int x, int y) {
        tiles.get(x).get(y).setValue(player.getMark());
    }

    public State result(Game game, Point action) {
        State newState = new State(game, this, action);

        return newState;
    }

    public void printState() {
        System.out.println("-------------------------");
        for(int i = 0; i < this.getTiles().size(); i++) {
            for(int j = 0; j < this.getTiles().get(i).size(); j++) {
                if(this.getTiles().get(i).get(j).getValue() == Mark.BLANK)
                    System.out.printf("|   ", this.getTiles().get(i).get(j).getValue());
                else
                    System.out.printf("| %s ", this.getTiles().get(i).get(j).getValue());
            }

            System.out.println("|\n-------------------------");
        }
    }
}
