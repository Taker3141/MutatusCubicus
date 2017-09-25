package entity.organism;

import java.util.List;
import entity.character.Player;

public abstract class Organ
{
	public Organ(List<Organ> list)
	{
		list.add(this);
	}
	
	public abstract String getName();
	public abstract String getDescription();
	
	public void loadModels(Player p)
	{
		
	}
	
	public void update()
	{
		
	}
}
