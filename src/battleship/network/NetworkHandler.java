package battleship.network;

import battleship.Player;
import battleship.Square;
import battleship.console.GraphicRunner;
import battleship.network.ActionContainer;
import battleship.network.MessegeContainer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHandler implements Runnable
{
    public ObjectInputStream inputStream = null;
    public ObjectOutputStream outputStream = null;

    public NetworkHandler(GraphicRunner runner)
    {
        this.runner = runner;
    }

    private GraphicRunner runner;

    public boolean isTeamA()
    {
        return isTeamA;
    }

    public boolean isTeamA;

    @Override
    public void run()
    {
        while (true)
        {
            try
            {

                Object object = inputStream.readObject();
                if (object instanceof ActionContainer)
                {
                    ActionContainer actionContainer = (ActionContainer) object;
                    Player player = actionContainer.isTeamA() ? runner.getTeamA() : runner.getTeamB();
                    Square square = player.getMap()[actionContainer.getX()][actionContainer.getY()];
                    runner.setAttackType(actionContainer.getAttackType());
                    runner.clickedOnSquare(square, actionContainer.isRightClick());
                }
                else if (object instanceof MessegeContainer)
                {
                    runner.sendMessage(isTeamA ? runner.getTeamB() : runner.getTeamA(), ((MessegeContainer) object).getMessege());
                }
                else if (object instanceof CommandContainer)
                {
                    String s=((CommandContainer) object).getMessege();
                    if(s.equals("next"))
                        runner.clickedOnButton("Next");
//                    else if(s.equals("pause"))
//
                }
            } catch (IOException e)
            {
                break;
            } catch (ClassNotFoundException e)
            {
                break;
            }
        }
    }

    public void sendObject(Object object)
    {
        try
        {
            outputStream.writeObject(object);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getIP()//Copy pasted from stackoverflow
    {
        String ip = "127.0.0.1";
        try
        {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements(); )
            {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements(); )
                {
                    InetAddress addr = a.nextElement();
                    if (!addr.getHostAddress().startsWith("127.") && !addr.getHostAddress().startsWith("0:"))
                        ip = addr.getHostAddress();
                }
            }
        } catch (SocketException e)
        {
            return ip;
        }
        return ip;
    }
}