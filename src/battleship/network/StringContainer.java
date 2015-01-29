package battleship.network;

import java.io.Serializable;

/**
 * Created by amin on 1/29/15.
 */
public abstract class StringContainer implements Serializable
{
    String messege;

    public String getMessege()
    {
        return messege;
    }

    public void setMessege(String messege)
    {

        this.messege = messege;
    }
}
