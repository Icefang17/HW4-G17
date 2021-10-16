package map;

import resource.Mark;

import java.awt.*;

public class Tile {

    private Mark value;

    protected Tile(Mark value){
        this.value = value;
    }

    public void setValue(Mark value){this.value = value;}

    public Mark getValue() {return value;}

    public static Point goTopLeft(int x, int y, int xBound, int yBound){
        if(checkBounds(x--, y++, xBound, yBound))
            return new Point(x--, y++);
        return new Point(0, 0);
    }

    public static Point goTop(int x, int y, int xBound, int yBound){
        if(checkBounds(x, y++, xBound, yBound))
            return new Point(x, y++);
        return new Point(0, 0);
    }

    public static Point goTopRight(int x, int y, int xBound, int yBound){
        if(checkBounds(x++, y++, xBound, yBound))
            return new Point(x++, y++);
        return new Point(0, 0);
    }

    public static Point goLeft(int x, int y, int xBound, int yBound){
        if(checkBounds(x--, y, xBound, yBound))
            return new Point(x--, y);
        return new Point(0, 0);
    }

    public static Point goRight(int x, int y, int xBound, int yBound){
        if(checkBounds(x++, y, xBound, yBound))
            return new Point(x++, y);
        return new Point(0, 0);
    }

    public static Point goBotLeft(int x, int y, int xBound, int yBound){
        if(checkBounds(--x, y--, xBound, yBound))
            return new Point(x--, y--);
        return new Point(0, 0);
    }

    public static Point goBot(int x, int y, int xBound, int yBound){
        if(checkBounds(x, y--, xBound, yBound))
            return new Point(x, y--);
        return new Point(0, 0);
    }

    public static Point goBotRight(int x, int y, int xBound, int yBound){
        if(checkBounds(x++, y--, xBound, yBound))
            return new Point(x++, y--);
        return new Point(0, 0);
    }

    private static boolean checkBounds(int x, int y, int xBound, int yBound){
        if(x < 0 || x > xBound)
            return false;
        if(y < 0 || y > yBound)
            return false;
        return true;
    }
}
