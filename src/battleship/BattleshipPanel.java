package battleship;

import battleship.console.GraphicRunner;

import javax.swing.*;
import java.awt.*;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class BattleshipPanel extends JComponent
{
    Player player;
    private GraphicSquare[][] map;
    private GraphicRunner runner;

    public void init(Player p, GraphicRunner runner)
    {
        this.runner = runner;
        player=p;
        setFocusable(true);
        requestFocus();
        setLayout(null);
        int width = player.getWidth();
        int height = player.getHeight();
        setPreferredSize(new Dimension(width * GraphicSquare.SIZE, height * GraphicSquare.SIZE));
        map = new GraphicSquare[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
            {
                map[i][j] = new GraphicSquare(player.getMap()[i][j], runner);
                add(map[i][j]);
            }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(100, 70, 44));
        g2d.fillRect(0, 0, player.getWidth() * GraphicSquare.SIZE, player.getHeight() * GraphicSquare.SIZE);
    }
}
