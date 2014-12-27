package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Mine extends PlacableWeaponry
{
    public Mine(int id, Player owner, int x, int y)
    {
        super(id, 1, owner, x, y);
        place(x, y, owner.getMap());
    }

    @Override
    public void attacked(int x,int y)
    {
        super.attacked(x, y);
        getOwner().normalAttack(x, y);
        getOwner().getRunner().mineTrap(getOwner().getOpponent(), x, y);
    }
}
