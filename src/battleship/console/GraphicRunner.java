package battleship.console;

import battleship.*;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GraphicRunner extends Runner {

    public static final int PORT = 8585;
    private GamePanel gamePanel;
    protected static final int[] LENGTH_OF_BATTLESHIP = new int[]{4, 3, 3, 2, 2, 1, 1};
    private int numOfBattleships = 0;
    private int numOfMines = 0;
    private int numOfAntiaircrafts = 0;
    private AttackType attackType=null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public GameState getState() {
        return state;
    }

    private GameState state;

    public void mineTrap(Player player, int x, int y)
    {
        x++;
        y++;
        gamePanel.addStatus("team " + player.getName() + " mine trap " + x + "," + y);
    }

    public void radarDetect(Player player, int x, int y)
    {
        x++;
        y++;
        gamePanel.addStatus("team " + player.getName() + " detected " + x + "," + y);
    }

    public void explode(Player player,int x, int y)
    {
        x++;
        y++;
        gamePanel.addStatus("team "+player.getName()+" explode "+x+","+y);
    }

    public void aircraftUnsuccessful(Player player)
    {
        gamePanel.addStatus("aircraft unsuccessful");
    }

    public void explodeAntiaircraft(Player player, int x, int y)
    {
        y++;
        gamePanel.addStatus("team " + player.getName() + " anti aircraft row " + y + " exploded");
    }

    @Override
    protected void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!state.equals(GameState.GameOver)) {
                            GraphicSquare.changeState();
                            gamePanel.repaint();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        frame.getContentPane().add(gamePanel);
        //Single/Multi Player
        Object[] gameModes = {"On single computer", "On network"};
        int gameMode = JOptionPane.showOptionDialog(frame, "Please select the game mode", "Battleship",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameModes, gameModes[0]);
        if (gameMode == 0)
        {
            final int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the width of the map please"));
            final int height = Integer.parseInt(JOptionPane.showInputDialog("Enter the height of the map please"));
            teamA = new Player(JOptionPane.showInputDialog("Player 1 enter your name please"), this, width, height);
            teamB = new Player(JOptionPane.showInputDialog("Player 2 enter your name please"), this, width, height);
            teamA.setOpponent(teamB);
            teamB.setOpponent(teamA);
            gamePanel.init(teamA, teamB, this);
            state = GameState.TeamAPlaceBattleship;
        }
        else//Multiplayer
        {
            final String name = JOptionPane.showInputDialog("Please enter your name");
            Object[] options = {"Host a server", "Connect to another server"};
            int connection = JOptionPane.showOptionDialog(frame, "How do you want to start the game?", "Battleship",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            try
            {
                if (connection == 0)//Server
                {
                    final int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the width of the map please"));
                    final int height = Integer.parseInt(JOptionPane.showInputDialog("Enter the height of the map please"));
                    teamA = new Player(name, this, width, height);
                    final ServerSocket serverSocket = new ServerSocket(PORT, 2);
//                    JOptionPane pane = new JOptionPane("Waiting for client...\nHost address is :"+getIP(), JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Cancel"}, null); TODO:make cancel work
                    final JOptionPane pane = new JOptionPane("Waiting for client...\nHost address is :" + getIP(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                    final JDialog dialog = pane.createDialog(null, "Waiting for connection");
                    Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Socket socket = serverSocket.accept();
                                outputStream = new ObjectOutputStream(socket.getOutputStream());
                                inputStream = new ObjectInputStream(socket.getInputStream());
                                outputStream.writeObject(name);
                                outputStream.writeObject(width);
                                outputStream.writeObject(height);
                                String otherName = (String) inputStream.readObject();
                                teamB = new Player(otherName, GraphicRunner.this, width, height);
                                pane.setMessage(otherName + " connected.");
                                Thread.sleep(3000);
                                dialog.dispose();

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    dialog.setVisible(true);
                }
                else//Client
                {

                    final String host = JOptionPane.showInputDialog("Enter host address:", "127.0.0.1");
                    final JOptionPane pane = new JOptionPane("Connecting to " + host, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                    final JDialog dialog = pane.createDialog(null, "Connecting...");
                    final Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            while (inputStream == null)
                            {
                                try
                                {
                                    Socket socket = new Socket(host, PORT);
                                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                                    inputStream = new ObjectInputStream(socket.getInputStream());
                                    outputStream.writeObject(name);
                                    String otherName = (String) inputStream.readObject();
                                    int width = (Integer) inputStream.readObject();
                                    int height = (Integer) inputStream.readObject();
                                    System.out.println("Width : " + width + " Height: " + height);
                                    teamB = new Player(name, GraphicRunner.this, width, height);
                                    teamA = new Player(otherName, GraphicRunner.this, width, height);
                                    pane.setMessage("Connected to " + otherName);
                                    Thread.sleep(3000);
                                    dialog.dispose();

                                } catch (Exception e)
                                {
                                    try
                                    {
                                        Thread.sleep(1000);//Trying to establish a connection every 1 second
                                    } catch (InterruptedException e1)
                                    {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                    thread.start();
                    dialog.setVisible(true);

                }
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(frame, "An error occurred, please try again");
                return;
            }
            teamA.setOpponent(teamB);
            teamB.setOpponent(teamA);
            gamePanel.init(teamA, teamB, this);
            state = GameState.TeamAPlaceBattleship;
        }
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String getIP()//Copy pasted from stackoverflow
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
                    if (addr.getHostAddress().startsWith("192."))
                        ip = addr.getHostAddress();
                }
            }
        } catch (SocketException e)
        {
            return ip;
        }
        return ip;
    }

    public void clickedOnMenuButton(JButton button)
    {
        if(state==GameState.GameOver)
            return;
        String s=button.getText();
        int so=state.ordinal();
        if(s.equals("Next"))
        {
            if(so==1 || so==2 || so==4 || so==5)
                state = GameState.values()[state.ordinal() + 1];
        }
        else if(s.equals("NormalAttack"))
            attackType=AttackType.Normal;
        else if(s.equals("AirCraftAttack"))
            attackType=AttackType.AirCraft;
        else
            attackType=AttackType.Radar;
        if(so<6)
            attackType=null;
        gamePanel.repaint();
    }

    public void clickedOnSquare(GraphicSquare graphicSquare, boolean isRight)
    {
        if(state==GameState.GameOver)
            return;
        if (state.ordinal() < GameState.TeamBPlaceBattleship.ordinal())
            readPlayerMap(teamA, graphicSquare, isRight);
        else if (state.ordinal() < GameState.TeamAPlaying.ordinal())
            readPlayerMap(teamB, graphicSquare, isRight);
        else
        {
            Player attacker=teamA;
            if(state==GameState.TeamBPlaying)
                attacker=teamB;
            try {
                if (graphicSquare.getSquare().getOwner() == attacker)
                    throw new BattleshipException("Clicking on the other player's map");
                int x = graphicSquare.getSquare().getX();
                int y = graphicSquare.getSquare().getY();
                if (attackType == AttackType.Normal)
                    attacker.normalAttack(x, y, "Normal");
                else if (attackType == AttackType.AirCraft)
                    attacker.aircraftAttack(y);
                else if (attackType == AttackType.Radar)
                    attacker.radarAttack(x, y);
                else
                    throw new BattleshipException("Null attack");
                if (state == GameState.TeamBPlaying)
                    state = GameState.TeamAPlaying;
                else
                    state = GameState.TeamBPlaying;
            }
            catch (BattleshipException e)
            {
                gamePanel.addStatus(e.getMessage());
                return;
            }
        }
        if (numOfBattleships == LENGTH_OF_BATTLESHIP.length || numOfMines == 5 || numOfAntiaircrafts == 2)
        {
            numOfBattleships = 0;
            numOfMines = 0;
            numOfAntiaircrafts = 0;
            state = GameState.values()[state.ordinal() + 1];
        }
        attackType=null;
        gamePanel.repaint();
        if (teamA.isLost())
        {
            gamePanel.showGameOverMessage("team " + teamB.getName() + " wins");
            gamePanel.addStatus("team " + teamB.getName() + " wins");
            state=GameState.GameOver;
            return;
        }
        if (teamB.isLost())
        {
            gamePanel.showGameOverMessage("team " + teamA.getName() + " wins");
            gamePanel.addStatus("team " + teamA.getName() + " wins");
            state=GameState.GameOver;
            return;
        }
    }

    private void readPlayerMap(Player player, GraphicSquare graphicSquare, boolean isRight)
    {
        try
        {
            if (graphicSquare.getSquare().getOwner() != player)
                throw new BattleshipException("Clicking on the other player's map");
            if (state == GameState.TeamAPlaceMine || state == GameState.TeamBPlaceMine)
            {
                new Mine(player, graphicSquare.getSquare().getX(), graphicSquare.getSquare().getY());
                numOfMines++;
            }
            else if (state == GameState.TeamAPlaceAntiaircraft || state == GameState.TeamBPlaceAntiaircraft)
            {
                new AntiAircraft(player, graphicSquare.getSquare().getY());
                numOfAntiaircrafts++;
            }
            else
            {
                new Battleship(player, graphicSquare.getSquare().getX(), graphicSquare.getSquare().getY(),
                        LENGTH_OF_BATTLESHIP[numOfBattleships], isRight);
                numOfBattleships++;
            }
        }
        catch (BattleshipException e)
        {
            gamePanel.addStatus(e.getMessage());
            return;
        }
    }
}
