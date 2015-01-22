package battleship;

import battleship.console.GraphicRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GamePanel extends JPanel {

    private BattleshipPanel panel1;
    private BattleshipPanel panel2;

    public void init(Player p1, Player p2, final GraphicRunner runner)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1 = new BattleshipPanel();
        panel1.init(p1, runner);
        panel2 = new BattleshipPanel();
        panel2.init(p2, runner);
        JPanel menuPanel = new JPanel();
        JButton[] buttons={
                new JButton("Next"),
                new JButton("NormalAttack"),
                new JButton("AirCraftAttack"),
                new JButton("RadarAttack"),
        };
        for(final JButton button:buttons)
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    runner.clickedOnMenuButton(button);
                }
            });
            menuPanel.add(button);
        }
        add(menuPanel, BorderLayout.CENTER);
        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);
    }
}
