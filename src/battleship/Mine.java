package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Mine extends PlacableWeaponry
{
    Mine(int id, Player owner, int x, int y, Square[][] map)
    {
        super(id, 1, owner, x, y, map);
    }

    @Override
    public void attacked()
    {
        super.attacked();
        getOwner().normalAttack(getX(), getY());
    }
}
