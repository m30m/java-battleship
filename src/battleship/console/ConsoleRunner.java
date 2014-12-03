package battleship.console;

import battleship.Player;
import battleship.Runner;

/**
 * Created by amin on 11/29/14.
 */
public class ConsoleRunner extends Runner
{
    void mineTrap(Player player, int x, int y)
    {
        System.out.println("team "+player.getName()+" mine trap "+x+","+y);
    }

    void explode(Player player,int x, int y)
    {
        System.out.println("team "+player.getName()+" explode "+x+","+y);
    }

    void aircraftUnsuccessful(Player player)
    {
        System.out.println("aircraft unsuccessful");
    }
}
