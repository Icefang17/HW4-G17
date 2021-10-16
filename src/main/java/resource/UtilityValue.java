package resource;

import map.State;

public class UtilityValue {
    public static int utility(State state) {
        int utilityValue = 0;

        // Calculate open sides for players and then:

        utilityValue =
                100 * [num of 2-open 3-streak for player]
                - 10 * [num of 2-open 3-streak for antiplayer]

                + 100 * [num of 1-open 3-streak for player]
                - 5 * [num of 1-open 3-streak for antiplayer]

                + 2 * [num 2-open 2-streak for player]
                - 2 * [num 2-open 2-streak for antiplayer]

                + [num 1-open 2-streak for player]
                - [num 1-open 2-streak for antiplayer];

        return utilityValue;
    }
}
