package battleship;

import battleship.console.ConsoleRunner;

/**
 * Created by amin on 11/29/14.
 */
public class Player
{
    private Square[][] map;

    public void setOpponent(Player opponent)
    {
        this.opponent = opponent;
    }

    private Player opponent;

    private Runner runner;

    private int health;

    public String getName()
    {
        return name;
    }

    private String name;

    public Runner getRunner()
    {
        return runner;
    }

    public Player(String name, Runner runner, int width,int height)
    {
        this.health = 16;
        this.name=name;
        this.runner=runner;
        map=new Square[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                map[i][j]=new Square();
    }

    public Square[][] getMap()
    {
        return map;
    }

    public void radarAttack(int x, int y)
    {
        for(int i=Math.max(0, x - 1);i<Math.min(x + 2, map.length);i++)
            for(int j=Math.max(0,y-1);j<Math.min(y+2,map[x].length);j++)
                if(getOpponent().getMap()[i][j].getPlacableWeaponry() instanceof Battleship)
                    getRunner().radarDetect(this,i,j);

    }

    public void aircraftAttack(int y)
    {
        if(getOpponent().getMap()[0][y].getPlacableWeaponry() instanceof AntiAircraft )
        {
            ConsoleRunner.airTof = true;
            normalAttack(0,y);
            getRunner().aircraftUnsuccessful(this);
            ConsoleRunner.airTof = false;
            return;
        }
        for(int i=0;i<map.length;i++)
            normalAttack(i,y);
    }

    public void normalAttack(int x, int y)
    {
        opponent.getMap()[x][y].attacked(x, y);
    }

    public Player getOpponent()
    {
        return opponent;
    }

    public void decreaseHealth()
    {
        health--;
    }

    public boolean isLost()
    {
        return health <= 0;
    }
}