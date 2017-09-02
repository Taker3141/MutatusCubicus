package level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import world.World;

public class LevelParser
{
	public static void parseLevelFile(File levelFile, World w)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(levelFile));
			List<Line> readLines = new ArrayList<>();
			boolean parsing = false;
			for(String line = reader.readLine(); line != null; line = reader.readLine())
			{
				line = line.trim();
				if(line.startsWith("#")) continue;
				if(line.startsWith("init") && !parsing) 
				{
					parsing = true; continue;
				}
				if(parsing)
				{
					if(line.startsWith("{")) continue;
					if(line.startsWith("}")) break;
					System.out.println(line);
					readLines.add(new Line(line));
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Unable to load level file \'" + levelFile + "\'");
			e.printStackTrace();
		}
	}
	
	private static class Line
	{
		public final String functionName;
		public final String[] arguments;
		
		public Line(String line)
		{
			String[] parts = line.split(" ", 2);
			functionName = parts[0];
			String[] fragments = parts[1].split(",");
			List<String> args = new ArrayList<>();
			boolean glue = false;
			for(int i = 0; i < fragments.length; i++)
			{
				if(fragments[i].trim().startsWith("\"") && !fragments[i].trim().endsWith("\"")) 
				{
					glue = true;
					args.add(fragments[i].trim());
					continue;
				}
				if(!glue) args.add(fragments[i].trim());
				else args.set(args.size() - 1, args.get(args.size() - 1) + fragments[i]);
				if(fragments[i].trim().endsWith("\"")) glue = false;
			}
			arguments = new String[args.size()];
			for(int i = 0; i < args.size(); i++)
			{
				if(args.get(i).startsWith("\"")) arguments[i] = args.get(i).substring(1, args.get(i).length() - 1);
				else arguments[i] = args.get(i);
			}
		}
	}
}
