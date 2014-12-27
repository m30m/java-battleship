package battleship;

import battleship.console.ConsoleRunner;

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

    protected Player teamA,teamB;

    protected abstract void mineTrap(Player player, int x, int y);

    protected abstract void explode(Player player, int x, int y);

    protected abstract void aircraftUnsuccessful(Player player);
    protected abstract void radarDetect(Player player, int x, int y);
    protected abstract void run();
    public static void main(String[] args)
    {
        Runner runner=new ConsoleRunner();
        runner.run();
    }
    
}
