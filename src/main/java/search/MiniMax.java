package search;

import game.Game;
import map.State;
import resource.UtilityValue;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class MiniMax {
    // Count the number of nodes generated.
    static int count = 0;

    public static Point minimaxDecision(Game game, State state, int depth) {
        count = 0;
        int value = Integer.MIN_VALUE;
        Point finalAction;

        // Evaluate each possible placement on the board.
        ArrayList<Point> equalMoves = new ArrayList<Point>();
        for(Point action : Game.actions(state)) {
            // Get the maximum minValue.
            int hold = value;
            value = Math.max(value, minValue(game, state, depth));

            // If value has increased, set it as the new final action.
            if(value > hold) {
                finalAction = action;
                equalMoves.clear();
                equalMoves.add(finalAction);
            }

            // If value has remained constant, add the action to a list of possible random actions.
            else if(value - hold == 0)
                equalMoves.add(action);

            // Add to nodes generated.
            count++;
        }

        // Pick a random point from the available best points.
        Random random = new Random();
        int randPoint = random.nextInt(equalMoves.size());

        // Set the action to place at the chosen random point.
        Point randomAction = new Point(equalMoves.get(randPoint));
        finalAction = randomAction;

        System.out.println("Nodes Generated: " + count);
        return finalAction;
    }

    private static int minValue(Game game, State state, int depth) {
        // Check if an end state or the max depth has been reached.
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        // Acts as infinity.
        int utilityValue = Integer.MAX_VALUE;

        // For all possible actions get the minimum between the maxValue and the current value.
        for(Point action : Game.actions(state)) {
            utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action), depth - 1));
            count++;
        }

        return utilityValue;
    }

    private static int maxValue(Game game, State state, int depth) {
        // Check if an end state or the max depth has been reached.
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        // Acts as -infinity.
        int utilityValue = Integer.MIN_VALUE;

        // For all possible actions get the minimum between the maxValue and the current value.
        for(Point action : Game.actions(state)) {
            utilityValue = Math.max(utilityValue, minValue(game, state.result(game, action), depth - 1));
            count++;
        }

        return utilityValue;
    }
}
