package entity.organism;

import java.util.ArrayList;
import java.util.List;
import entity.Entity;
import entity.IEdible;
import entity.character.Player;

public class Organism
{
	protected boolean boosting;
	private float extraSlime = 0;
	private Player p;
	
	protected List<Organ> list = new ArrayList<>();
	protected OrganHeart heart = new OrganHeart(list, this);
	protected OrganBrain brain = new OrganBrain(list, this);
	protected OrganShaper shaper = new OrganShaper(list, this);
	protected OrganLiver liver = new OrganLiver(100, 110, 100, 100, list, this);
	protected OrganDigestiveSystem digestive = new OrganDigestiveSystem(list, this);
	protected OrganSlime sime = new OrganSlime(list, this);
	
	public Organism(Player player)
	{
		p = player;
		for(Organ o : list) o.loadModels(p);
	}
	
	public void eat(IEdible f)
	{
		digestive.eat(f, p);
	}
	
	public void update(float delta, boolean boost)
	{
		for(Organ o : list) o.update(delta, p);
		p.organs.update();
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
