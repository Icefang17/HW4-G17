package search;

import map.State;
import resource.UtilityValue;

import java.lang.Math;
import game.Game;
import map.State;
import resource.UtilityValue;

public class MiniMax {
    public static Action minimaxDecision(Game game, State state) {
        int value = 0, hold;
        Action finalAction;

        // This loop is not done. Computes the element 'action' of set actions(state) that has the maximum value of minValue(result(state, action)).
        // Grab the action from the available actions that has the greatest
        for(Action action : Game.actions(state)) {
            hold = value;
            value = max(value, minValue(result(game, state, action)));

            if(value != hold)
                finalAction = action;
        }

        return finalAction;
    }

    public static int minValue(Game game, State state) {
        if(Game.terminalTest(state))
            return UtilityValue.utility(state, game.getPlayers());

        int utilityValue = Integer.MAX_VALUE;

        for(Action action : Game.actions(state))
            utilityValue = Math.min(utilityValue, maxValue(result(state, action)));

        // actions(state).forEach(
        //     (action) -> utilityValue = Math.min(utilityValue, maxValue(result(state, action)))
        // );

        return utilityValue;
    }

    public static int maxValue(Game game, State state) {
        if(Game.terminalTest(state))
            return UtilityValue.utility(state, game.getPlayers());

        int utilityValue = Integer.MIN_VALUE;

        for(Action action : Game.actions(state))
            utilityValue = Math.max(utilityValue, minValue(result(state, action)));

        // actions(state).forEach(
        //     (action) -> utilityValue = Math.max(utilityValue, minValue(result(state, action)))
        // );

        return utilityValue;
    }
}
