package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Mine extends PlacableWeaponry
{
	/**
	 * constructor that gets the below arguments and implants a mine
	 * @param id the id of the mine
	 * @param owner the owner of the mine
	 * @param x the X coordinate of the mine
	 * @param y the Y coordinate of the mine
	 */
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
