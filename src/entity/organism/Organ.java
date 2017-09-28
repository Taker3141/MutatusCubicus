package entity.organism;

import java.util.Map;
import entity.character.Player;

public abstract class Organ
{
	protected Organism o;
	public final OrganType type;
	
	public Organ(Map<OrganType, Organ> list, Organism organism, OrganType t)
	{
		type = t;
		list.put(type, this);
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
	
	public enum OrganType
	{
		SLIME, LIVER, BRAIN, DIGESTIVE, SHAPER, HEART;
	}
}
