import java.io.File;
import java.lang.Thread;
import java.lang.Process;
import java.lang.Runtime;
import java.util.ArrayList;

public class ProgCompTeam implements Runnable
{
	public ProgCompTeam(File Directory) 
	{
		this.Directory = Directory;
		Files = new ArrayList<File>();
	}
	
	public void run() 
	{
		
	}
	
	private File Directory;
	private ArrayList<File> Files;
	
	private String TeamName, Password;
}
