package battleship;

import battleship.console.GraphicRunner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by The_CodeBreakeR on 1/20/15.
 */
public class GamePanel extends JPanel {

    private BattleshipPanel panel1;
    private BattleshipPanel panel2;
    private JPanel menuPanel = new JPanel();
    private JTextArea status=new JTextArea("Welcome, please build your maps!");
    private JButton[] buttons={
            new JButton("Next"),
            new JButton("NormalAttack"),
            new JButton("AirCraftAttack"),
            new JButton("RadarAttack"),
    };

    private static BufferedImage buttonImages[]=new BufferedImage[4];

    static {
        try {
            buttonImages[1] = ImageIO.read(new File("src/images/normal.png"));
            buttonImages[2] = ImageIO.read(new File("src/images/aircraft.png"));
            buttonImages[3] = ImageIO.read(new File("src/images/radar.png"));
        } catch (IOException e) {
        }
    }

    public void init(Player p1, Player p2, final GraphicRunner runner)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1 = new BattleshipPanel();
        panel1.init(p1, runner);
        panel2 = new BattleshipPanel();
        panel2.init(p2, runner);
        int height = p1.getHeight() * GraphicSquare.SIZE;
        menuPanel.setPreferredSize(new Dimension(200, height));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.setLayout(null);
        for(int i=0;i<4;i++) {
            final JButton button=buttons[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    runner.clickedOnMenuButton(button);
                }
            });
            button.setSize(new Dimension(150,height/9));
            button.setLocation(25,(i)*(height/8));
            if(p1.getHeight()>6 && buttonImages[i]!=null) {
                Image air = buttonImages[i].getScaledInstance(button.getWidth(), button.getHeight(), java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(air));
            }
            menuPanel.add(button);
        }
        status.setSize(new Dimension(150, 4 * (height/8) ));
        status.setEditable(false);
        status.setWrapStyleWord(true);
        status.setLineWrap(true);
        status.setLocation(25, 4 * (height/8));
        menuPanel.add(status);
        add(menuPanel, BorderLayout.CENTER);
        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);
    }

    public void showGameOverMessage (String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addStatus(String message) {
        status.setText(message+'\n'+status.getText());
    }
}
