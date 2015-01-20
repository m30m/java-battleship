package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Player
{
    private int height;
    private int width;
    private Square[][] map;
    private Player opponent;
    private Runner runner;
    private int health;
    private String name;

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
        this.width=width;
        this.height=height;
        map=new Square[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                map[i][j]=new Square();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName()
    {
        return name;
    }

    public Runner getRunner()
    {
        return runner;
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
            normalAttack(0,y,"Aircraft");
            getRunner().aircraftUnsuccessful(this);
            return;
        }
        for(int i=0;i<map.length;i++)
            normalAttack(i,y,"Aircraft");
    }

    /**
     * the player normally attacks on of his opponent square
     * @param x the X coordinate of the square
     * @param y the Y coordinate of the square
     */
    public void normalAttack(int x, int y,String attacker)
    {
        opponent.getMap()[x][y].attacked(x, y,attacker);
    }

    public Player getOpponent()
    {
        return opponent;
    }

    public void setOpponent(Player opponent)
    {
        this.opponent = opponent;
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