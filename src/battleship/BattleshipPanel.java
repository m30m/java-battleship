package battleship;

import battleship.console.GraphicRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class BattleshipPanel extends JComponent
{
    public Player getPlayer() {
        return player;
    }

    private Player player;
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
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(52, 65, 255));
        int width = player.getWidth() * GraphicSquare.SIZE;
        int height = player.getHeight() * GraphicSquare.SIZE;
        g2d.fillRect(0, 0, width, height);
        paintChildren(g);
        paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int width = player.getWidth() * GraphicSquare.SIZE;
        int height = player.getHeight() * GraphicSquare.SIZE;
        if (runner.isPaused()) {
            g2d.setColor(new Color(128, 128, 128, 100));
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(new Color(255, 255, 255, 220));
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            String paused_string = "Paused";
            drawString(g2d, paused_string, width / 2, height/ 2);
        }
    }

    /**
     * Convenient function to draw a string centered in an arbitrary position
     *
     * @param g2d     Graphics2D to draw in
     * @param str     string to draw
     * @param centerX X coordinate of the position
     * @param centerY Y coordinate of the position
     */
    private void drawString(Graphics2D g2d, String str, int centerX, int centerY) {
        Rectangle2D stringBounds = g2d.getFontMetrics().getStringBounds(str, g2d);
        int startX = centerX - (int) stringBounds.getWidth() / 2;
        int startY = centerY - (int) stringBounds.getHeight() / 2;
        g2d.drawString(str, startX, startY);
    }
}
