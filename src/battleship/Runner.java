package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract public class Runner
{
//    void placeBattleship(Player player,int x,int y,int length,boolean isVertical)
//    {
//        new Battleship(0,player,x,y,length,isVertical);
//    }
//    void placeAircraft(Player player,int x,int y,int length,boolean isVertical)

    abstract void mineTrap(Player player, int x, int y);

    abstract void explode(Player player,int x, int y);

    abstract void aircraftUnsuccessful(Player player);
}
