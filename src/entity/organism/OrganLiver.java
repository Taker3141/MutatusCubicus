package entity.organism;

import org.lwjgl.util.vector.Vector3f;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganLiver extends Organ
{
	protected float energyCapacity;
	protected float energy;
	
	protected float boost;
	protected float boostCapacity;
	
	public static float[][] upgradeTable = new float[][]{{0, 0}, {20, 0}, {50, 0}, {100, 50}, {200, 100}};
	
	public OrganLiver(float energyCapacity, float boostCapacity, Organism organism)
	{
		super(organism, OrganType.LIVER);
		this.energyCapacity = energyCapacity;
		this.energy = energyCapacity;
		this.boostCapacity = boostCapacity;
		this.boost = boostCapacity;
	}
	
	public OrganLiver(int level, Organism organism)
	{
		this(upgradeTable[level][0], upgradeTable[level][1], organism);
	}
	
	@Override
	public void update(float delta, Player p)
	{
		energy -= delta / 10;
		if(energy < 0) energy = 0;
		if(energy > energyCapacity) energy = energyCapacity;
		if(energy < 20) p.dyingAnimation = 1 - (energy / 20);
		else p.dyingAnimation = 1.1F;
		
		if(boost > boostCapacity) boost = boostCapacity;
		if(o.boosting) boost -= delta * 10;
		if(boost < 0) boost = 0;
	}
	
	@Override
	public String[] getStatus()
	{
		return new String[]{"Energie: " + (int)energy + "/" + (int)energyCapacity, "Treibstoff: " + (int)boost + "/" + (int)boostCapacity};
	}
	
	@Override
	public String getName()
	{
		return "Leber";
	}
	
	@Override
	public String getDescription()
	{
		return "Die Leber ist eine bionische elektrochemische Batterie die große Energiemengen speichern kann. In ihr kann auch Raketentreibstoff eingelagert und seine Energie kontrolliert freigesetzt werden.";
	}
	
	@Override
	public void loadModels(Player p)
	{
		SubEntity liver = new SubEntity(World.createModel("liver", "texture/cube/storage_cone", 0.5F), new Vector3f(-5.8F, 6.18F, -6.18F), 0, 0, 0, 1, Entity.w.entities, p);
		Keyframe[] k = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 20F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 90, 0), 0, 20F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 180, 0), 0, 20F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 270, 0), 0, 20F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 360, 0), 0, 0.01F)
			};
		liver.a = new KeyframeAnimation(liver, k);
	}
}
