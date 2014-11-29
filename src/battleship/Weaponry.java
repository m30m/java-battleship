package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Weaponry
{
    private int id;
    private int health;

    public Weaponry(int id, int health)
    {
        this.id = id;
        this.health = health;
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
