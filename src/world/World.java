package world;

import static org.lwjgl.input.Keyboard.*;
import java.util.ArrayList;
import java.util.List;
import main.MainGameLoop;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import animation.KeyframeAnimation;
import static animation.KeyframeAnimation.Keyframe;
import raycasting.Raycaster;
import renderer.*;
import renderer.fbo.PostProcessing;
import renderer.models.TexturedModel;
import renderer.textures.*;
import terrain.Terrain;
import entity.*;

public class World
{
	private Raycaster ray;
	public List<Entity> entities;
	private Player player;
	private List<Light> lights = new ArrayList<Light>();
	private Camera c;
	private Terrain[] t;
	private MasterRenderer renderer;
	private Loader loader = MainManagerClass.loader;
	private Input input;
	
	public World()
	{
		Particle.init();
		entities = new ArrayList<Entity>();
		{
			TexturedModel model = new TexturedModel(OBJLoader.loadOBJModel("outer_cube"), new ModelTexture(loader.loadTexture("texture/cube/outer_cube"), true));
			player = new Player(model, new Vector3f(101, 0, 101), 0, 0, 0, 0.05F, entities, 30);
			
			new Organ(createModel("brain", "texture/cube/brain"), new Vector3f(5, 12.87F, -4), 0, 0, 0, 1, entities, player);
			{
				TexturedModel heart = createModel("heart", "texture/cube/heart");
				Organ heart1 = new Organ(heart, new Vector3f(-0.35F, 10, -2.58F), 0, 0, 0, 1, entities, player);
				Organ heart2 = new Organ(heart, new Vector3f(-2.33F, 10, -2.58F), 90, 0, 0, 1, entities, player);
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
			{
				Organ shaper = new Organ(createModel("shaper", "texture/cube/shaper"), new Vector3f(5.47F, 6.76F, 3.12F), 0, 0, 0, 1, entities, player);
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
			ModelTexture digestive = new ModelTexture(loader.loadTexture("texture/cube/intestines"));			
			TexturedModel upperIntestine = new TexturedModel(OBJLoader.loadOBJModel("upper_intestine"), digestive);
			new Organ(upperIntestine, new Vector3f(-2.97F, 5.9F, 3.42F), 0, 0, 0, 1, entities, player);
			{
				Organ liver = new Organ(createModel("liver", "texture/cube/storage_cone"), new Vector3f(-5.8F, 6.18F, -6.18F), 0, 0, 0, 1, entities, player);
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
			TexturedModel lowerIntestine = new TexturedModel(OBJLoader.loadOBJModel("lower_intestine"), digestive);
			new Organ(lowerIntestine, new Vector3f(-2.7F, 7.56F, 3.42F), 0, 0, 0, 1, entities, player);
			{
				TexturedModel stomachModel = new TexturedModel(OBJLoader.loadOBJModel("stomach"), digestive);
				Organ stomach = new Organ(stomachModel, new Vector3f(-2.97F, 9.2F, 3.42F), 0, 0, 0, 1, entities, player);
				Keyframe[] k = 
					{
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1), 
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.2F, 2), 
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
					};
				stomach.a = new KeyframeAnimation(stomach, k);
			}
			
			TexturedModel veins = createModel("veins", "texture/cube/veins");
			new Organ(veins, new Vector3f(-4.93F, 7.37F, -2.71F), -12.01F, 15.67F, 0, 1, entities, player);
			new Organ(veins, new Vector3f(-2.48F, 9.43F, 1.48F), 14.2F, -4.72F, 0, 0.8F, entities, player);
			new Organ(veins, new Vector3f(5.31F, 9.28F, 0.79F), 36.21F, 0, 0, 1, entities, player);
			new Organ(veins, new Vector3f(3.03F, 8.7F, 0.27F), 30.38F, 45, 0, 1, entities, player);
			new Organ(veins, new Vector3f(2.17F, 12.21F, -2.38F), 36.21F, -94.14F, 0, 0.7F, entities, player);
		}
		lights.add(new Light(new Vector3f(100, 100, 0), new Vector3f(1, 1, 1)));
		c = new Camera(player);
		t = new Terrain[2];
		TerrainTexturePack pack = loadTerrainTexturePack(loader);
		t[0] = new Terrain(0, 0, loader, pack, new TerrainTexture(loader.loadTexture("texture/blend_map")), "height_map");
		ray = new Raycaster(player);
		ray.setList(entities);
		input = new Input(Display.getHeight());
		renderer = new MasterRenderer();
	}
	
	public boolean tick()
	{
		input.poll(Display.getWidth(), Display.getHeight());
		player.update(terrain(player.position.x));
		c.update();
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(terrain(e.position.x));
			if(!e.invisible) renderer.processEntities(e);
		}
		lights.get(0).position = new Vector3f(player.position.x + 100, player.position.y + 100, player.position.z);
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		
		MainGameLoop.fbo.bindFrameBuffer();
		renderer.processTerrain(t[0]);
		renderer.render(lights, c);
		MainGameLoop.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(MainGameLoop.fbo.getColorTexture());

		if(isKeyDown(KEY_ESCAPE)) return false;
		if(isKeyDown(KEY_F2)) PostProcessing.effects.warp();
		
		if(isKeyDown(KEY_F5)) t[0] = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map")), "height_map");
		return true;
	}
	
	private TexturedModel createModel(String modelName, String textureName)
	{
		return new TexturedModel(OBJLoader.loadOBJModel(modelName), new ModelTexture(loader.loadTexture(textureName), false, 0.5F));
	}
	
	public float height(float x, float z)
	{
		return terrain(x).getHeight(x, z);
	}
	
	public Terrain terrain(float positionX)
	{
		return t[0];
	}
	
	public void cleanUp()
	{
		renderer.cleanUp();
		loader.cleanUp();
	}

	private TerrainTexturePack loadTerrainTexturePack(Loader loader)
	{
		TerrainTexture back = new TerrainTexture(loader.loadTexture("texture/moon_dust"));
		TerrainTexture r = new TerrainTexture(loader.loadTexture("texture/moon_crater"));
		TerrainTexture g = new TerrainTexture(loader.loadTexture("texture/grass"));
		TerrainTexture b = new TerrainTexture(loader.loadTexture("texture/moon_path"));
		return new TerrainTexturePack(back, r, g, b);
	}
	
	public void updateRaycaster()
	{
		ray.setList(entities);
	}
}
