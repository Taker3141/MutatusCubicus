package entity.organism;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganShaper extends Organ
{
	public OrganShaper(List<Organ> list, Organism organism)
	{
		super(list, organism, OrganType.SHAPER);
	}
	
	@Override
	public String getName()
	{
		return "Shaper";
	}
	
	@Override
	public String getDescription()
	{
		return "";
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
