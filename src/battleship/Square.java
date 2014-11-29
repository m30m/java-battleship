package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Square
{
    private boolean destroyed;
    private PlacableWeaponry placableWeaponry;

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

    void attacked()
    {
        if (isDestroyed())//Why should you destroy it again???
            return;
        setDestroyed(true);
        if (placableWeaponry != null)
        {
            placableWeaponry.attacked();
            setPlacableWeaponry(null);
        }
    }
}
