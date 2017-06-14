package world;

import static org.lwjgl.input.Keyboard.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.MainGameLoop;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import animation.KeyframeAnimation;
import static animation.KeyframeAnimation.Keyframe;
import raycasting.AABB;
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
			player = new Player(model, new Vector3f(1164, 0, 1808), 0, 180, 0, 0.02F, entities);
			
			new SubEntity(createModel("brain", "texture/cube/brain", 0.5F), new Vector3f(5, 12.87F, -4), 0, 0, 0, 1, entities, player);
			{
				TexturedModel heart = createModel("heart", "texture/cube/heart", 0.5F);
				SubEntity heart1 = new SubEntity(heart, new Vector3f(-0.35F, 10, -2.58F), 0, 0, 0, 1, entities, player);
				SubEntity heart2 = new SubEntity(heart, new Vector3f(-2.33F, 10, -2.58F), 90, 0, 0, 1, entities, player);
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
				SubEntity shaper = new SubEntity(createModel("shaper", "texture/cube/shaper", 0.5F), new Vector3f(5.47F, 6.76F, 3.12F), 0, 0, 0, 1, entities, player);
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
			new SubEntity(upperIntestine, new Vector3f(-2.97F, 5.9F, 3.42F), 0, 0, 0, 1, entities, player);
			{
				SubEntity liver = new SubEntity(createModel("liver", "texture/cube/storage_cone", 0.5F), new Vector3f(-5.8F, 6.18F, -6.18F), 0, 0, 0, 1, entities, player);
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
			new SubEntity(lowerIntestine, new Vector3f(-2.7F, 7.56F, 3.42F), 0, 0, 0, 1, entities, player);
			{
				TexturedModel stomachModel = new TexturedModel(OBJLoader.loadOBJModel("stomach"), digestive);
				SubEntity stomach = new SubEntity(stomachModel, new Vector3f(-2.97F, 9.2F, 3.42F), 0, 0, 0, 1, entities, player);
				Keyframe[] k = 
					{
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1), 
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.2F, 2), 
						new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
					};
				stomach.a = new KeyframeAnimation(stomach, k);
			}
			
			TexturedModel veins = createModel("veins", "texture/cube/veins", 0.5F);
			new SubEntity(veins, new Vector3f(-4.93F, 7.37F, -2.71F), -12.01F, 15.67F, 0, 1, entities, player);
			new SubEntity(veins, new Vector3f(-2.48F, 9.43F, 1.48F), 14.2F, -4.72F, 0, 0.8F, entities, player);
			new SubEntity(veins, new Vector3f(5.31F, 9.28F, 0.79F), 36.21F, 0, 0, 1, entities, player);
			new SubEntity(veins, new Vector3f(3.03F, 8.7F, 0.27F), 30.38F, 45, 0, 1, entities, player);
			new SubEntity(veins, new Vector3f(2.17F, 12.21F, -2.38F), 36.21F, -94.14F, 0, 0.7F, entities, player);
		}
		lights.add(new Light(new Vector3f(0, 100000, 100000), new Vector3f(1, 1, 1)));
		lights.add(new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0.6F, 0), new Vector3f(1, 0.01F, 0.2F)));
		lights.add(new PulsatingLight(new Vector3f(1261, 40, 1955), new Vector3f(2F, 0.1F, 1.8F), new Vector3f(1, 0.01F, 0.002F), 2));
		c = new Camera(player);
		t = new Terrain[3 * 3];
		TerrainTexturePack pack = loadTerrainTexturePack(loader);
		for (int i = 0; i < 9; i++)
		{
			int tx = i % 3; int tz = i / 3;
			t[i] = new Terrain(tx, 2 - tz, loader, pack, 
					new TerrainTexture(loader.loadTexture((tx == 1 && tz == 1) ? "texture/terrain/blend_1_1" : "texture/terrain/blend_0_0")), 
					"terrain/height_" + tx + "_" + tz);
		}
		new Vehicle(hVector(player.position.x + 10, player.position.z), 0, 0, 0, 0.6F, entities, 1000);
		Entity rocketship = new Entity(createModel("rocketship", "texture/rocketship", 0.1F), hVector(1135, 1700), 0, -90, 0, 10, entities);
		Entity engine = new SubEntity(createModel("rocket_engine", "texture/color/red", 0.1F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, rocketship);
		Entity decks = new SubEntity(createModel("decks", "texture/metal", 0.1F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, rocketship);
		Entity rocketship2 = new Entity(rocketship.model, hVector(1250, 1700), 0, 180, 0, 10, entities);
		new SubEntity(engine.model, new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, rocketship2);
		new SubEntity(decks.model, new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, rocketship2);
		{
			Random r = new Random();
			TexturedModel rock = createModel("rock", "texture/moon_dust", 0.1F);
			for(int i = 0; i < 100; i++)
			{
				Vector3f position = new Vector3f(250 * r.nextFloat(), 0, 250 * r.nextFloat());
				position.y = height(position.x, position.z);
				new Rock(rock, position, r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.04F + r.nextFloat() / 2.5F, entities);
			}
			
			TexturedModel waste = createModel("waste", "texture/waste", 0.05F);
			for(int i = 0; i < 100; i++)
			{
				Vector3f position = new Vector3f(20 * r.nextFloat(), 0, 20 * r.nextFloat());
				position.y = height(position.x, position.z) + 0.2F;
				new Waste(waste, position, r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.24F + r.nextFloat() * 0.08F, entities);
			}
			
			Entity reactor = new Entity(createModel("reactor/reactor_building", "texture/concrete", 0), new Vector3f(1062, height(1062, 2004), 2004), 0, 90, 0, 50, entities);
			new SubEntity(createModel("reactor/reactor_decoration", "texture/reactor_decoration", 0.5F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/plasma_chamber", "texture/plasma_chamber", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/turbine", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/electrolyse_chamber", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/isotope_generator", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(-0.09F, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(-0.18F, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/hydrogen_tube1", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/hydrogen_tube2", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/heavy_hydrogen_tube", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/helium_tube", "texture/color/yellow", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/steam_tube1", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			new SubEntity(createModel("reactor/steam_tube2", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, reactor);
			{
				TexturedModel energyCell = createModel("reactor/energy_cell", "texture/color/red", 0);
				for(int i = 0; i < 11; i++)
				{
					for(int j = 0; j < 6; j++)
					{
						new Entity(energyCell, hVector(1310 + i * 8, 1940 + j * 8), 0, 0, 0, 2, entities);
					}
				}
			}
			{
				FuelGenerator fuelGenerator = new FuelGenerator(hVector(1510, 1936), 0, 0, 0, entities);
				fuelGenerator.setHitBox(new AABB(new Vector3f(fuelGenerator.position), new Vector3f(7, 8, 10), new Vector3f(-2, 0, -5)));
				SubEntity pipe1 = new SubEntity(createModel("fuel_generator/pipe1", "texture/metal", 0.2F), new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator);
				SubEntity pipe2 = new SubEntity(createModel("fuel_generator/pipe2", "texture/metal", 0.2F), new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator);
				SubEntity pipe3 = new SubEntity(createModel("fuel_generator/pipe3", "texture/metal", 0.2F), new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator);
				SubEntity out = new SubEntity(createModel("fuel_generator/output", "texture/color/red", 0), new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, fuelGenerator);
				SubEntity box1 = new SubEntity(createModel("box", "texture/nitrogen_box", 0), new Vector3f(0, 0, 2), 0, 0, 0, 1, entities, fuelGenerator);
				SubEntity box2 = new SubEntity(createModel("box", "texture/benzene_box", 0), new Vector3f(2, 0, 1), 0, 0, 0, 1, entities, fuelGenerator);
				FuelGenerator fuelGenerator2 = new FuelGenerator(hVector(1520, 1936), 0, 0, 0, entities);
				fuelGenerator2.setHitBox(new AABB(new Vector3f(fuelGenerator2.position), new Vector3f(7, 8, 10), new Vector3f(-2, 0, -5)));
				new SubEntity(pipe1.model, new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator2);
				new SubEntity(pipe2.model, new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator2);
				new SubEntity(pipe3.model, new Vector3f(), 0, 0, 0, 1, entities, fuelGenerator2);
				new SubEntity(out.model, new Vector3f(0, 0, 0), 0, 0, 0, 1, entities, fuelGenerator2);
				new SubEntity(box1.model, new Vector3f(0, 0, 2), 0, 0, 0, 1, entities, fuelGenerator2);
				new SubEntity(box2.model, new Vector3f(2, 0, 1), 0, 0, 0, 1, entities, fuelGenerator2);
				for(int i = 0; i < 10; i++)
				{
					new Rock(rock, hVector(1525 + r.nextFloat() * 20, 1967 + r.nextFloat() * 20), r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.3F + r.nextFloat() / 10F, entities);
					Entity b1 = new Entity(box1.model, hVector(1468 + r.nextFloat() * 20, 1949 + r.nextFloat() * 20), 0, r.nextFloat() * 360, 0, 2, entities);
					b1.setHitBox(new AABB(b1.position, new Vector3f(2, 2, 2), new Vector3f(-1, 0, -1)));
					Entity b2 = new Entity(box2.model, hVector(1480 + r.nextFloat() * 20, 1913 + r.nextFloat() * 20), 0, r.nextFloat() * 360, 0, 2, entities);
					b2.setHitBox(new AABB(b2.position, new Vector3f(2, 2, 2), new Vector3f(-1, 0, -1)));
				}
			}
			new Entity(createModel("antenna", "texture/metal", 0.4F), new Vector3f(1184, height(1184, 1224), 1224), 0, 45, 0, 50, entities);
			Entity biosphere = new Entity(createModel("biosphere_outside", "texture/glass", 0.2F), new Vector3f(1194, height(1194, 1436) - 10, 1436), 0, 0, 0, 120, entities);
			biosphere.model.getTexture().setHasTransparency(true);
			biosphere.model.transparencyNumber = 1;
			SubEntity biosphereInside = new SubEntity(createModel("biosphere_inside", "texture/glass", 0.2F), new Vector3f(), 0, 0, 0, 1, entities, biosphere);
			biosphereInside.model.getTexture().setHasTransparency(true);
			biosphereInside.model.transparencyNumber = 1;
			TexturedModel grass = createModel("grass", "texture/plant/grass", 0);
			grass.getTexture().setUseFakeLightning(true);
			generateDecoration(grass, 100, biosphere.position.x, biosphere.position.z, 0.5F, 1.5F, 110, true);
			TexturedModel bush = createModel("grass", "texture/plant/bush", 0);
			bush.getTexture().setUseFakeLightning(true);
			generateDecoration(bush, 100, biosphere.position.x, biosphere.position.z, 1, 2, 110, true);
			TexturedModel tree = createModel("tree", "texture/plant/tree", 0);
			generateDecoration(tree, 40, biosphere.position.x, biosphere.position.z, 0.1F, 2, 110, false);
		}
		
		ray = new Raycaster(player);
		ray.setList(entities);
		input = new Input(Display.getHeight());
		renderer = new MasterRenderer();
	}
	
	public boolean tick()
	{
		input.poll(Display.getWidth(), Display.getHeight());
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(terrain(e.position.x, e.position.z));
			if(!e.invisible) renderer.processEntities(e);
		}
		c.update();
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		lights.get(1).position = new Vector3f(player.position.x, player.position.y + 0.5F, player.position.z);
		for(Light l : lights) l.update();
		
		MainGameLoop.fbo.bindFrameBuffer();
		renderer.processTerrain(terrain(player.position.x + 200, player.position.z + 200));
		renderer.processTerrain(terrain(player.position.x - 200, player.position.z + 200));
		renderer.processTerrain(terrain(player.position.x + 200, player.position.z - 200));
		renderer.processTerrain(terrain(player.position.x - 200, player.position.z - 200));
		renderer.render(lights, c, player);
		MainGameLoop.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(MainGameLoop.fbo.getColorTexture());

		if(isKeyDown(KEY_ESCAPE)) return false;
		
		if(isKeyDown(KEY_F5)) t[0] = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map")), "height_map");
		return true;
	}
	
	private void generateDecoration(TexturedModel model, int number, float x, float z, float scaleMin, float scaleMax, float rad, boolean duplicate)
	{
		Random r = new Random();
		for(int i = 0; i < number; i++)
		{
			float angle = r.nextFloat() * 360;
			float radius = r.nextFloat() * rad;
			float rotation = r.nextFloat() * 90;
			float size = scaleMin + r.nextFloat() * (scaleMax - scaleMin);
			new Entity(model, hVector((float)(x + radius * Math.cos(angle)), (float)(z + radius * Math.sin(angle))), 0, rotation, 0, size, entities);
			if(duplicate) new Entity(model, hVector((float)(x + radius * Math.cos(angle)), (float)(z + radius * Math.sin(angle))), 0, rotation + 180, 0, size, entities);
		}
	}
	
	public static TexturedModel createModel(String modelName, String textureName, float reflect)
	{
		return new TexturedModel(OBJLoader.loadOBJModel(modelName), new ModelTexture(MainManagerClass.loader.loadTexture(textureName), false, reflect));
	}
	
	public float height(float x, float z)
	{
		Terrain terr = terrain(x, z);
		return terr == null ? 0 : terr.getHeight(x, z);
	}
	
	public Terrain terrain(float positionX, float positionZ)
	{
		if(positionX < 0 || positionX > 3072 || positionZ < 0 || positionZ > 3072) return null;
		return t[((int)(positionX / 1024)) + (2 - ((int)(positionZ / 1024))) * 3];
	}
	
	public Vector3f hVector(float x, float z)
	{
		return new Vector3f(x, height(x, z), z);
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
