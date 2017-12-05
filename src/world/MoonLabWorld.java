package world;

import static org.lwjgl.input.Keyboard.*;
import gui.handler.MouseHandler;
import main.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import raycasting.*;
import renderer.fbo.PostProcessing;
import renderer.models.TexturedModel;
import renderer.textures.*;
import terrain.*;
import entity.*;
import entity.building.*;
import entity.character.Player;
import entity.vehicle.*;

public class MoonLabWorld extends World
{
	private Terrain[] t;
	private Input input;
	private MouseHandler mouse;
	private static final Vector3f SUN_START = new Vector3f(0.866F, 0, -0.5F);
	private static final float SUN_DISTANCE = 10000;
	
	@Override
	public void loadEntities()
	{		
		player = new Player(new Vector3f(1650, 0, 1319), 0, 180, 0, entities);
		overlays.add(player.organs);
		timeOfDay = 0.25F;
		lights.add(new Light(new Vector3f(SUN_START.x * SUN_DISTANCE, 0, SUN_START.z * SUN_DISTANCE), new Vector3f(1, 1, 1)));
		overlays.add(player.com);
		overlays.add(characterInfo);
		MainGameLoop.reportProgress(20);
		lights.add(new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0.6F, 0), new Vector3f(1, 0.01F, 0.2F)));
		lights.add(new PulsatingLight(new Vector3f(1261, 40, 1955), new Vector3f(2F, 0.1F, 1.8F), new Vector3f(1, 0.01F, 0.002F), 2));
		c = new Camera(player, this, false);
		t = new SquareTerrain[3 * 3];
		TerrainTexturePack pack = loadTerrainTexturePack();
		for (int i = 0; i < 9; i++)
		{
			int tx = i % 3; int tz = i / 3;
			t[i] = new SquareTerrain(tx, 2 - tz, loader, pack, 
					new TerrainTexture(loader.loadTexture((tx == 1 && tz == 1) ? "texture/terrain/blend_1_1" : "texture/terrain/blend_0_0")), 
					"terrain/height_" + tx + "_" + tz);
		}
		MainGameLoop.reportProgress(40);
		new Car(hVector(player.position.x - 5, player.position.z), 0, 0, 0, 0.6F, entities);
		new Rocketship(hVector(1135, 1700), 0, -90, 0, entities);
		new Rocketship(hVector(1250, 1700), 0, 180, 0, entities);
		{
			for(int i = 0; i < 100; i++)
			{
				Vector3f position = new Vector3f(1024 * r.nextFloat() + 1024, 0, 1024 * r.nextFloat() + 1024);
				position.y = height(position.x, position.z);
				new Rock(position, r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.04F + r.nextFloat() / 2.5F, entities);
			}
			
			TexturedModel waste = createModel("waste", "texture/waste", 0.05F);
			for(int i = 0; i < 100; i++)
			{
				Vector3f position = new Vector3f(20 * r.nextFloat(), 0, 20 * r.nextFloat());
				position.y = height(position.x, position.z) + 0.2F;
				new Waste(waste, position, r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.24F + r.nextFloat() * 0.08F, entities);
			}
			MainGameLoop.reportProgress(60);
			new ReactorBuilding(new Vector3f(1062, height(1062, 2004) - 1, 2004), 0, 90, 0, entities, this);
			new LivingBuilding(new Vector3f(1361, height(1361, 1498) - 1, 1498), 0, 0, 0, entities);
			new ChemicalReactorBuilding(new Vector3f(1824, height(1824, 1694) - 1, 1714), 0, 0, 0, entities);
			new LabBuilding(hVector(1577, 1421), entities);
			{
				FuelGenerator.init();
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
				MainGameLoop.reportProgress(80);
				for(int i = 0; i < 10; i++)
				{
					new Rock(hVector(1525 + r.nextFloat() * 20, 1967 + r.nextFloat() * 20), r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.3F + r.nextFloat() / 10F, entities);
					Entity b1 = new Entity(box1.model, hVector(1468 + r.nextFloat() * 20, 1949 + r.nextFloat() * 20), 0, r.nextFloat() * 360, 0, 2, entities);
					b1.setHitBox(new AABB(b1.position, new Vector3f(2, 2, 2), new Vector3f(-1, 0, -1)));
					Entity b2 = new Entity(box2.model, hVector(1480 + r.nextFloat() * 20, 1913 + r.nextFloat() * 20), 0, r.nextFloat() * 360, 0, 2, entities);
					b2.setHitBox(new AABB(b2.position, new Vector3f(2, 2, 2), new Vector3f(-1, 0, -1)));
				}
				for(int i = 0; i < 100; i++)
				{
					new OreRock(hVector(1757 + r.nextFloat() * 164, 1915 + r.nextFloat() * 89), r.nextFloat() * 360, r.nextFloat() * 360, 0, 0.1F + r.nextFloat() / 5F, entities);
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
			MainGameLoop.reportProgress(100);
		}
		input = new Input(Display.getHeight());
		mouse = new MouseHandler(overlays, true);
		input.addMouseListener(mouse);
		mouse.setInput(input);
	}
	
	@Override
	public boolean tick()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(this, terrain(e.position.x, e.position.z));
			if(!e.invisible) renderer.processEntities(e);
		}
		if(isKeyDown(KEY_H))player.position.y = 70;
		mouse.updateList(overlays);
		input.poll(Display.getWidth(), Display.getHeight());
		float lightCosine = (float)Math.cos(timeOfDay * 2 * 3.14159265358F); 
		float lightSine = (float)Math.sin(timeOfDay * 2 * 3.14159265358F);
		float lightBrightness = (lightSine > 0 ? lightSine : 0) + 0.2F;
		lights.get(0).color = (Vector3f)new Vector3f(1, 1, 1).scale(lightBrightness);
		lights.get(0).position = new Vector3f(SUN_DISTANCE * SUN_START.x, SUN_DISTANCE * lightSine, SUN_DISTANCE * lightCosine * SUN_START.z);
		super.tick();
		MainGameLoop.fbo.bindFrameBuffer();
		renderer.processTerrain(terrain(player.position.x + 200, player.position.z + 200));
		renderer.processTerrain(terrain(player.position.x - 200, player.position.z + 200));
		renderer.processTerrain(terrain(player.position.x + 200, player.position.z - 200));
		renderer.processTerrain(terrain(player.position.x - 200, player.position.z - 200));
		renderer.render(this);
		MainGameLoop.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(MainGameLoop.fbo.getColorTexture());

		if(isKeyDown(KEY_ESCAPE)) return false;
		if(isKeyDown(KEY_DELETE) && isKeyDown(KEY_LSHIFT)) {@SuppressWarnings("unused") int ohNo = 1 / 0;}
		
		if(isKeyDown(KEY_F1) && player.com.hidden()) player.com.show(); 
		else if(isKeyDown(KEY_F1) && !player.com.hidden()) player.com.hide();
		
		return true;
	}
	
	@Override
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
	
	@Override
	public Vector3f getGravityVector(Entity e)
	{
		return new Vector3f(0, -10, 0);
	}

	private TerrainTexturePack loadTerrainTexturePack()
	{
		TerrainTexture back = new TerrainTexture(loader.loadTexture("texture/moon_dust"));
		TerrainTexture r = new TerrainTexture(loader.loadTexture("texture/moon_crater"));
		TerrainTexture g = new TerrainTexture(loader.loadTexture("texture/grass"));
		TerrainTexture b = new TerrainTexture(loader.loadTexture("texture/moon_path"));
		return new TerrainTexturePack(back, r, g, b);
	}
}
