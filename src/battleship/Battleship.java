package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Battleship extends Weaponry implements Placable
{
    private final int length;
    private boolean isVertical;

    Battleship(int id, int length, boolean isVertical)
    {
        super(id, length);
        this.length = length;
        this.isVertical = isVertical;
    }

    @Override
    public boolean place(int x, int y, Square[][] map)
    {
        if (isVertical)
        {
            for (int i = y; i < y + length; i++)
            {
                if (map[x][i].getWeaponry() != null)
                    return false;
            }
            for (int i = y; i < y + length; i++)
                map[x][i].setWeaponry(this);
        }
        else
        {
            for (int i = x; i < x + length; i++)
            {
                if (map[i][y].getWeaponry() != null)
                    return false;
            }
            for (int i = x; i < x + length; i++)
                map[i][y].setWeaponry(this);
        }
        return true;
    }
}
