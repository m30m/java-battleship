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

    /**
     * constructor that gets the below arguments and creates a player
     * @param name the name of the player
     * @param runner the runner
     * @param width the width of the player's map
     * @param height the height of the player's map
     */
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

    /**
     * the player attacks with radar
     * @param x the X coordinate of the square that the player place the radar 
     * @param y the Y coordinate of the square that the player place the radar
     */
    public void radarAttack(int x, int y)
    {
        for(int i=Math.max(0, x - 1);i<Math.min(x + 2, map.length);i++)
            for(int j=Math.max(0,y-1);j<Math.min(y+2,map[x].length);j++)
                if(getOpponent().getMap()[i][j].getPlacableWeaponry() instanceof Battleship)
                    getRunner().radarDetect(this,i,j);

    }
    
    /**
     * the player attacks his opponent map with an aircraft
     * @param y the Y coordinate of the row the the player attacks
     */
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

    /**
     * the player normally attacks on of his opponent square
     * @param x the X coordinate of the square
     * @param y the Y coordinate of the square
     */
    public void normalAttack(int x, int y)
    {
        opponent.getMap()[x][y].attacked(x, y);
    }

    public Player getOpponent()
    {
        return opponent;
    }

    /**
     * decrease the health of the player
     */
    public void decreaseHealth()
    {
        health--;
    }
    
    /**
     * check if the player is lost
     * @return if the player is lost
     */
    public boolean isLost()
    {
        return health <= 0;
    }
}