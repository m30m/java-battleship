package battleship.console;

import battleship.Player;
import battleship.Runner;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by amin on 1/2/15.
 */
abstract public class TextRunner extends Runner
{
    TreeMap<Integer, ArrayList<String>> tm = new TreeMap<Integer, ArrayList<String>>();
    private int currentTime = 0;

    /**
     * Reading actions from scanner and storing them in a TreeMap to make them sorted by their time in order to execute them chronologically correct
     *
     * @param scanner the input
     */
    void readActions(Scanner scanner)
    {
        int teamATime=0,teamBTime=0;
        while (scanner.hasNext())
        {
            String str = scanner.nextLine();
            if (str.contains("go"))
            {
                Scanner tmp = new Scanner(str);
                tmp.next();
                currentTime += tmp.nextInt();
                while (!tm.isEmpty() && tm.firstKey() <= currentTime)
                {
                    ArrayList<String> actions = tm.get(tm.firstKey());
                    for (String action : actions)
                    {
                        executeAction(action);
                        if (teamA.isLost())
                        {
                            System.out.println("team b wins");
                            return;
                        }
                        if (teamB.isLost())
                        {
                            System.out.println("team a wins");
                            return;
                        }

                    }
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
                int finalTime;
                if(str.contains("team a"))
                {
                    teamATime += timeToExecute;
                    finalTime=teamATime;
                }
                else
                {
                    teamBTime += timeToExecute;
                    finalTime=teamBTime;
                }
                if (tm.get(finalTime) == null)
                    tm.put(finalTime, new ArrayList<String>());
                tm.get(finalTime).add(str);
            }
        }
    }

    /**
     * Executes a single action
     *
     * @param action the action string which is read from input
     */
    private void executeAction(String action)
    {
        Player executor;
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
            row--;
            executor.aircraftAttack(row);
        }
        else
        {
            int x = tmp.nextInt();
            int y = tmp.nextInt();
            x--;
            y--;
            if (action.contains("attack"))
                executor.normalAttack(x, y, "Normal");
            else//radar
                executor.radarAttack(x, y);
        }

    }

    //Reporting events to console:

    public void mineTrap(Player player, int x, int y)
    {
        x++;
        y++;
        System.out.println("team "+player.getName()+" mine trap "+x+","+y);
    }

    public void radarDetect(Player player, int x, int y)
    {
        x++;
        y++;
        System.out.println("team "+player.getName()+" detected "+x+","+y);
    }

    public void explode(Player player,int x, int y)
    {
        x++;
        y++;
        System.out.println("team "+player.getName()+" explode "+x+","+y);
    }

    public void aircraftUnsuccessful(Player player)
    {
        System.out.println("aircraft unsuccessful");
    }

    public void explodeAntiaircraft(Player player, int x, int y)
    {
        y++;
        System.out.println("team " + player.getName() + " anti aircraft row " + y + " exploded");
    }
}
