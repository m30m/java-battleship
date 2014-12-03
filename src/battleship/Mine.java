package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Mine extends PlacableWeaponry
{
    Mine(int id, Player owner, int x, int y)
    {
        super(id, 1, owner, x, y);
    }

    @Override
    public void attacked(int x,int y)
    {
        super.attacked(x, y);
        getOwner().getRunner().mineTrap(getOwner(), x, y);
        getOwner().normalAttack(x, y);
    }
}