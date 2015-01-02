package battleship;

import battleship.console.ConsoleRunner;
import battleship.console.FileRunner;

/**
 * Created by amin on 11/29/14.
 */
abstract public class Runner
{

    protected Player teamA,teamB;
    /**
     * the "mine trap" message
     * @param player the player
     * @param x the X coordinate of the mine
     * @param y the Y coordinate of the mine
     */
    protected abstract void mineTrap(Player player, int x, int y);
    /**
     * the "explode" message
     * @param player the player
     * @param x the X coordinate of the exploded square
     * @param y the Y coordinate of the exploded square
     */
    protected abstract void explode(Player player, int x, int y);
    /**
     * the "aircraft unsuccessful" message
     * @param player the player
     */
    protected abstract void aircraftUnsuccessful(Player player);
    /**
     * the "detected" message
     * @param player the player
     * @param x the X coordinate of the detected square
     * @param y the Y coordinate of the detected square
     */
    protected abstract void radarDetect(Player player, int x, int y);
    /**
     * the "anti aircraft exploded" message
     * @param player the player
     * @param x the X coordinate of the anti aircraft
     * @param y the Y coordinate of the anti aircraft
     */
    protected abstract void explodeAntiaircraft(Player player, int x, int y);
    /**
     * runs the runner
     */
    protected abstract void run();
    public static void main(String[] args)
    {
        Runner runner=new ConsoleRunner();
        runner.run();
    }

}
