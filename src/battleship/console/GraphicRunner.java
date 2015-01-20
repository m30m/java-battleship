package battleship.console;

import battleship.GamePanel;
import battleship.Player;
import battleship.Runner;

import javax.swing.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GraphicRunner extends Runner {
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

        GamePanel panel = new GamePanel ();
        frame.getContentPane().add(panel);
        teamA=new Player("amoo", this, 10, 10);
        teamB=new Player("xashxash", this, 10, 10);
        panel.init (teamA, teamB);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
