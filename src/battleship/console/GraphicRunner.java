package battleship.console;

import battleship.*;

import javax.swing.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GraphicRunner extends Runner {

    private GamePanel gamePanel;
    protected static final int[] LENGTH_OF_BATTLESHIP = new int[]{4, 3, 3, 2, 2, 1, 1};
    private int numOfBattleship;

    GameState state;

    @Override
    protected void mineTrap(Player player, int x, int y) {

    }

    @Override
    protected void explode(Player player, int x, int y) {

    }

    @Override
    protected void aircraftUnsuccessful(Player player) {

    }

    @Override
    protected void radarDetect(Player player, int x, int y) {

    }

    @Override
    protected void explodeAntiaircraft(Player player, int x, int y) {

    }

    @Override
    protected void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        frame.getContentPane().add(gamePanel);
        teamA=new Player("amoo", this, 10, 10);
        teamB=new Player("xashxash", this, 10, 10);
        gamePanel.init(teamA, teamB, this);
        state = GameState.TeamAInit;
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void clickedOnSquare(GraphicSquare graphicSquare, boolean isRight)
    {
        switch (state)
        {
            case TeamAInit:
            {
                readPlayerMap(teamA, graphicSquare,isRight);
                break;
            }
            case TeamBInit:
            {
                readPlayerMap(teamB, graphicSquare,isRight);
                break;
            }
            case TeamAPlaying:
            {
                break;
            }
            case TeamBPlaying:
            {
                break;
            }
        }
        if (numOfBattleship == LENGTH_OF_BATTLESHIP.length)
        {
            numOfBattleship = 0;
            if (state == GameState.TeamAInit)
                state = GameState.TeamBInit;
            else
                state = GameState.TeamAPlaying;
        }
        gamePanel.repaint();
    }

    private void readPlayerMap(Player player, GraphicSquare graphicSquare, boolean isRight)
    {
        if (graphicSquare.getSquare().getOwner() != player)
            return;
        try
        {
            new Battleship(0, player, graphicSquare.getSquare().getX(), graphicSquare.getSquare().getY(),
                    LENGTH_OF_BATTLESHIP[numOfBattleship], isRight);
        }
        catch (BattleshipException e)
        {
            return;
        }
        numOfBattleship++;
    }
}
