package battleship.console;

import battleship.*;

import javax.swing.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GraphicRunner extends Runner {

    private GamePanel gamePanel;
    protected static final int[] LENGTH_OF_BATTLESHIP = new int[]{4, 3, 3, 2, 2, 1, 1};
    private int numOfBattleships = 0;
    private int numOfMines = 0;
    private int numOfAntiaircrafts = 0;
    private AttackType attackType=AttackType.Normal;

    public GameState getState() {
        return state;
    }

    private GameState state;

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
        teamA.setOpponent(teamB);
        teamB.setOpponent(teamA);
        gamePanel.init(teamA, teamB, this);
        state = GameState.TeamAPlaceBattleship;
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void clickedOnSquare(GraphicSquare graphicSquare, boolean isRight)
    {
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
                //  attackType=null;
            }
            catch (BattleshipException e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }
        if (numOfBattleships == LENGTH_OF_BATTLESHIP.length || numOfMines == 5 || numOfAntiaircrafts == 2)
        {
            numOfBattleships = 0;
            numOfMines = 0;
            numOfAntiaircrafts = 0;
            state = GameState.values()[state.ordinal() + 1];
            System.out.println("I'm here and state is:");
            System.out.println(state);
        }
        gamePanel.repaint();
    }

    private void readPlayerMap(Player player, GraphicSquare graphicSquare, boolean isRight)
    {
        try
        {
            if (graphicSquare.getSquare().getOwner() != player)
                throw new BattleshipException("Clicking on the other player's map");
            if (state == GameState.TeamAPlaceMine || state == GameState.TeamBPlaceMine)
            {
                new Mine(0, player, graphicSquare.getSquare().getX(), graphicSquare.getSquare().getY());
                numOfMines++;
            }
            else if (state == GameState.TeamAPlaceAntiaircraft || state == GameState.TeamBPlaceAntiaircraft)
            {
                new AntiAircraft(0, player, graphicSquare.getSquare().getY());
                numOfAntiaircrafts++;
            }
            else
            {
                new Battleship(0, player, graphicSquare.getSquare().getX(), graphicSquare.getSquare().getY(),
                        LENGTH_OF_BATTLESHIP[numOfBattleships], isRight);
                numOfBattleships++;
            }
        }
        catch (BattleshipException e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }
}
