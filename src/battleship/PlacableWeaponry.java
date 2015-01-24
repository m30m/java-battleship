package battleship;

/**
 * Created by amin on 11/29/14.
 */
abstract class PlacableWeaponry extends Weaponry
{
    private int x;
    private int y;

    /**
     * constructor that gets the below arguments and creates a placable weaponry
     * @param health the health of the placable weaponry
     * @param owner the owner of the placable weaponry
     * @param x the X coordinate of the placable weaponry
     * @param y the Y coordinate of the placable weaponry
     */
    public PlacableWeaponry(int health, Player owner, int x, int y)
    {
        super(health, owner);
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }


    public int getY()
    {
        return y;
    }

    /**
     * place the equipment in the square, if the square was empty
     * @param x the X coordinate of the square that we want to place the equipment in it
     * @param y the Y coordinate of the square that we want to place the equipment in it
     * @param map the map where we want to place the equipment
     * @return if the square was empty and we can place the equipment
     */
    boolean place(int x, int y, Square[][] map)
    {
        if (map[x][y].getPlacableWeaponry() != null)
            throw new BattleshipException("Filled square");
        map[x][y].setPlacableWeaponry(this);
        return true;
    }
    
	/**
	 * reduces the health of the equipment that had been attacked
     * @param x the X coordinate of the square that had been attacked
     * @param y the Y coordinate of the square that had been attacked
     * @param attacker
     */
    void attacked(int x, int y, String attacker)
    {
        setHealth(getHealth() - 1);
    }
}
