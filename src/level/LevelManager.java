package level;

import java.util.ArrayList;
import java.util.List;
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
		//TODO Implement parser for level base File
		sections.add(new Section("Das Mond-Labor"));
		sections.get(0).levels.add(new Level("Erstes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Zweites Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Drittes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Viertes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Fünftes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Sechstes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Siebtes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(0).levels.add(new Level("Achtes Level", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.add(new Section("Irgendwas"));
		sections.get(1).levels.add(new Level("1", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(1).levels.add(new Level("2", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
		sections.get(1).levels.add(new Level("3", MoonLabWorld.class, new Vector3f(1844, 21, 1623), true));
	}
	
	private void loadProgress()
	{
		
	}
}
