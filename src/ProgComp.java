import java.io.File;
import java.lang.Thread;
import java.lang.Process;
import java.lang.Runtime;
import java.util.ArrayList;

public class ProgComp
{
	public static void main(String[] args)
	{
		threads = new ArrayList<Thread>();
		
		ProgCompTeam test = new ProgCompTeam(new File("/home/students/test"));
		ProgCompTeam test2 = new ProgCompTeam(new File("/home/students/test2"));
		
		threads.add(new Thread(test));
		threads.get(0).start();
		
		threads.add(new Thread(test2));
		threads.get(1).start();
		
		//Waits for all threads to finish before termination
		while(true)
		{
			if(threads.isEmpty()) 
			{
				break;
			}
			
			threads.removeIf((Thread thr)-> !(thr.isAlive()));
		}

	}
	
	
	private static ArrayList<Thread> threads;
}
