package search;

import game.Game;
import map.State;
import resource.UtilityValue;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class MiniMax {
    static int value = 0;
    static Point finalAction;
    static int utilityValue;
    static ArrayList<Point> equalMoves;

    public static Point minimaxDecision(Game game, State state, int depth) {
        equalMoves = new ArrayList<Point>();
        Game.actions(state).forEach((action) -> {
            int hold = value;
            value = Math.max(value, minValue(game, state, depth));

            if(value != 0)
                System.out.println(value);

            if(value > hold) {
                finalAction = action;
                equalMoves.clear();
                equalMoves.add(finalAction);
            }

            else if(value - hold == 0) {
                equalMoves.add(action);
                /*Random random = new Random();
                int rand = random.nextInt(2);

                switch(rand) {
                    case 0:
                        finalAction = action;
                        break;
                    case 1:
                        break;
                }*/
            }
        });
        Random random = new Random();
        int randPoint = random.nextInt(equalMoves.size());
        Point randomAction = new Point(equalMoves.get(randPoint));
        finalAction = randomAction;

        /*if(game.getGameState().getTurn() == 0){
            Random random = new Random();
            int randX = random.nextInt(state.getTiles().length);
            int randY = random.nextInt(state.getTiles()[randX].length);

            Point randomAction = new Point(randX, randY);
            finalAction = randomAction;
        }*/
        return finalAction;
    }

    private static int minValue(Game game, State state, int depth) {
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MAX_VALUE;

        for(Point action : Game.actions(state)) {
            utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action), depth - 1));
        }

        // Game.actions(state).forEach((action) -> {
        //     utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action), depth - 1));
        // });
        return utilityValue;
    }

    private static int maxValue(Game game, State state, int depth) {
        if(Game.terminalTest(state) || depth == 0)
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MIN_VALUE;

        for(Point action : Game.actions(state)) {
            utilityValue = Math.max(utilityValue, minValue(game, state.result(game, action), depth - 1));
        }
        
        // Game.actions(state).forEach((action) -> {
        //     utilityValue = Math.max(utilityValue, minValue(game, state.result(game, action), depth - 1));
        // });
        return utilityValue;
    }
}
