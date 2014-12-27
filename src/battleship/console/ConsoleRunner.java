package battleship.console;

import battleship.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by amin on 11/29/14. this is changed
 */
public class ConsoleRunner extends Runner
{

    TreeMap<Integer, ArrayList<String>> tm = new TreeMap<Integer, ArrayList<String>>();
    private int currentTime = 0;

    @Override
    protected void run()
    {
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        System.out.println("Hey there");
        teamA = new Player("a", this, width, height);
        teamB = new Player("b", this, width, height);
        teamA.setOpponent(teamB);
        teamB.setOpponent(teamA);
        scanner.nextLine();
        readPlayerMap(scanner, teamA);
        readPlayerMap(scanner, teamB);
        readActions(scanner);
    }

    void readActions(Scanner scanner)
    {
        while (scanner.hasNext())
        {
            String str = scanner.nextLine();
            if (str.contains("go"))
            {
                Scanner tmp = new Scanner(str);
                tmp.next();
                currentTime += tmp.nextInt();
                while (!tm.isEmpty() && tm.firstKey() < currentTime)
                {
                    ArrayList<String> actions = tm.get(tm.firstKey());
                    for (String action : actions)
                        executeAction(action);
                    tm.remove(tm.firstKey());
                }
            }
            else
            {
                int timeToExecute;
                if (str.contains("attack"))
                    timeToExecute = 1;
                else//radar and aircraft
                    timeToExecute = 2;
                int finalTime = currentTime + timeToExecute;
                if (tm.get(finalTime) == null)
                    tm.put(finalTime, new ArrayList<String>());
                tm.get(finalTime).add(str);
            }
        }
    }

    private void executeAction(String action)
    {
        Player executor = null;
        if (action.contains("team a"))
            executor = teamA;
        else
            executor = teamB;
        action = action.replace(',', ' ');
        Scanner tmp = new Scanner(action);
        tmp.next();//team
        tmp.next();//a or b
        String attackType = tmp.next();
        if (attackType.equals("aircraft"))
        {
            int row = tmp.nextInt();
            executor.aircraftAttack(row);
        }
        else
        {
            int x = tmp.nextInt();
            int y = tmp.nextInt();
            if (action.contains("attack"))
                executor.normalAttack(x, y);
            else//radar
                executor.radarAttack(x, y);
        }

    }

    void readPlayerMap(Scanner input, Player player)
    {
        String str = null, equipment = "battleship";
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
                new Battleship(0, player, x, y,
                        lengthOfBattleship[numOfBattleship], scanner.next().equals("V"));
                numOfBattleship++;
            }
            else if (equipment.equals("anti aircraft"))
                new AntiAircraft(0, player, scanner.nextInt());
            else
                new Mine(0, player, scanner.nextInt(), scanner.nextInt());
		}
	}
	
    public void mineTrap(Player player, int x, int y)
    {
        System.out.println("team "+player.getName()+" mine trap "+x+","+y);
    }
    
    public void radarDetect(Player player, int x, int y)
    {
    	System.out.println("team "+player.getName()+" detected "+x+","+y);
    }

    public void explode(Player player,int x, int y)
    {
        System.out.println("team "+player.getName()+" explode "+x+","+y);
    }

    public void aircraftUnsuccessful(Player player)
    {
        System.out.println("aircraft unsuccessful");
    }
}
