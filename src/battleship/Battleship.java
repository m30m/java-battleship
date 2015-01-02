package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Battleship extends PlacableWeaponry
{
    private final int length;
    private boolean isVertical;

    /**
     * constructor that gets the below arguments and creates a battleship
     * @param id the id of the battleship
     * @param owner the owner of the battleship
     * @param x the X coordinate of the first square of the battleship
     * @param y the Y coordinate of the first square of the battleship
     * @param length defines the length of the battleship
     * @param isVertical if the battleship is vertical
     */
    public Battleship(int id,Player owner, int x, int y, int length, boolean isVertical)
    {
        super(id, length,owner, x, y);
        this.length = length;
        this.isVertical = isVertical;
        place(x, y, owner.getMap());
    }

    @Override
    public boolean place(int x, int y, Square[][] map)
    {
        if (isVertical)
        {
            for (int i = y; i < y + length; i++)
            {
                if (map[x][i].getPlacableWeaponry() != null)
                    return false;
            }
            for (int i = y; i < y + length; i++)
                map[x][i].setPlacableWeaponry(this);
        }
        else
        {
            for (int i = x; i < x + length; i++)
            {
                if (map[i][y].getPlacableWeaponry() != null)
                    return false;
            }
            for (int i = x; i < x + length; i++)
                map[i][y].setPlacableWeaponry(this);
        }
        return true;
    }

    @Override
    public void attacked(int x, int y, String attacker)
    {
        super.attacked(x, y, attacker);
        getOwner().getRunner().explode(getOwner().getOpponent(), x, y);
        getOwner().decreaseHealth();
    }
}
