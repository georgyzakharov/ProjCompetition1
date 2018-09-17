import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Process;
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.Scanner;

public class ProgCompTeam implements Runnable
{
	public ProgCompTeam(File Directory) 
	{
		System.out.println("Constructed team");
		
		this.Directory = Directory;
		
		Files = new ArrayList<File>();
	}
	
	//The method called at the start of a thread
	public void run() 
	{
		/*Runs a set of methods for each new file that shows up in the team directory.
		 *Each file that is operated on is added to a list of completed file.
		 *This is done so that no file is compiled, executed, and graded more than once 
		 * */
		System.out.println("Created thread for team");
		for( File file:Directory.listFiles())
		{
			System.out.println("Running thread for " + file.getName());
			if(!(Files.contains(file)) && file.isFile()) 
			{	
				//compile(file);
				exec(file);
				//grade(file);
				Files.add(file);
			}
		}
	}
	
	//A method that compiles the File(if applicable) that is passed as an argument
	private void compile(File file) 
	{
		//Creates an array of strings to be used as a system command
		String[] syscommand = new String[2]; 
		syscommand [1] = file.getAbsolutePath();
		
		//Adds necessary syntax for the system command depending on the file extention
		if(file.getName().contains(".cpp")) 
		{
			syscommand[0] = "g++ ";
			syscommand[1] = syscommand[1] + (" -o "+file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf('.')));
		}
		else if(file.getName().contains(".c")) 
		{
			syscommand[0] = "gcc ";
			syscommand[1] = syscommand[1] + (" -o "+file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf('.')));
		}
		else if(file.getName().contains(".java")) 
		{
			syscommand[0] = "javac ";
		}
		else if(file.getName().contains(".py")) 
		{
			return;
		}
		
		//Creates a new process that runs the system command
		try 
		{
			Process P = Runtime.getRuntime().exec(syscommand[0] + syscommand[1]);
			
			while(P.isAlive()) {}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//A method that runs the compiled executable and captures output to be stored in a submission 
	private void exec(File program) 
	{
		//Declares a string to be used as a system command
		System.out.println("Trying to execute code for "+program.getName());
		String syscommand = ""; 		
		
		
		//Adds elements to the syste command depending on the file type
		if(program.getName().contains(".c")) 
		{
			syscommand = program.getAbsolutePath().substring(0, program.getAbsolutePath().indexOf('.'));
		}
		else if(program.getName().contains(".java")) 
		{
			System.out.println(syscommand);
			syscommand = syscommand + "java -cp "+Directory.getAbsolutePath() + " " + program.getName();
			System.out.println(syscommand);
			syscommand = syscommand.substring(0, syscommand.indexOf('.'));
			System.out.println(syscommand);
		}
		else if(program.getName().contains(".py")) 
		{
			syscommand.concat("python3 ").concat(program.getAbsolutePath());
		}
		/*
		//Searches for a file containing inputs for the child program
		File inputs = null;
		for(File f : Directory.getParentFile().listFiles()) 
		{
			if((f.getName().substring(0, f.getName().indexOf('.'))).equals(program.getName().substring(0, program.getName().indexOf('.')))) 
			{
				inputs = f;
			}
		}
		
		//Creates a scanner for the inputs in the inputs file
		Scanner inputsForProg = null;
		try
		{
			inputsForProg = new Scanner(inputs);
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		*/
		String output = "";
		
		//Executes the child program and feeds it input while capturing output
		try
		{
			System.out.println(syscommand);
			
			Process P = Runtime.getRuntime().exec(syscommand);
			Scanner inputFromProg =  new Scanner(P.getInputStream());
			OutputStream outputToProg = P.getOutputStream();
			while (P.isAlive()) 
			{
				/*if(inputsForProg.hasNextLine()) 
				{
					outputToProg.write(inputsForProg.nextLine().getBytes());
				}*/
				while(inputFromProg.hasNextLine())
				{
					output = output + inputFromProg.nextLine() + "\n";
				}
			}
			
			inputFromProg.close();
			Submissions.add(new ProgCompSubmission(program,output));
			
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		
	}
	
	//A method that grades a submission
	private void grade(File file) 
	{
		for(ProgCompSubmission sub : Submissions) 
		{
			if (file.equals(sub.file))
			{
				//Push the output to grading software
				//Println for testing
				System.out.print(sub.data);
			}
		}
	}
	
	
	private File Directory;
	private ArrayList<File> Files;
	private ArrayList<ProgCompSubmission> Submissions;
	protected ArrayList<Process> processes;
	//private String TeamName, Password;
}
