package battleship;

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

    void radarAttack(int x, int y)
    {
        for(int i=Math.max(0, x - 1);i<Math.min(x + 2, map.length);i++)
            for(int j=Math.max(0,y-1);j<Math.min(y+2,map[x].length);j++)
                if(map[i][j].getPlacableWeaponry() instanceof Battleship)
                {
                    //TODO
                    return;
                }

    }

    void aircraftAttack(int x)
    {
        if(map[x][0].getPlacableWeaponry() instanceof AntiAircraft ) //TODO check if its null
        {
            normalAttack(x,0);
            getRunner().aircraftUnsuccessful(this);
            return;
        }
        for(int i=0;i<map[x].length;i++)
            normalAttack(x,i);
    }

    void normalAttack(int x, int y)
    {
        opponent.getMap()[x][y].attacked(x, y);
    }
}