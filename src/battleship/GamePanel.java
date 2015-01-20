package battleship;

import battleship.console.GraphicRunner;

import javax.swing.*;
import java.awt.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GamePanel extends JPanel {

    private BattleshipPanel panel1;
    private BattleshipPanel panel2;

    public void init(Player p1, Player p2, GraphicRunner runner)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1 = new BattleshipPanel();
        panel1.init(p1, runner);
        panel2 = new BattleshipPanel();
        panel2.init(p2, runner);
        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);
    }
}
