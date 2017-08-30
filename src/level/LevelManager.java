package level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;
import world.MoonLabWorld;

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
		List<String> sectionNames = new ArrayList<>();
		try (FileReader r = new FileReader(sectionIndexFile); BufferedReader reader = new BufferedReader(r))
		{
			String line = reader.readLine();
			while (line != null)
			{
				sectionNames.add(line);
				line = reader.readLine();
			}
		}
		catch (Exception e)
		{
			System.out.println("Could not read level section index file");
			e.printStackTrace();
		}
		for (String n : sectionNames)
		{
			sections.add(new Section(n));
			try (FileReader r = new FileReader(levelPath + n + "/section_info"); BufferedReader reader = new BufferedReader(r))
			{
				String line = reader.readLine();
				while (line != null)
				{
					if(line.startsWith("level:"))
					{
						sections.get(sections.size() - 1).levels.add(new Level(line.split(":")[1].trim(), MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
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
	
	private void loadProgress()
	{	
		
	}
}
