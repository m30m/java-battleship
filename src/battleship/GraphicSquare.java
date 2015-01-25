package battleship;

import battleship.console.GraphicRunner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by amin on 1/20/15.
 */
public class GraphicSquare extends JComponent
{
    public static final int SIZE = 50;
    private static int SQUARE_STATE = 0;
    Square square;
    GraphicRunner runner;

    private static BufferedImage[] mine = new BufferedImage[2];
    private static BufferedImage[] antiaircraft = new BufferedImage[2];
    private static BufferedImage[] battleship = new BufferedImage[8];

    static {
        try {
            mine[0] = ImageIO.read(new File("src/images/mine0.png"));
            mine[1] = ImageIO.read(new File("src/images/mine1.png"));
            antiaircraft[0] = ImageIO.read(new File("src/images/antiaircraft0.png"));
            antiaircraft[1] = ImageIO.read(new File("src/images/antiaircraft1.png"));
            for (int i = 0; i < 8; i++)
                battleship[i] = ImageIO.read(new File("src/images/battleship" + i + ".png"));
        } catch (IOException e) {
        }
    }

    public static void changeState(){
        SQUARE_STATE=1-SQUARE_STATE;
    }

    public GraphicSquare(Square square, final GraphicRunner runner)
    {
        this.runner = runner;
        this.square = square;
        int padding = 2;
        setBounds(square.getX() * SIZE + padding / 2, square.getY() * SIZE + padding / 2, SIZE - padding, SIZE - padding);
        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent)
            {
                runner.sendClickOnSquare(GraphicSquare.this, mouseEvent.getButton() == 3);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent)
            {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent)
            {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent)
            {

            }
        });
    }

    public Square getSquare()
    {
        return square;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        Color c = g2d.getColor();
        g2d.setColor(new Color(255, 230, 225));
        if (runner.getState() == GameState.TeamAPlaying || runner.getState() == GameState.TeamBPlaying) {
            if (square.isDestroyed())
                g2d.setColor(new Color(0, 0, 0));
            else if (square.isDetected()) {
                g2d.setColor(new Color(153, 230, 255));
                if (square.getPlacableWeaponry() instanceof Battleship) {
                    g2d.drawImage(battleship[selectBattleshipImage()], 0, 0, null);
                    g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0));
                }
            }

        } else {
            if (square.getPlacableWeaponry() == null)
                g2d.setColor(new Color(153, 230, 255));
            else if (square.getPlacableWeaponry() instanceof AntiAircraft){
                g2d.drawImage(antiaircraft[SQUARE_STATE], 0, 0, null);
                g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0));
            }
            else if (square.getPlacableWeaponry() instanceof Battleship){
                g2d.drawImage(battleship[selectBattleshipImage()], 0, 0, null);
                g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0));
            }
            else if (square.getPlacableWeaponry() instanceof Mine) {
                g2d.drawImage(mine[SQUARE_STATE], 0, 0, null);
                g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0));
            }
        }
        g2d.fillRect(0, 0, SIZE, SIZE);
    }

    private int selectBattleshipImage() {
        Battleship battleship=(Battleship) square.getPlacableWeaponry();
        if(battleship.isVertical())
        {
            if(battleship.getLength()==1)
                return 0;
            else if(battleship.getY()==square.getY())
                return 3;
            else if(battleship.getY()+battleship.getLength()-1==square.getY())
                return 4;
            else
                return 1;
        }
        else
        {
            if(battleship.getLength()==1)
                return 7;
            else if(battleship.getX()==square.getX())
                return 5;
            else if(battleship.getX()+battleship.getLength()-1==square.getX())
                return 6;
            else
                return 2;
        }
    }
}
