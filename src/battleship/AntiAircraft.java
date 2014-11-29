package battleship;

/**
 * Created by amin on 11/29/14.
 */
class AntiAircraft extends PlacableWeaponry
{
    AntiAircraft(int id,Player owner, int x, Square[][] map)
    {
        super(id, 1,owner, x, 0, map);
    }
}
