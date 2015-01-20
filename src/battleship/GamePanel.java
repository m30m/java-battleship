package battleship;

import javax.swing.*;
import java.awt.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GamePanel extends JPanel {
    public void init(Player p1, Player p2)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        BattleshipPanel battleshipPanel1=new BattleshipPanel();
        battleshipPanel1.init(p1);
        BattleshipPanel battleshipPanel2=new BattleshipPanel();
        battleshipPanel2.init(p2);
        add(battleshipPanel1, BorderLayout.WEST);
        add(battleshipPanel2, BorderLayout.EAST);
    }
}
