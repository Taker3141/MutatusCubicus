package entity.vehicle;

import java.util.List;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import entity.Movable;
import entity.SubEntity;
import renderer.Loader;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;

public class Rocketship extends Movable
{
	private static TexturedModel HULL;
	private static TexturedModel DECKS;
	private static TexturedModel ENGINE;
	
	public static void init()
	{
		Loader l = MainManagerClass.loader;
		HULL = new TexturedModel(OBJLoader.loadOBJModel("rocketship/rocketship"), new ModelTexture(l.loadTexture("texture/rocketship")));
		DECKS = new TexturedModel(OBJLoader.loadOBJModel("rocketship/decks"), new ModelTexture(l.loadTexture("texture/metal")));
		ENGINE = new TexturedModel(OBJLoader.loadOBJModel("rocketship/engine"), new ModelTexture(l.loadTexture("texture/color/red")));
	}
	
	public Rocketship(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(HULL, position, rotX, rotY, rotZ, 10, list, 1000000);
		new SubEntity(DECKS, new Vector3f(), 0, 0, 0, 1, list, this);
		new SubEntity(ENGINE, new Vector3f(), 0, 0, 0, 1, list, this);
	}
}
