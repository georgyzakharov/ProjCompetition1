import java.io.File;
import java.io.IOException;
import java.lang.Process;
import java.lang.Runtime;
import java.util.ArrayList;
import java.awt.Robot;

public class ProgCompTeam implements Runnable
{
	public ProgCompTeam(File Directory) 
	{
		System.out.println("Constructed team");
		this.Directory = Directory;
		
		Files = new ArrayList<File>();
	}
	
	public void run() 
	{
		System.out.println("Created thread for team");
		for( File file:Directory.listFiles())
		{
			System.out.println("Running thread for " + file.getName());
			if(!(Files.contains(file)) && file.isFile()) 
			{	
				compile(file);
				exec(file);
				grade(file);
				Files.add(file);
			}
		}
	}
	
	private void compile(File file) 
	{
		String[] syscommand = new String[2]; 
		syscommand [1] = file.getAbsolutePath();
		System.out.println("Compiling "+file.getName());
		
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
			System.out.println(syscommand[0]+syscommand[1]);
			P = Runtime.getRuntime().exec(syscommand);
			
			while (P.isAlive()) {}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void exec(File file) 
	{
		System.out.println("Trying to execute code for "+file.getName());
		String syscommand = ""; 		
		
		if(file.getName().contains(".c")) 
		{
			syscommand.concat(file.getAbsolutePath());
		}
		else if(file.getName().contains(".java")) 
		{
			System.out.println(syscommand);
			syscommand.concat("java ").concat(file.getAbsolutePath());
			System.out.println(syscommand);
			syscommand = syscommand.substring(0, syscommand.indexOf('.'));
			System.out.println(syscommand);
		}
		else if(file.getName().contains(".py")) 
		{
			syscommand.concat("python3 ").concat(file.getAbsolutePath());
		}
		
		try
		{
			System.out.println(syscommand);
			P = Runtime.getRuntime().exec(syscommand);
			
			while(P.isAlive()) 
			{
				int seconds=10;
				
				for(seconds=seconds*2 ; seconds > 0 ;seconds--)
				{
					bob.delay(500);
					
					if(!P.isAlive())
					{
						System.out.println("Program finished execution before timeout");
						break;
					}
				}
			}
			
			Submissions.add(new ProgCompSubmission(file,P.getInputStream()));
			
			
			if(P.isAlive()) {P.destroyForcibly();}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void grade(File file) 
	{
		for(ProgCompSubmission sub : Submissions) 
		{
			if (file.equals(sub.file))
			{
				//Push the output to grading software
			   //using output stream and system call
				System.out.print(sub.data);
			}
		}
	}
	
	private Process P;
	
	
	private File Directory;
	private ArrayList<File> Files;
	private ArrayList<ProgCompSubmission> Submissions;
	private Robot bob;
	//private String TeamName, Password;
}
