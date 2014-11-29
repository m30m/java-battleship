package battleship;

/**
 * Created by amin on 11/29/14.
 */
class Square {
    private boolean destroyed;
    private Weaponry weaponry;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Weaponry getWeaponry() {
        return weaponry;
    }

    public void setWeaponry(Weaponry weaponry) {
        this.weaponry = weaponry;
    }
}
