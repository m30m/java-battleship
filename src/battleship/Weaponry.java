package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract class Weaponry
{
    private int id;
    private int health;
    private Player owner;

    public Weaponry(int id, int health, Player owner)
    {
        this.id = id;
        this.health = health;
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public int getId()
    {

        return id;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }
}
