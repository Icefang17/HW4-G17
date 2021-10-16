package search;

import game.Game;
import map.State;
import resource.UtilityValue;

import java.lang.Math;
import java.awt.Point;

public class MiniMax {
    static int value = 0;
    static Point finalAction;
    static int utilityValue;

    public static Point minimaxDecision(Game game, State state) {
        //int value = 0;
        // Point finalAction;

        // This loop is maybe done. Computes the element 'action' of set actions(state) that has the maximum value of minValue(result(state, action)).
        // Grab the action from the available actions that has the greatest
        Game.actions(state).forEach((action) -> {
            int hold = value;
            value = Math.max(value, minValue(game, state.result(game, action)));

            if(value != hold)
                finalAction = action;
        });

        return finalAction;
    }

    private static int minValue(Game game, State state) {
        if(Game.terminalTest(state))
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MAX_VALUE;

        // for(Action action : Game.actions(state))
        //     utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action)));

        Game.actions(state).forEach((action) -> {
            utilityValue = Math.min(utilityValue, maxValue(game, state.result(game, action)));
        });

        return utilityValue;
    }

    private static int maxValue(Game game, State state) {
        if(Game.terminalTest(state))
            return UtilityValue.utility(state, game.getPlayers());

        utilityValue = Integer.MIN_VALUE;

        // for(Action action : Game.actions(state))
        //     utilityValue = Math.max(utilityValue, minValue(state.result(game, action)));

        Game.actions(state).forEach((action) -> {
            utilityValue = Math.max(utilityValue, minValue(game, state.result(game, action)));
        });

        return utilityValue;
    }
}
