package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class AntiAircraft extends PlacableWeaponry
{
    public AntiAircraft(int id,Player owner, int x)
    {
        super(id, 1,owner, x, 0);
        place(x, 0, owner.getMap());
    }
}
