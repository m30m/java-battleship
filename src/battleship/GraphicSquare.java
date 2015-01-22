package battleship;

import battleship.console.GraphicRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by amin on 1/20/15.
 */
public class GraphicSquare extends JComponent
{
    public static final int SIZE = 50;
    Square square;
    GraphicRunner runner;

    public GraphicSquare(Square square, final GraphicRunner runner)
    {
        this.runner = runner;
        this.square = square;
        int padding = 6;
        setBounds(square.getX() * SIZE + padding / 2, square.getY() * SIZE + padding / 2, SIZE - padding, SIZE - padding);
        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent)
            {
                System.out.println(mouseEvent.getButton());
                runner.clickedOnSquare(GraphicSquare.this, mouseEvent.getButton() == 3);
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
    protected void paintComponent(Graphics g)
    {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255, 230, 225));
        if(runner.getState()==GameState.TeamAPlaying || runner.getState()==GameState.TeamBPlaying) {
            if(square.isDestroyed())
                g2d.setColor(new Color(0, 0, 0));
            if(square.isDetected())
            {
                g2d.setColor(new Color(153, 230, 255));
                if(square.getPlacableWeaponry() instanceof Battleship)
                    g2d.setColor(new Color(23, 255, 139));
            }

        }
        else {
            if (square.getPlacableWeaponry() == null)
                g2d.setColor(new Color(153, 230, 255));
            else if (square.getPlacableWeaponry() instanceof AntiAircraft)
                g2d.setColor(new Color(222, 255, 76));
            else if (square.getPlacableWeaponry() instanceof Battleship)
                g2d.setColor(new Color(23, 255, 139));
            else if (square.getPlacableWeaponry() instanceof Mine)
                g2d.setColor(new Color(170, 54, 255));
        }
        g2d.fillRect(0, 0, SIZE, SIZE);
    }
}