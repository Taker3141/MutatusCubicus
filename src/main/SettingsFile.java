package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsFile
{
	private File file;
	public String language = "en_US";
	public int resolutionX = 1280;
	public int resolutionY = 720;
	public boolean fullscreen = false;
	public boolean music = true;
	
	public SettingsFile(String path)
	{
		file = new File(path);
		if(!file.exists())
		{
			createFile();
		}
		readFile();
	}
	
	private void createFile()
	{
		try
		{
			file.getParentFile().mkdirs(); 
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
			writer.write("language=en_US\n");
			writer.write("resolution=1280x720\n");
			writer.write("fullscreen=false\n");
			writer.write("music=true\n");
			writer.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not create settings file!");
			e.printStackTrace();
		}
	}
	
	private void readFile()
	{
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			while(line != null)
			{
				String[] part = line.split("=");
				switch(part[0])
				{
					case "language" : language = part[1]; break;
					case "resolution" : 
					{
						resolutionX = Integer.parseInt(part[1].split("x")[0]); 
						resolutionY = Integer.parseInt(part[1].split("x")[1]);
						break;
					}
					case "fullscreen" : fullscreen = Boolean.parseBoolean(part[1]); break;
					case "music" : music = Boolean.parseBoolean(part[1]);
				}
				line = reader.readLine();
			}
			reader.close();
			fr.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not read settings file!");
			e.printStackTrace();
		}
	}
	
	public void writeFile()
	{
		try
		{
			PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
			writer.write("language=" + language +"\n");
			writer.write("resolution=" + resolutionX + "x" + resolutionY + "\n");
			writer.write("fullscreen=" + fullscreen + "\n");
			writer.write("music=" + music + "\n");
			writer.close();
			MainManagerClass.update();
		}
		catch (IOException e)
		{
			System.out.println("Could not write settings file!");
			e.printStackTrace();
		}
	}
}
