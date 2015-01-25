package battleship;

import battleship.console.GraphicRunner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private JTextField chatArea = new JTextField("");
    private JPanel statusPanel = new JPanel();
    private JButton[] buttons={
            new JButton("Next"),
            new JButton("NormalAttack"),
            new JButton("AirCraftAttack"),
            new JButton("RadarAttack"),
    };
    private JLabel scoreboard1 = new JLabel();
    private JLabel scoreboard2 = new JLabel();
    private JButton pauseButton = new JButton("Pause");
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
                    runner.clickedOnButton(button);
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
        int width = (int) (panel1.getPreferredSize().getWidth() * 2 + menuPanel.getPreferredSize().getWidth());
        statusPanel.setPreferredSize(new Dimension(width, 65));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statusPanel.setLayout(null);
        chatArea.setSize(new Dimension(150, 35));
        chatArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    runner.sendMessaege(chatArea.getText());
                    chatArea.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        chatArea.setLocation((int) (panel1.getPreferredSize().getWidth() + 25), 10);
        scoreboard1.setSize(new Dimension((int) panel1.getPreferredSize().getWidth(), 55));
        scoreboard2.setSize(new Dimension((int) panel2.getPreferredSize().getWidth(), 55));
        scoreboard1.setLocation(0, 10);
        scoreboard2.setLocation((int) (panel1.getPreferredSize().getWidth() + menuPanel.getPreferredSize().getWidth()), 10);
        pauseButton.setSize(80,20);
        pauseButton.setLocation((int) (panel1.getPreferredSize().getWidth()*2 + menuPanel.getPreferredSize().getWidth())-pauseButton.getWidth(),40);
        pauseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                runner.clickedOnButton(pauseButton);
            }
        });
        statusPanel.add(chatArea);
        statusPanel.add(scoreboard1);
        statusPanel.add(scoreboard2);
        statusPanel.add(pauseButton);

        add(menuPanel, BorderLayout.CENTER);
        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);
        add(statusPanel, BorderLayout.PAGE_END);
    }

    public void showGameOverMessage (String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addStatus(String message) {
        status.setText(message + '\n' + status.getText());
    }

    @Override
    public void repaint() {
        super.repaint();
        if (panel1 == null || panel2 == null)
            return;
        Player player1 = panel1.getPlayer();
        Player player2 = panel2.getPlayer();
        scoreboard1.setText("<html> AirCrafts: " + player1.getNumOfAirCrafts() + "/" + 2 +
                "<br>Radars: " + player1.getNumOfRadars() + "/" + 4 + "<br>Destroyed Squares: " + (16 - player1.getHealth()) + "</html>");
        scoreboard2.setText("<html> AirCrafts: " + player2.getNumOfAirCrafts() + "/" + 2 +
                "<br>Radars: " + player2.getNumOfRadars() + "/" + 4 + "<br>Destroyed Squares: " + (16 - player2.getHealth()) + "</html>");
    }
}
