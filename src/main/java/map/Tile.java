package map;

import resource.Mark;

public class Tile {

    private Mark value;

    protected Tile(Mark value){
        this.value = value;
    }

    public void setValue(Mark value){this.value = value;}

    public Mark getValue() {return value;}
}
