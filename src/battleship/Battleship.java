package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Battleship extends PlacableWeaponry
{
    private final int length;
    private boolean isVertical;

    Battleship(int id,Player owner, int x, int y, int length, boolean isVertical)
    {
        super(id, length,owner, x, y);
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
    public void attacked(int x,int y)
    {
        super.attacked(x, y);
        getOwner().getRunner().explode(getOwner(), x, y);
    }
}
