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
		
		levels.put(LIVER, 1);
		Organ[] livers = new Organ[5];
		for(int i = 0; i < 5; i++) livers[i] = new OrganLiver(i, organism);
		addOrgans(LIVER, livers);
		
		levels.put(BRAIN, 0);
		addOrgans(BRAIN, new OrganBrain(organism));
		
		levels.put(DIGESTIVE, 0);
		addOrgans(DIGESTIVE, new OrganDigestiveSystem(organism));
		
		levels.put(SHAPER, 0);
		addOrgans(SHAPER, new OrganShaper(organism));
		
		levels.put(HEART, 0);
		addOrgans(HEART, new OrganHeart(organism));
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
