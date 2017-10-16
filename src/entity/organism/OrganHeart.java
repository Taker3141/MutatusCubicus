package entity.organism;

import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganHeart extends Organ
{
	public final float powerFactor;
	
	public OrganHeart(int level, Organism organism)
	{
		super(organism, OrganType.HEART);
		powerFactor = level > 0 ? level * 0.5F + 0.5F : 0;
	}
	
	@Override
	public String getName()
	{
		return "Herz";
	}
	
	@Override
	public String getDescription()
	{
		return "Das aus einem Doppeltorus bestehende Herz transportiert durch Kontraktionen energiereiche Stoffe durch Kanäle im Schleim zu den anderen Organen.";
	}
	
	@Override
	public String[] getStatus()
	{
		return new String[]{"Kraft-Faktor: x" + powerFactor};
	}
	
	@Override
	public void loadModels(Player p)
	{
		TexturedModel heart = World.createModel("cube/heart", "texture/cube/heart", 0.5F);
		SubEntity heart1 = new SubEntity(heart, new Vector3f(-0.35F, 10, -2.58F), 0, 0, 0, 1, Entity.w.entities, p);
		SubEntity heart2 = new SubEntity(heart, new Vector3f(-2.33F, 10, -2.58F), 90, 0, 0, 1, Entity.w.entities, p);
		Keyframe[] k1 = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.2F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.1F), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1F, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.8F)
			};
		heart1.a = new KeyframeAnimation(heart1, k1);
		Keyframe[] k2 = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.1F), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1F, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
			};
		heart2.a = new KeyframeAnimation(heart2, k2);
	}
}
