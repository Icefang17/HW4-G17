package resource;

import game.Player;
import map.State;

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

        Player currentPlayer =
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
