package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract class PlacableWeaponry extends Weaponry
{
    private int x;
    private int y;

    public PlacableWeaponry(int id, int health, int x, int y, Square[][] map)
    {
        super(id, health);
        this.x = x;
        this.y = y;
        place(x, y, map);
    }

    public int getX()
    {

        return x;
    }


    public int getY()
    {
        return y;
    }


    abstract boolean place(int x, int y, Square[][] map);
}
