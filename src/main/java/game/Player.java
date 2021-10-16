package game;

import resource.Mark;

public class Player {
    private int playerId;
    private Mark mark;

    public Player(int playerId, Mark mark){
        this.playerId = playerId;
        this.mark = mark;
    }

    public int getPlayerId(){return playerId;}

    public Mark getMark(){return mark;}
}
