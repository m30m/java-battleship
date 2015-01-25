package battleship.network;

import java.io.Serializable;

/**
 * Created by amin on 1/25/15.
 */
public class MessegeContainer implements Serializable
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
