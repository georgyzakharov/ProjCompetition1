import java.io.File;
import java.io.IOException;
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
		for( File file:Directory.listFiles())
		{
			if(!(Files.contains(file)) && file.isFile()) 
			{	
				compile(file);
				exec(file);
				Files.add(file);
			}
		}
	}
	
	private void compile(File file) 
	{
		String[] syscommand = new String[2]; 
		syscommand [1] = file.getAbsolutePath();
		
		if(file.getName().contains(".c")) 
		{
			syscommand[0] = "clang";
		}
		else if(file.getName().contains(".java")) 
		{
			syscommand[0] = "javac";
		}
		else if(file.getName().contains(".py")) 
		{
			return;
		}
			
		try 
		{
			//Process P = Runtime.getRuntime().exec(syscommand);
			ProgComp.processes.add(Runtime.getRuntime().exec(syscommand));
		}
		catch(IOException e) 
		{
			
		}
	}
	
	private void exec(File file) 
	{
		String syscommand = ""; 		
		
		if(file.getName().contains(".c")) 
		{
			syscommand.concat(file.getAbsolutePath());
		}
		else if(file.getName().contains(".java")) 
		{
			syscommand.concat("javax ").concat(file.getAbsolutePath());
		}
		else if(file.getName().contains(".py")) 
		{
			syscommand.concat("python3 ").concat(file.getAbsolutePath());
		}
		
		try
		{
			//Process p = Runtime.getRuntime().exec(syscommand);
			ProgComp.processes.add(Runtime.getRuntime().exec(syscommand));
		} 
		catch (IOException e)
		{
			
		}
		
	}
	
	private File Directory;
	private ArrayList<File> Files;
	
	private String TeamName, Password;
}
