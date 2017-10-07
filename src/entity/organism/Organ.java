package entity.organism;

import entity.character.Player;

public abstract class Organ
{
	protected Organism o;
	public final OrganType type;
	
	public Organ(Organism organism, OrganType t)
	{
		type = t;
		o = organism;
	}
	
	public abstract String getName();
	public abstract String getDescription();
	
	public String[] getStatus()
	{
		return null;
	}
	
	public void loadModels(Player p)
	{
		
	}
	
	public void update(float delta, Player p)
	{
		
	}
	
	public enum OrganType
	{
		SLIME, LIVER, BRAIN, DIGESTIVE, SHAPER, HEART;
		
		public static String getTextureName(OrganType type)
		{
			final String path = "texture/cube/";
			switch(type)
			{
				case BRAIN: return path + "brain";
				case DIGESTIVE: return path + "intestines";
				case HEART:	return path + "heart";
				case LIVER: return path + "storage_cone";
				case SHAPER: return path + "shaper";
				case SLIME: return path + "outer_cube";				
			}
			return "";
		}
	}
}
