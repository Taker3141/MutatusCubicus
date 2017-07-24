package localize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Localizer
{
	private Map<String, String> map;
	private String name;
	
	public Localizer(String fileName)
	{
		name = fileName;
		try
		{
			FileReader fr = new FileReader(new File("res/lang/" + fileName + ".lang"));
			BufferedReader reader = new BufferedReader(fr);
			
			map = new HashMap<String, String>();
			String line = reader.readLine();
			
			while (line != null)
			{
				if (line.length() > 3)
				{
					String[] splitLine = line.split("=");
					map.put(splitLine[0], splitLine[1]);
				}
				line = reader.readLine();
			}
			
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Localisation file \"" + fileName + "\" could not be found!");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String localizeString(String unlocalized)
	{
		String localized = map.get(unlocalized);
		if(unlocalized.startsWith("o!"))
		{
			return unlocalized.substring(2);
		}
		else return unlocalized;
//		if (localized == null)
//		{
//			System.out.println("\"" + unlocalized + "\" could not be found in the \"" + name + "\" lang file!");
//			return unlocalized;
//		}
//		return localized;
	}
}
