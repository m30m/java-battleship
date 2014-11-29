package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Player
{
    private Square[][] map;
    private Player opponent;

    public Square[][] getMap()
    {
        return map;
    }

    void radarAttack(int x, int y)
    {
        //TODO:
    }

    void aircraftAttack(int x)
    {

    }

    void normalAttack(int x, int y)
    {
        opponent.getMap()[x][y].attacked();
    }
}