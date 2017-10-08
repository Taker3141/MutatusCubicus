package entity.organism;

import java.util.*;
import entity.Entity;
import entity.IEdible;
import entity.character.Player;
import entity.organism.Organ.OrganType;
import gui.overlay.OverlayOrganInfo;
import static entity.organism.Organ.OrganType.*;

public class Organism
{
	protected boolean boosting;
	protected Player p;
	
	public OrganUpgradeManager upgrade;
	public Map<OrganType, Organ> list = new HashMap<>();
	protected OrganHeart heart;
	protected OrganBrain brain;
	protected OrganShaper shaper;
	protected OrganLiver liver;
	protected OrganDigestiveSystem digestive;
	protected OrganSlime slime;
	public OverlayOrganInfo overlay;
	
	public Organism(Player player)
	{
		p = player;
		upgrade = new OrganUpgradeManager(this);
		heart = (OrganHeart)upgrade.getOrganCurrentLevel(HEART);
		brain = (OrganBrain)upgrade.getOrganCurrentLevel(BRAIN);
		shaper = (OrganShaper)upgrade.getOrganCurrentLevel(SHAPER);
		liver = (OrganLiver)upgrade.getOrganCurrentLevel(LIVER);
		digestive = (OrganDigestiveSystem)upgrade.getOrganCurrentLevel(DIGESTIVE);
		slime = (OrganSlime)upgrade.getOrganCurrentLevel(SLIME);
		addOrgans(heart, brain, shaper, liver, digestive, slime);
		overlay = new OverlayOrganInfo(this);
		overlay.setVisible(false);
		Entity.w.overlays.add(overlay);
		for(Map.Entry<OrganType, Organ> entry : list.entrySet()) entry.getValue().loadModels(p);
	}
	
	public void eat(IEdible f)
	{
		digestive.eat(f, p);
	}
	
	public void update(float delta, boolean boost)
	{
		for(Map.Entry<OrganType, Organ> entry : list.entrySet()) entry.getValue().update(delta, p);
		p.organs.update();
		overlay.update();
		boosting = boost && (liver.boost > 0);
	}
	
	public float getSpeed()
	{
		float standardSpeed = 3 + p.scale * 100 * heart.powerFactor;
		if(!boosting) return standardSpeed;
		else return standardSpeed * 10;
	}
	
	public float getJumpPower()
	{
		float standardJump = 3 + p.scale * 200 * heart.powerFactor;
		if(!boosting) return standardJump;
		else return standardJump * 2;
	}
	
	public boolean canDigest(IEdible.FoodType testFood) {return digestive.canDigest(testFood);}	
	public void addSlime(float slime) {shaper.addSlime(slime);}
	public float getMaxSize() {return shaper.getMaxSize();}
	public float getEnergy() {return liver.energy;}
	public float getMaxEnergy() {return liver.energyCapacity;}
	public float getDigestion() {return digestive.digestion;}
	public float getStomachCapacity() {return digestive.capacity;}
	public Entity getEating() {return digestive.eating;}
	public float getBoost() {return liver.boost;}
	private void addOrgans(Organ... organs) {for(Organ org : organs) list.put(org.type, org);}
}
