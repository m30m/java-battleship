package battleship;

import javax.swing.*;
import java.awt.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class BattleshipPanel extends JPanel {
    private static final int SIZE = 50;
    Player player;
    public void init(Player p) {
        player=p;
        setFocusable(true);
        requestFocus();
        setLayout(null);
        setPreferredSize(new Dimension(player.getWidth()*SIZE, player.getHeight()*SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(100, 70, 44));
        g2d.fillRect(0, 0, player.getWidth()*SIZE, player.getHeight()*SIZE);
    }
}
