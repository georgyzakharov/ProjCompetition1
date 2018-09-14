import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class ProgCompSubmission
{
	public ProgCompSubmission(File file,InputStream stream) 
	{
		data = "";
		this.file = file;
		Scanner scanner = new Scanner(stream);
		
		while(scanner.hasNext()) 
		{
			data = data + ((scanner.nextLine()).concat("\n"));
		}
	}
	
	protected String data;
	protected File file;
}
