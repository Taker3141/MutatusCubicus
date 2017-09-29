package entity.organism;

import java.util.*;
import entity.Entity;
import entity.IEdible;
import entity.character.Player;
import entity.organism.Organ.OrganType;
import gui.overlay.OverlayOrganInfo;

public class Organism
{
	protected boolean boosting;
	protected float extraSlime = 0;
	protected Player p;
	
	public Map<OrganType, Organ> list = new HashMap<>();
	protected OrganHeart heart = new OrganHeart(list, this);
	protected OrganBrain brain = new OrganBrain(list, this);
	protected OrganShaper shaper = new OrganShaper(list, this);
	protected OrganLiver liver = new OrganLiver(200, 110, 100, 100, list, this);
	protected OrganDigestiveSystem digestive = new OrganDigestiveSystem(list, this);
	protected OrganSlime sime = new OrganSlime(list, this);
	public OverlayOrganInfo overlay;
	
	public Organism(Player player)
	{
		p = player;
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
		if(extraSlime > 0 && p.scale < p.NORMAL_SIZE * p.MAX_SIZE_FACTOR) {p.scale += 0.01F * delta; extraSlime -= 0.01F * delta;}
		else extraSlime = 0;
		boosting = boost && (liver.boost > 0);
	}
	
	public void addSlime(float slime)
	{
		extraSlime += slime;
	}
	
	public float getSpeed()
	{
		float standardSpeed = 3 + p.scale * 100;
		if(!boosting) return standardSpeed;
		else return standardSpeed * 10;
	}
	
	public float getJumpPower()
	{
		float standardJump = 3 + p.scale * 200;
		if(!boosting) return standardJump;
		else return standardJump * 2;
	}
	
	public float getEnergy()
	{
		return liver.energy;
	}
	
	public float getDigestion()
	{
		return digestive.digestion;
	}
	
	public Entity getEating()
	{
		return digestive.eating;
	}
	
	public float getBoost()
	{
		return liver.boost;
	}
}
