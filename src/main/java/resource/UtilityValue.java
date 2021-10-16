package resource;

import game.Player;
import map.State;
import map.Tile;

import java.awt.*;
import java.util.ArrayList;

public class UtilityValue {

    public static int utility(State state, ArrayList<Player> players){
        if(state != null && players.size() == 2) {
            if(state.getPlayer().getPlayerId() == 1)
                return calculateUtilityValue(state, players.get(0), players.get(1));
            else
                return calculateUtilityValue(state, players.get(1), players.get(0));
        }
        return (-1);
    }
    private static int calculateUtilityValue(State state, Player player, Player opponent) {
        int utilityValue = 0;

        // Calculate open sides for players and then:

       /* utilityValue =
                100 * [num of 2-open 3-streak for player]
                - 10 * [num of 2-open 3-streak for opponent]

                + 100 * [num of 1-open 3-streak for player]
                - 5 * [num of 1-open 3-streak for opponent]

                + 2 * [num 2-open 2-streak for player]
                - 2 * [num 2-open 2-streak for opponent]

                + [num 1-open 2-streak for player]
                - [num 1-open 2-streak for opponent];*/

        // true = 2-open false = 1-open
        utilityValue =
                100 * (findStreaks(state, player, 3, true))
                - 10 * (findStreaks(state, opponent, 3, true))

                + 100 * (findStreaks(state, player, 3, false))
                - 5 * (findStreaks(state, opponent, 3, false))

                + 2 * (findStreaks(state, player, 2, true))
                - 2 * (findStreaks(state, opponent, 2, true))

                + (findStreaks(state, player, 2, false))
                - (findStreaks(state, opponent, 2, false));

        return utilityValue;
    }

    private static int findStreaks(State state, Player player, int streak, boolean open){
        Tile tiles[][] = state.getTiles();
        Point coordinates;
        ArrayList<Point> found = new ArrayList<>();

        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[0].length; y++){
                if(tiles[x][y].getValue() == Mark.BLANK){
                    for(int i = 0; i < 8; i++){
                        coordinates = checkAdjacentTiles(x, y, tiles.length, tiles[0].length, i);
                        int counter = 0;
                        int newX = coordinates.x;
                        int newY = coordinates.y;
                        do{
                            counter++;

                            // Break if at edge of board
                            if(newX == coordinates.x && newY == coordinates.y)
                                break;

                            // Repeat the directional operation
                            coordinates = checkAdjacentTiles(newX, newY, tiles.length, tiles[0].length, i);
                            newX = coordinates.x;
                            newY = coordinates.y;

                            // Add streak to found if looking for closed streak
                            if(counter == streak && tiles[newX][newY].getValue() == player.getMark()){
                                coordinates = checkAdjacentTiles(newX, newY, tiles.length, tiles[0].length, i);
                                Mark mark = tiles[coordinates.x][coordinates.y].getValue();
                                // If looking for an open streak
                                if(open){
                                    // Make sure the streak is open and hasn't already been found
                                    if(mark == Mark.BLANK && !found.contains(coordinates))
                                        found.add(new Point(x, y));
                                }
                                else{
                                    // Make sure the streak isn't bigger than the search value
                                    if(mark != player.getMark() && mark != Mark.BLANK)
                                        found.add(new Point(x, y));
                                    // Check if streak ended on an edge
                                    else if(coordinates.x == newX && coordinates.y == newY)
                                        found.add(new Point(x, y));
                                    break;
                                }
                            }
                        }while(counter < streak && tiles[newX][newY].getValue() == player.getMark());
                        for(int q = 0; q < streak && tiles[newX][newY].getValue() == player.getMark(); q++) {

                        }
                    }
                }
            }
        }
        return found.size();
    }

    private static Point checkAdjacentTiles(int x, int y, int xBound, int yBound, int instruction){
        switch(instruction){
            case(0):
                return Tile.goTopLeft(x, y, xBound, yBound);
            case(1):
                return Tile.goTop(x, y, xBound, yBound);
            case(2):
                return Tile.goTopRight(x, y, xBound, yBound);
            case(3):
                return Tile.goLeft(x, y, xBound, yBound);
            case(4):
                return Tile.goRight(x, y, xBound, yBound);
            case(5):
                return Tile.goBotLeft(x, y, xBound, yBound);
            case(6):
                return Tile.goBot(x, y, xBound, yBound);
            case(7):
                return Tile.goBotRight(x, y, xBound, yBound);
            default:
                System.out.println("Something went wrong in [utility]!\n");
        }
        return new Point(0, 0);
    }
}