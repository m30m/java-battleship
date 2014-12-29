package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class AntiAircraft extends PlacableWeaponry
{
	/**
	 * constructor that gets the below arguments and creates an anti aircraft
	 * @param id the id of the anti aircraft
	 * @param owner the owner of the anti aircraft
	 * @param y the Y coordinate of the row that the anti aircraft had been placed in
	 */
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
