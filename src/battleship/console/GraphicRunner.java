package battleship.console;

import battleship.*;
import battleship.network.ActionContainer;
import battleship.network.CommandContainer;
import battleship.network.MessegeContainer;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GraphicRunner extends Runner
{

    public static final int PORT = 8585;
    protected static final int[] LENGTH_OF_BATTLESHIP = new int[]{4, 3, 3, 2, 2, 1, 1};
    NetworkHandler networkHandler = null;
    Player pausedPlayer;
    private GamePanel gamePanel;
    private int numOfBattleships = 0;
    private int numOfMines = 0;
    private int numOfAntiaircrafts = 0;
    private AttackType attackType = null;
    private boolean isPaused;
    private GameState state;
    private JFrame frame;

    public void setAttackType(AttackType attackType)
    {
        this.attackType = attackType;
    }

    public GameState getState()
    {
        return state;
    }

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

    public void explode(Player player, int x, int y)
    {
        x++;
        y++;
        gamePanel.addStatus("team " + player.getName() + " explode " + x + "," + y);
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
    protected void run()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGui();
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        while (!state.equals(GameState.GameOver))
                        {
                            GraphicSquare.changeState();
                            gamePanel.repaint();
                            try
                            {
                                Thread.sleep(200);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void sendMessaege(String text)
    {
        if (networkHandler != null)
        {
            sendMessage(networkHandler.isTeamA ? teamA : teamB, text);
            MessegeContainer messege = new MessegeContainer();
            messege.setMessege(text);
            networkHandler.sendObject(messege);
        }
        else
            sendMessage(null, text);
    }

    public void sendMessage(Player player, String text)
    {
        gamePanel.addStatus((player != null ? player.getName() + ": " : "") + text);
    }

    public boolean isNetwork()
    {
        return networkHandler != null;
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    public NetworkHandler getNetworkHandler()
    {
        return networkHandler;
    }

    private void createAndShowGui()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Object[] gameModes = {"On single computer", "On network"};
        int gameMode = JOptionPane.showOptionDialog(frame, "Please select the game mode", "Battleship",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameModes, gameModes[0]);
        if (gameMode == 0)
        {
//            final int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the width of the map please"));
//            final int height = Integer.parseInt(JOptionPane.showInputDialog("Enter the height of the map please"));
            final int width = 10;
            final int height = 10;
//            teamA = new Player(JOptionPane.showInputDialog("Player 1 enter your name please"), this, width, height);
//            teamB = new Player(JOptionPane.showInputDialog("Player 2 enter your name please"), this, width, height);
            teamA = new Player("Amin", this, width, height);
            teamB = new Player("Taba", this, width, height);
        }
        else//On network
        {
            if (!establishNetwork()) return;
        }
        teamA.setOpponent(teamB);
        teamB.setOpponent(teamA);
        state = GameState.TeamAPlaceBattleship;
        gamePanel = new GamePanel(teamA, teamB, this);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean establishNetwork()
    {
        networkHandler = new NetworkHandler();
        final String name = JOptionPane.showInputDialog("Please enter your name");
        Object[] options = {"Host a server", "Connect to another server"};
        int connection = JOptionPane.showOptionDialog(frame, "How do you want to start the game?", "Battleship",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        try
        {
            if (connection == 0)//Server
            {
                networkHandler.isTeamA = true;
                final int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the width of the map please"));
                final int height = Integer.parseInt(JOptionPane.showInputDialog("Enter the height of the map please"));
                teamA = new Player(name, this, width, height);
                final ServerSocket serverSocket = new ServerSocket(PORT, 2);
                final JOptionPane pane = new JOptionPane("Waiting for client...\nHost address is :" + networkHandler.getIP(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                final JDialog dialog = pane.createDialog(null, "Waiting for connection");
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Socket socket = serverSocket.accept();
                            networkHandler.outputStream = new ObjectOutputStream(socket.getOutputStream());
                            networkHandler.inputStream = new ObjectInputStream(socket.getInputStream());
                            networkHandler.outputStream.writeObject(name);
                            networkHandler.outputStream.writeObject(width);
                            networkHandler.outputStream.writeObject(height);
                            String otherName = (String) networkHandler.inputStream.readObject();
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
                networkHandler.isTeamA = false;
                final String host = JOptionPane.showInputDialog("Enter host address:", "127.0.0.1");
                final JOptionPane pane = new JOptionPane("Connecting to " + host, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                final JDialog dialog = pane.createDialog(null, "Connecting...");
                final Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        while (networkHandler.inputStream == null)
                        {
                            try
                            {
                                Socket socket = new Socket(host, PORT);
                                networkHandler.outputStream = new ObjectOutputStream(socket.getOutputStream());
                                networkHandler.inputStream = new ObjectInputStream(socket.getInputStream());
                                networkHandler.outputStream.writeObject(name);
                                String otherName = (String) networkHandler.inputStream.readObject();
                                int width = (Integer) networkHandler.inputStream.readObject();
                                int height = (Integer) networkHandler.inputStream.readObject();
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
            return false;
        }
        Thread thread = new Thread(networkHandler);
        thread.start();
        return true;
    }

    public void sendClickOnButton(String s)
    {
        if (isNetwork())
        {
            if (s.equals("Next"))
            {
                CommandContainer command = new CommandContainer();
                command.setMessege(s);
                networkHandler.sendObject(command);
            }
            else if (s.equals("Pause"))
            {
                CommandContainer command = new CommandContainer();
                command.setMessege(s);
                networkHandler.sendObject(command);
            }
        }
        clickedOnButton(s, getMyPlayer());
    }

    public void clickedOnButton(String s, Player player)
    {
        if (state == GameState.GameOver)
            return;
        if (s.equals("Pause"))
        {
            if (!isPaused || (isPaused && pausedPlayer == player))
            {
                isPaused = !isPaused;
                pausedPlayer = player;
            }
            return;
        }
        if (isPaused())
            return;
        int so = state.ordinal();
        if (s.equals("Next"))
        {
            if (so == 1 || so == 2 || so == 4 || so == 5)
                state = GameState.values()[state.ordinal() + 1];
        }
        else if (s.equals("NormalAttack"))
            attackType = AttackType.Normal;
        else if (s.equals("AirCraftAttack"))
            attackType = AttackType.AirCraft;
        else
            attackType = AttackType.Radar;
        if (so < 6)
            attackType = null;
        gamePanel.repaint();
    }

    public void sendClickOnSquare(GraphicSquare graphicSquare, boolean isRight)
    {
        Square square = graphicSquare.getSquare();
        if (isNetwork() && state.ordinal() < GameState.TeamAPlaying.ordinal() && square.getOwner() != getMyPlayer())
            return;
        if (isNetwork())
        {
            ActionContainer actionContainer = new ActionContainer();
            actionContainer.setAttackType(attackType);
            actionContainer.setRightClick(isRight);
            actionContainer.setTeamA(square.getOwner() == teamA);
            actionContainer.setX(square.getX());
            actionContainer.setY(square.getY());
            networkHandler.sendObject(actionContainer);
        }
        clickedOnSquare(square, isRight);
    }

    public void clickedOnSquare(Square square, boolean isRight)
    {
        if (state == GameState.GameOver)
            return;
        if (isPaused())
            return;
        if (state.ordinal() < GameState.TeamBPlaceBattleship.ordinal())
            readPlayerMap(teamA, square, isRight);
        else if (state.ordinal() < GameState.TeamAPlaying.ordinal())
            readPlayerMap(teamB, square, isRight);
        else
        {
            final Player attacker = getAttacker();
            try
            {
                if (square.getOwner() == attacker)
                    throw new BattleshipException("Clicking on the other player's map");
                final int x = square.getX();
                final int y = square.getY();
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
            } catch (BattleshipException e)
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
        attackType = null;
        gamePanel.repaint();
        if (teamA.isLost())
        {
            gamePanel.showGameOverMessage("team " + teamB.getName() + " wins");
            gamePanel.addStatus("team " + teamB.getName() + " wins");
            state = GameState.GameOver;
            return;
        }
        if (teamB.isLost())
        {
            gamePanel.showGameOverMessage("team " + teamA.getName() + " wins");
            gamePanel.addStatus("team " + teamA.getName() + " wins");
            state = GameState.GameOver;
            return;
        }
    }

    private Player getAttacker()
    {
        Player attacker = teamA;
        if (state == GameState.TeamBPlaying)
            attacker = teamB;
        return attacker;
    }

    private void readPlayerMap(Player player, Square square, boolean isRight)
    {
        try
        {
            if (square.getOwner() != player)
                throw new BattleshipException("Clicking on the other player's map");
            if (state == GameState.TeamAPlaceMine || state == GameState.TeamBPlaceMine)
            {
                new Mine(player, square.getX(), square.getY());
                numOfMines++;
            }
            else if (state == GameState.TeamAPlaceAntiaircraft || state == GameState.TeamBPlaceAntiaircraft)
            {
                new AntiAircraft(player, square.getY());
                numOfAntiaircrafts++;
            }
            else
            {
                new Battleship(player, square.getX(), square.getY(),
                        LENGTH_OF_BATTLESHIP[numOfBattleships], isRight);
                numOfBattleships++;
            }
        } catch (BattleshipException e)
        {
            gamePanel.addStatus(e.getMessage());
            return;
        }
    }

    public Player getTeamB()
    {
        return teamB;
    }

    public Player getTeamA()
    {
        return teamA;
    }

    public Player getMyPlayer()
    {
        if (isNetwork())
            return networkHandler.isTeamA() ? teamA : teamB;
        return teamA;
    }

    public Player getOtherPlayer()
    {
        if (isNetwork())
            return networkHandler.isTeamA() ? teamB : teamA;
        return teamB;
    }

    public class NetworkHandler implements Runnable
    {
        public boolean isTeamA;
        private ObjectInputStream inputStream = null;
        private ObjectOutputStream outputStream = null;

        public boolean isTeamA()
        {
            return isTeamA;
        }

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
                        Player player = actionContainer.isTeamA() ? getTeamA() : getTeamB();
                        Square square = player.getMap()[actionContainer.getX()][actionContainer.getY()];
                        setAttackType(actionContainer.getAttackType());
                        clickedOnSquare(square, actionContainer.isRightClick());
                    }
                    else if (object instanceof MessegeContainer)
                    {
                        sendMessage(isTeamA ? getTeamB() : getTeamA(), ((MessegeContainer) object).getMessege());
                    }
                    else if (object instanceof CommandContainer)
                    {
                        String s = ((CommandContainer) object).getMessege();
                        if (s.equals("Next"))
                            clickedOnButton(s, getOtherPlayer());
                        else if (s.equals("Pause"))
                            clickedOnButton(s, getOtherPlayer());

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
}
