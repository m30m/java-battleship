package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class AntiAircraft extends PlacableWeaponry
{
	/**
	 * constructor that gets the below arguments and creates an anti aircraft
	 * @param owner the owner of the anti aircraft
	 * @param y the Y coordinate of the row that the anti aircraft had been placed in
	 */
    public AntiAircraft(Player owner, int y)
    {
        super(1, owner, 0, y);
        place(0, y, owner.getMap());
    }

    @Override
    public void attacked(int x, int y, String attacker)
    {
        super.attacked(x, y, attacker);
        if (!attacker.equals("Aircraft"))
            getOwner().getRunner().explodeAntiaircraft(getOwner(), x, y);
    }
}
