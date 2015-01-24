package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract class Weaponry
{
    private int health;
    private Player owner;

    /**
     * constructor that gets the below arguments and creates a weaponry
     * @param health the health of the weaponry
     * @param owner the owner of the weaponry
     */
    public Weaponry(int health, Player owner)
    {
        this.health = health;
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
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
