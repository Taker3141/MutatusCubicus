package entity.organism;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import entity.Entity;
import entity.IEdible;
import entity.character.Player;

public class Organism
{
	private float digestion = 0;
	private float energy = 110;
	private float boost = 100;
	private boolean boosting;
	private IEdible food;
	private Entity eating;
	private float extraSlime = 0;
	private Player p;
	
	private OrganHeart heart = new OrganHeart();
	
	public Organism(Player player)
	{
		p = player;
		heart.loadModels(p);
	}
	
	public void eat(IEdible f)
	{
		if(f instanceof Entity) eating = (Entity)f;
		food = (IEdible)f;
		digestion = food.getAmmount() < 100 ? food.getAmmount() : 100;
		p.organs.setDigestingTexture(eating.model.getTexture().getID());
	}
	
	public void update(float delta, boolean boost)
	{
		energy -= delta / 50;
		digest(delta);
		if(energy < 0) energy = 0;
		if(energy > 200) energy = 200;
		if(energy < 20) p.dyingAnimation = 1 - (energy / 20);
		else p.dyingAnimation = 1.1F;
		if(extraSlime > 0 && p.scale < p.NORMAL_SIZE * p.MAX_SIZE_FACTOR) {p.scale += 0.01F * delta; extraSlime -= 0.01F * delta;}
		else extraSlime = 0;
		boosting = boost && (this.boost > 0);
		if(boosting) this.boost -= delta * 10;
		if(this.boost < 0) this.boost = 0;
	}
	
	private void digest(float delta)
	{
		if(food != null)
		{
			digestion -= food.getType().digestPerSecond * delta;
			energy += (food.getEnergy() / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
			if(food.getType() == IEdible.FoodType.FUEL)
			{
				boost += (50 / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
				if(boost > 100) boost = 100;
			}
			if(digestion < 0) 
			{
				digestion = 0;
				p.inv.addItem(food.getItem());
				food = null;
			}
		}
		p.organs.update();
		if(eating != null)
		{
			eating.position = new Vector3f(p.position);
			eating.position.y = p.position.y + 10 * p.scale;
			if(eating.scale > 0) eating.scale -= DisplayManager.getFrameTimeSeconds() * 0.1F;
			else {eating.unregister(); eating = null;}
		}
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
		return energy;
	}
	
	public float getDigestion()
	{
		return digestion;
	}
	
	public Entity getEating()
	{
		return eating;
	}
	
	public float getBoost()
	{
		return boost;
	}
}
