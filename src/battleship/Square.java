package battleship;

/**
 * Created by amin on 11/29/14.
 */
public class Square
{
    private int x;
    private int y;
    private boolean destroyed;
    private PlacableWeaponry placableWeaponry;
    private Player owner;

    /**
     * constructor that creates a new empty square
     */
    Square(int x, int y, Player owner)
    {
        this.owner = owner;
        destroyed = false;
        placableWeaponry = null;
        this.x = x;
        this.y = y;
    }

    public Player getOwner()
    {
        return owner;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
        this.destroyed = destroyed;
    }

    public PlacableWeaponry getPlacableWeaponry()
    {
        return placableWeaponry;
    }

    public void setPlacableWeaponry(PlacableWeaponry placableWeaponry)
    {
        this.placableWeaponry = placableWeaponry;
    }
    
    /**
     * destroy the square if it had been attacked
     * @param x the X coordinate of the square
     * @param y the Y coordinate of the square
     * @param attacker
     */
    void attacked(int x, int y, String attacker)
    {
        if (isDestroyed()) //Why should you destroy it again???
            return;
        setDestroyed(true);
        if (placableWeaponry != null)
        {
            placableWeaponry.attacked(x, y,attacker);
            setPlacableWeaponry(null);
        }
    }
}
