package search;

import game.Game;
import map.State;
import resource.UtilityValue;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class MiniMax {
    static int count = 0;
    static int value = 0;
    static Point finalAction;
    static int utilityValue;
    static ArrayList<Point> equalMoves;

    public static Point minimaxDecision(Game game, State state, int depth) {
        equalMoves = new ArrayList<Point>();
        Game.actions(state).forEach((action) -> {
            int hold = value;
            value = Math.max(value, minValue(game, state, depth));

            if(value > hold) {
                finalAction = action;
                equalMoves.clear();
                equalMoves.add(finalAction);
            }

            else if(value - hold == 0)
                equalMoves.add(action);

            count++;
        });

        Random random = new Random();
        int randPoint = random.nextInt(equalMoves.size());

        Point randomAction = new Point(equalMoves.get(randPoint));
        finalAction = randomAction;

        System.out.println("Nodes Generated: " + count);
        return finalAction;
    }

    private static int minValue(Game game, State state, int depth) {
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MAX_VALUE;

        for(Point action : Game.actions(state)) {
            utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action), depth - 1));
            count++;
        }

        return utilityValue;
    }

    private static int maxValue(Game game, State state, int depth) {
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MIN_VALUE;

        for(Point action : Game.actions(state)) {
            utilityValue = Math.max(utilityValue, minValue(game, state.result(game, action), depth - 1));
            count++;
        }

        return utilityValue;
    }
}
