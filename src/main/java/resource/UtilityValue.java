package resource;

import game.Player;
import map.State;
import map.Tile;

import java.awt.Point;
import java.util.ArrayList;

public class UtilityValue {

    public static int utility(State state, ArrayList<Player> players) {
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

        /**
         * Heuristics defined as:
         * 100 * [num of 2-open 3-streak for player]
         * - 10 * [num of 2-open 3-streak for opponent]
         *
         * + 100 * [num of 1-open 3-streak for player]
         * - 5 * [num of 1-open 3-streak for opponent]
         *
         * + 2 * [num 2-open 2-streak for player]
         * - 2 * [num 2-open 2-streak for opponent]
         *
         * + 1 * [num 1-open 2-streak for player]
         * - 1 * [num 1-open 2-streak for opponent]
        **/

        // Calculate streaks.
        ArrayList<Integer> streaks = findStreaks(state, player, opponent);

        // Calculate utility value relative to found streaks.
        utilityValue =
                100 * (streaks.get(0))
                - 10 * (streaks.get(1))

                + 100 * (streaks.get(2))
                - 5 * (streaks.get(3))

                + 2 * (streaks.get(4))
                - 2 * (streaks.get(5))

                + (streaks.get(6))
                - (streaks.get(7));

        return utilityValue;
    }

    private static Point getDirectionVector(int x, int y, int xBound, int yBound, int instruction){
        // Instruction table for 8 directions from a single tile.
        switch(instruction) {
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
                break;
        }
        return new Point(0, 0);
    }

    // list[0]: 2-open 3 streaks - player
    // list[1]: 2-open 3 streaks - opponent
    // list[2]: 1-open 3 streaks - player
    // list[3]: 1-open 3 streaks - opponent
    // list[4]: 2-open 2 streaks - player
    // list[5]: 2-open 2 streaks - opponent
    // list[6]: 1-open 2 streaks - player
    // list[7]: 1-open 2 streaks - opponent
    private static ArrayList<Integer> findStreaks(State state, Player curPlayer, Player curOpponent){
        Tile[][] tiles = state.getTiles();
        ArrayList<Integer> streaks = new ArrayList<>();

        Mark playerMark = curPlayer.getMark();
        Mark opMark = curOpponent.getMark();
        int xBound = state.getTiles().length - 1;
        int yBound = state.getTiles()[0].length - 1;
        boolean player = false;
        Point translation = new Point();

        // Initialize all streaks to 0.
        for(int i = 0; i < 8; i++){
            streaks.add(0);
        }

        // Iterate through all possible points.
        for(int x = 0; x < xBound; x++){
            for(int y = 0; y < yBound; y++){
                // If the point is blank.
                if(tiles[x][y].getValue() == Mark.BLANK){
                    Point location = new Point(x, y);
                    // location.setLocation(x, y);

                    // Check all adjacent tiles.
                    for(int i = 0; i < 8; i++){
                        // Set the translation to the vector to the adjacent tile.
                        translation = getDirectionVector(location.x, location.y, xBound, yBound, i);

                        // Edge of board edge-case.
                        if(translation != null) {
                            int counter = 0;
                            boolean edge = false;
                            location.translate(translation.x, translation.y);

                            // Loop while finding player marks.
                            if(tiles[location.x][location.y].getValue() == playerMark) {
                                // Looking at current player.
                                player = true;

                                // While there are equal player marks remaining in the direction of the vector
                                while(tiles[location.x][location.y].getValue() == playerMark) {
                                    // Increment streak.
                                    counter++;

                                    // If the edge of the board has been reached.
                                    if(getDirectionVector(location.x, location.y, xBound, yBound, i) == null) {
                                        edge = true;
                                        break;
                                    }

                                    // Simulate a translation in the direction of the translation vector.
                                    location.translate(translation.x, translation.y);
                                }
                            }
                            // Loop while finding opponent marks.
                            else if(tiles[location.x][location.y].getValue() != Mark.BLANK){
                                // Looking at opposing player.
                                player = false;

                                // While there are equal player marks remaining in the direction of the vector
                                while(tiles[location.x][location.y].getValue() == opMark) {
                                    // Increment streak.
                                    counter++;

                                    // If the edge of the board has been reached.                                   
                                    if(getDirectionVector(location.x, location.y, xBound, yBound, i) == null) {
                                        edge = true;
                                        break;
                                    }

                                    // Simulate a translation in the direction of the translation vector.
                                    location.translate(translation.x, translation.y);
                                }
                            }

                            // If the streak is greater than 1.
                            if(counter > 1) {
                                // Count 2-open occurences.
                                if(tiles[location.x][location.y].getValue() == Mark.BLANK) {
                                    // For the current player.
                                    if(!player){
                                        // Count 2-streak occurences.
                                        if(counter == 2)
                                            streaks.set(4, streaks.get(4) + 1);
                                        // Count 3-streak occurences.
                                        else
                                            streaks.set(0, streaks.get(0) + 1);
                                    }

                                    // For the opposing player.
                                    else{
                                        // Count 2-streak occurences.
                                        if(counter == 2)
                                            streaks.set(5, streaks.get(5) + 1);
                                        // Count 3-streak occurences.
                                        else
                                            streaks.set(1, streaks.get(1) + 1);
                                    }
                                }

                                // Count 1-open occurences.
                                else if(tiles[location.x][location.y].getValue() == opMark || edge){
                                    // For the current player.
                                    if(!player){
                                        // Count 2-streak occurences.
                                        if(counter == 2)
                                            streaks.set(6, streaks.get(6) + 1);
                                        // Count 3-streak occurences.
                                        else
                                            streaks.set(2, streaks.get(2) + 1);
                                    }

                                    // For the opposing player.
                                    else{
                                        // Count 2-streak occurences.
                                        if(counter == 2)
                                            streaks.set(7, streaks.get(7) + 1);
                                        // Count 3-streak occurences.
                                        else
                                            streaks.set(3, streaks.get(3) + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return streaks;
    }
}