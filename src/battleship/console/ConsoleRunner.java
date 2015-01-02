package battleship.console;

import battleship.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by amin on 11/29/14. this is changed
 */
public class ConsoleRunner extends TextRunner
{

    @Override
    protected void run()
    {
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        teamA = new Player("a", this, width, height);
        teamB = new Player("b", this, width, height);
        teamA.setOpponent(teamB);
        teamB.setOpponent(teamA);
        scanner.nextLine();
        System.out.println("Player number 1, please build your map");
        readPlayerMap(scanner, teamA);
        System.out.println("Player number 2, please build your map");
        readPlayerMap(scanner, teamB);
        readActions(scanner);
    }

    /**
     * Reading player map: the player place items on his/her map one at a time
     * @param input
     * @param player
     */
    void readPlayerMap(Scanner input, Player player)
    {
        String str, equipment = "battleship";
        int[] lengthOfBattleship = {4, 3, 3, 2, 2, 1, 1};
        int numOfBattleship = 0;
        while (true)
        {
            str = input.nextLine();
            if (str.equals("done"))
                break;
            if (str.equals("anti aircraft") || str.equals("mine"))
            {
                equipment = str;
                continue;
			}
            str = str.replace(',', ' ');
            Scanner scanner = new Scanner(str);
            if (equipment.equals("battleship"))
            {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                x--;
                y--;
                new Battleship(0, player, x, y,
                        lengthOfBattleship[numOfBattleship], scanner.next().equals("V"));
                numOfBattleship++;
            }
            else if (equipment.equals("anti aircraft"))
                new AntiAircraft(0, player, scanner.nextInt() - 1);
            else
                new Mine(0, player, scanner.nextInt() - 1, scanner.nextInt() - 1);
        }
    }

}
