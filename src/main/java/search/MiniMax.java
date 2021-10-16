package search;

public class MiniMax {
    public Action minimaxDecision(State state) {
        Action finalAction;

        // This loop is not done. Computes the element 'action' of set actions(state) that has the maximum value of minValue(result(state, action)).
        for(Action action : actions(state)) {
            // Probably use a comparator for this. ***
            if(minValue(result(state, action)))
                break;

            else
                finalAction = action;
        }

        return finalAction;
    }

    public int minValue(State state) {
        if(terminalTest(state))
            return utility(state);

        int utilityValue = Integer.MAX_VALUE;

        for(Action action : actions(state))
            utilityValue = min(utilityValue, maxValue(result(state, action)));

        return utilityValue;
    }

    public int maxValue(State state) {
        if(terminalTest(state))
            return utility(state);

        int utilityValue = Integer.MIN_VALUE;

        for(Action action : actions(state))
            utilityValue = max(utilityValue, minValue(result(state, action)));

        return utilityValue;
    }
}
