package entity.organism;

import java.util.List;
import entity.character.Player;

public abstract class Organ
{
	protected Organism o;
	
	public Organ(List<Organ> list, Organism organism)
	{
		list.add(this);
		o = organism;
	}
	
	public abstract String getName();
	public abstract String getDescription();
	
	public void loadModels(Player p)
	{
		
	}
	
	public void update(float delta, Player p)
	{
		
	}
}
