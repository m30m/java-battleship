package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract class PlacableWeaponry extends Weaponry
{
    private int x;
    private int y;

    public PlacableWeaponry(int id, int health,Player owner, int x, int y)
    {
        super(id, health,owner);
        this.x = x;
        this.y = y;
    }

    public int getX()
    {

        return x;
    }


    public int getY()
    {
        return y;
    }


    boolean place(int x, int y, Square[][] map)
    {
        if (map[x][y].getPlacableWeaponry() != null)
            return false;
        map[x][y].setPlacableWeaponry(this);
        return true;
    }

    void attacked(int x,int y)
    {
        setHealth(getHealth() - 1);
    }
}
