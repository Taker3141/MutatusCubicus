package level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import world.World;

public class LevelManager
{
	public List<Section> sections = new ArrayList<>();
	
	public LevelManager()
	{
		loadProgress();
		loadLevels();
	}
	
	public class Section
	{
		public final String name;
		public List<Level> levels = new ArrayList<>();
		
		private Section(String name)
		{
			this.name = name;
		}
	}
	
	private void loadLevels()
	{
		String levelPath = "res/level/";
		File sectionIndexFile = new File(levelPath + "sections");
		Map<String, List<String>> sectionNames = loadSections(sectionIndexFile);
		loadLevelNames(sectionNames, levelPath);
		String[] names = sectionNames.keySet().toArray(new String[0]);
		Arrays.sort(names);
		for(String n : names)
		{
			Section sec = new Section(n.split(":")[1]);
			sections.add(sec);
			List<String> levelNames = sectionNames.get(n);
			for(String l : levelNames)
			{
				sec.levels.add(loadLevelInfo(levelPath + n.split(":")[1] + "/" + l + ".lvl"));
			}
		}
	}

	private Level loadLevelInfo(String levelPath)
	{
		String name = null;
		Class<? extends World> startWorld = null;
		try (FileReader r = new FileReader(levelPath); BufferedReader reader = new BufferedReader(r))
		{
			String line = reader.readLine();
			boolean foundInfoSection = false;
			while(line != null)
			{
				if(!foundInfoSection && line.startsWith("info:")) foundInfoSection = true;
				if(foundInfoSection)
				{
					if(line.trim().startsWith("name:")) name = line.split(":")[1].trim();
					if(line.trim().startsWith("world:")) 
					{
						@SuppressWarnings("unchecked")
						Class<? extends World> c = (Class<? extends World>) Class.forName("world." + line.split(":")[1].trim());
						startWorld = c;
					}
					if(line.trim().startsWith("}")) break;
				}
				line = reader.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println("Could not read level information for level \'" + levelPath + "\'");
			e.printStackTrace();
		}
		return new Level(name, startWorld, true, levelPath);
	}
	
	private void loadLevelNames(Map<String, List<String>> sectionNames, String levelPath)
	{
		String[] nameArray = sectionNames.keySet().toArray(new String[0]);
		for (int i = 0; i < sectionNames.size(); i++)
		{
			String n = nameArray[i].split(":")[1];
			try (FileReader r = new FileReader(levelPath + n + "/section_info"); BufferedReader reader = new BufferedReader(r))
			{
				String line = reader.readLine();
				while (line != null)
				{
					if(line.startsWith("level:"))
					{
						sectionNames.get(nameArray[i]).add(line.split(":")[1].trim());
					}
					line = reader.readLine();
				}
			}
			catch (Exception e)
			{
				System.out.println("Could not read section info file for section \'" + n + "\'");
				e.printStackTrace();
			}
		}
	}

	private Map<String, List<String>> loadSections(File sectionIndexFile)
	{
		Map<String, List<String>> sectionNames = new HashMap<>();
		try (FileReader r = new FileReader(sectionIndexFile); BufferedReader reader = new BufferedReader(r))
		{
			String line = reader.readLine();
			int lineCounter = 0;
			while (line != null)
			{
				sectionNames.put(lineCounter + ":" + line, new ArrayList<String>());
				lineCounter++;
				line = reader.readLine();
			}
		}
		catch (Exception e)
		{
			System.out.println("Could not read level section index file");
			e.printStackTrace();
		}
		return sectionNames;
	}

	private void loadProgress()
	{	
		
	}
}
