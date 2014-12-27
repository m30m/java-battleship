package battleship.console;

import java.util.Scanner;

import battleship.Player;
import battleship.Runner;

/**
 * Created by amin on 11/29/14. this is changed
 */
public class ConsoleRunner extends Runner
{
	
	void staticReader(Scanner input, Player player)
	{
		String str=null, equipment="Battleship";
		int[] lengthOfBattleship={4, 3, 3, 2, 2, 1, 1};
		int numOfBattleship=0;
		while(!str.equals("DONE"))
		{
			str=input.nextLine();
			if(str.equals("Anti aircraft") || str.equals("Mine"))
			{
				equipment=str;
				continue;
			}
			str.replace(',', ' ');
			Scanner scanner=new Scanner(str);
			if(equipment.equals("Battleship"))
			{
				new Battleship(0, player, scanner.nextInt(), scanner.nextInt(), 
						lengthOfBattleship[numOfBattleship], scanner.nextByte()=='V');
				numOfBattleship++;
			}
			else if(equipment.equals("Anti aircraft"))
				new AntiAircraft(0, player, scanner.nextInt());
			else
				new Mine(0, player, scanner.nextInt(), scanner.nextInt());
		}
	}
	
    public void mineTrap(Player player, int x, int y)
    {
        System.out.println("team "+player.getName()+" mine trap "+x+","+y);
    }

    public void explode(Player player,int x, int y)
    {
        System.out.println("team "+player.getName()+" explode "+x+","+y);
    }

    public aircraftUnsuccessful(Player player)
    {
        System.out.println("aircraft unsuccessful");
    }
}
