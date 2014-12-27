package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class AntiAircraft extends PlacableWeaponry
{
    public AntiAircraft(int id, Player owner, int y)
    {
        super(id, 1, owner, 0, y);
        place(0, y, owner.getMap());
    }

    @Override
    public void attacked(int x, int y)
    {
        super.attacked(x, y);
        getOwner().getRunner().explodeAntiaircraft(getOwner(), x, y);
    }
}
