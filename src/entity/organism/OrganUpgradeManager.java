package entity.organism;

import java.util.HashMap;
import java.util.Map;
import entity.organism.Organ.OrganType;
import static entity.organism.Organ.OrganType.*;

public class OrganUpgradeManager
{
	private Map<OrganType, Organ[]> organLevels = new HashMap<>();
	private Map<OrganType, Integer> levels = new HashMap<>();
		
	public OrganUpgradeManager(Organism organism)
	{
		levels.put(SLIME, 0);
		addOrgans(SLIME, new OrganSlime(organism));
		
		levels.put(LIVER, 4);
		Organ[] livers = new Organ[5];
		for(int i = 0; i < 5; i++) livers[i] = new OrganLiver(i, organism);
		addOrgans(LIVER, livers);
		
		levels.put(BRAIN, 0);
		addOrgans(BRAIN, new OrganBrain(organism));
		
		levels.put(DIGESTIVE, 5);
		Organ[] digestiveSystems = new Organ[6];
		for(int i = 0; i < 6; i++) digestiveSystems[i] = new OrganDigestiveSystem(i, organism);
		addOrgans(DIGESTIVE, digestiveSystems);
		
		levels.put(SHAPER, 0);
		addOrgans(SHAPER, new OrganShaper(organism));
		
		levels.put(HEART, 3);
		Organ[] hearts = new Organ[4];
		for(int i = 0; i < 4; i++) hearts[i] = new OrganHeart(i, organism);
		addOrgans(HEART, hearts);
	}
	
	public int getLevel(OrganType type)
	{
		return levels.get(type);
	}
	
	public int getMaxLevel(OrganType type)
	{
		return organLevels.get(type).length - 1;
	}
	
	public Organ getOrganCurrentLevel(OrganType type)
	{
		return organLevels.get(type)[levels.get(type)];
	}
	
	public Organ[] getOrganOfType(OrganType type)
	{
		return organLevels.get(type);
	}
	
	private void addOrgans(OrganType type, Organ... organs)
	{
		organLevels.put(type, organs);
	}
}
