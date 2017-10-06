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
	public OrganShaper(Organism organism)
	{
		super(organism, OrganType.SHAPER);
	}
	
	@Override
	public String[] getStatus()
	{
		return new String[]{"Schleimvolumen: " + Integer.toString((int)((100 * o.p.scale) / (o.p.MAX_SIZE_FACTOR * o.p.NORMAL_SIZE))) + "%"};
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
