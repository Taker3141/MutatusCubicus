package entity.organism;

import java.util.List;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganDigestiveSystem extends Organ
{
	
	public OrganDigestiveSystem(List<Organ> list)
	{
		super(list);
	}
	
	@Override
	public String getName()
	{
		return "Verdauungssystem";
	}
	
	@Override
	public String getDescription()
	{
		return "";
	}
	
	@Override
	public void loadModels(Player p)
	{
		ModelTexture digestive = new ModelTexture(MainManagerClass.loader.loadTexture("texture/cube/intestines"));			
		TexturedModel upperIntestine = new TexturedModel(OBJLoader.loadOBJModel("upper_intestine"), digestive);
		new SubEntity(upperIntestine, new Vector3f(-2.97F, 5.9F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
		TexturedModel lowerIntestine = new TexturedModel(OBJLoader.loadOBJModel("lower_intestine"), digestive);
		new SubEntity(lowerIntestine, new Vector3f(-2.7F, 7.56F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
		TexturedModel stomachModel = new TexturedModel(OBJLoader.loadOBJModel("stomach"), digestive);
		SubEntity stomach = new SubEntity(stomachModel, new Vector3f(-2.97F, 9.2F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
		Keyframe[] k = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.2F, 2), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
			};
		stomach.a = new KeyframeAnimation(stomach, k);
	}
}
