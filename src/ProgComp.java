import java.io.File;
import java.lang.Thread;
import java.lang.Process;
import java.lang.Runtime;
import java.util.ArrayList;

public class ProgComp
{
	public static void main(String[] args)
	{
		processes = new ArrayList<Process>();
		threads = new ArrayList<Thread>();
		
		ProgCompTeam test = new ProgCompTeam(new File("/home/students/test"));
		
		threads.add(new Thread(test));
		threads.get(0).start();

	}
	
	protected static ArrayList<Process> processes;
	private static ArrayList<Thread> threads;
}
