package entity.organism;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganLiver extends Organ
{
	public OrganLiver(List<Organ> list)
	{
		super(list);
	}
	
	@Override
	public String getName()
	{
		return "Leber";
	}
	
	@Override
	public String getDescription()
	{
		return "";
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
