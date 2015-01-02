package battleship.console;

import battleship.AntiAircraft;
import battleship.Battleship;
import battleship.Mine;
import battleship.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by amin on 1/2/15.
 */
public class FileRunner extends TextRunner
{
    String file_path = null;

    @Override
    protected void run()
    {
        if (file_path == null)
            file_path = "src/battleship/console/sample_map.txt";
        Scanner scanner;
        try
        {
            scanner = new Scanner(new File(file_path));

        } catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            return;
        }
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        teamA = new Player("a", this, width, height);
        teamB = new Player("b", this, width, height);
        teamA.setOpponent(teamB);
        teamB.setOpponent(teamA);
        readPlayerMap(scanner, teamA, width, height);
        readPlayerMap(scanner, teamB, width, height);
        readActions(new Scanner(System.in));
    }

    /**
     * Reads the player map from scanner
     * @param scanner input
     * @param player the player whose map we are reading
     * @param width the width of the map
     * @param height the height of the map
     */
    private void readPlayerMap(Scanner scanner, Player player, int width, int height)
    {
        for (int i = 0; i < width; i++)
        {
            String line = scanner.next();
            for (int j = 0; j < height; j++)
            {
                char c = line.charAt(j);

                if (c == 'A')//AntiAircraft
                    new AntiAircraft(0, player, j);
                else if (c == 'B')//Battleship
                    new Battleship(0, player, i, j, 1, true);
                else if (c == 'M')//Mine
                    new Mine(0, player, i, j);
            }
        }
    }
}
