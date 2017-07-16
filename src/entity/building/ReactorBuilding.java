package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import renderer.models.TexturedModel;
import world.MoonLabWorld;
import world.World;
import entity.Entity;
import entity.SubEntity;

public class ReactorBuilding extends Building
{
	public ReactorBuilding(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, MoonLabWorld w)
	{
		super(World.createModel("reactor/reactor_building", "texture/concrete", 0), position, rotX, rotY, rotZ, scale, list);
		loadEntities(w);
	}

	@Override
	protected void initializeWalls()
	{
		walls = new AABB[0];
	}
	
	protected void loadEntities(MoonLabWorld w)
	{
		new SubEntity(World.createModel("reactor/reactor_decoration", "texture/reactor_decoration", 0.5F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/plasma_chamber", "texture/plasma_chamber", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/turbine", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/electrolyse_chamber", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/isotope_generator", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(-0.09F, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/water_tube", "texture/color/blue", 0.2F), new Vector3f(-0.18F, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/hydrogen_tube1", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/hydrogen_tube2", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/heavy_hydrogen_tube", "texture/color/green", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/helium_tube", "texture/color/yellow", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/steam_tube1", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		new SubEntity(World.createModel("reactor/steam_tube2", "texture/metal", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, entityList, this);
		{
			TexturedModel energyCell = World.createModel("reactor/energy_cell", "texture/color/red", 0);
			for(int i = 0; i < 11; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					new Entity(energyCell, w.hVector(1310 + i * 8, 1940 + j * 8), 0, 0, 0, 2, entityList);
				}
			}
		}
	}
}
