package entity.organism;

import org.lwjgl.util.vector.Vector3f;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganShaper extends Organ
{
	protected float extraSlime = 0;
	protected final int level;
	protected final float[] SIZE_TABLE;
	
	public OrganShaper(int level, Organism organism)
	{
		super(organism, OrganType.SHAPER);
		this.level = level;
		final float normal = organism.p.NORMAL_SIZE, slime = 0.005F;
		SIZE_TABLE = new float[]{normal, normal, normal + slime * 3, normal + slime * 5, normal + slime * 10, normal + slime * 20};
	}
	
	@Override
	public void update(float delta, Player p)
	{
		if(extraSlime > 0 && p.scale < SIZE_TABLE[level]) {p.scale += 0.01F * delta; extraSlime -= 0.01F * delta;}
		else extraSlime = 0;
	}
	
	public void addSlime(float slime)
	{
		extraSlime += slime;
	}
	
	public float getMaxSize()
	{
		return SIZE_TABLE[level];
	}
	
	@Override
	public String[] getStatus()
	{
		return new String[]{"Schleimvolumen: " + Integer.toString((int)((100 * o.p.scale) / (SIZE_TABLE[level]))) + "%"};
	}
	
	@Override
	public String getName()
	{
		return "Shaper";
	}
	
	@Override
	public String getDescription()
	{
		return "Der Shaper sorgt durch Erzeugen von Mikrovibrationen im Schleim dafür, dass dieser seine Form beibehält und der Organismus sich fortbewegen kann.";
	}
	
	@Override
	public void loadModels(Player p)
	{
		SubEntity shaper = new SubEntity(World.createModel("shaper", "texture/cube/shaper", 0.5F), new Vector3f(5.47F, 6.76F, 3.12F), 0, 0, 0, 1, Entity.w.entities, p);
		Keyframe[] k = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.2F),
				new Keyframe(new Vector3f(0, 0.2F, 0), new Vector3f(0, 0, 0), 0, 0.2F),
				new Keyframe(new Vector3f(0, 0.2F, 0.2F), new Vector3f(0, 0, 0), 0, 0.2F),
				new Keyframe(new Vector3f(0, 0, 0.2F), new Vector3f(0, 0, 0), 0, 0.2F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.01F),
			};
		shaper.a = new KeyframeAnimation(shaper, k);
	}
}
