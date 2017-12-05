package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.IHitBox.Type;
import renderer.models.TexturedModel;
import world.MoonLabWorld;
import world.World;
import entity.Entity;
import entity.SubEntity;

public class ReactorBuilding extends Building
{
	public ReactorBuilding(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list, MoonLabWorld w)
	{
		super(World.createModel("reactor/reactor_building", "texture/concrete", 0), position, rotX, rotY, rotZ, 50, list);
		loadEntities(w);
	}

	@Override
	protected void initializeWalls()
	{
		Vector3f[] wallData = 
			{
				new Vector3f(5.01F, 0.2F, 0), new Vector3f(1.6F, 0.01F, -1.4F),
				new Vector3f(0, 0.2F, 0), new Vector3f(1.7F, 0.01F, -2),
				new Vector3f(0, 0.3F, 0), new Vector3f(1.7F, 0.01F, -2),
				new Vector3f(1.7F, 0.5F, 0), new Vector3f(3.3F, 0.01F, -2F),
				
				new Vector3f(-0.01F, 0, 0), new Vector3f(0.01F, 0.5F, -2.01F),
				new Vector3f(-0.01F, 0, 0), new Vector3f(2.4F, 0.5F, 0.01F),
				new Vector3f(2.6F, 0, 0), new Vector3f(2.4F, 0.5F, 0.01F),
				new Vector3f(2.4F, 0.15F, 0), new Vector3f(0.2F, 0.35F, 0.01F),
				new Vector3f(-0.01F, 0, -2.01F), new Vector3f(5.01F, 0.5F, 0.01F),
				new Vector3f(5.01F, 0, -1.41F), new Vector3f(0.01F, 0.5F, -0.58F),
				new Vector3f(5.01F, 0, 0), new Vector3f(1.6F, 0.2F, 0.01F),
				new Vector3f(5.01F, 0, -1.4F), new Vector3f(1.6F, 0.2F, 0.01F),
			};
		for(int i = 0; i < wallData.length; i++) wallData[i] = (Vector3f)wallData[i].scale(scale);
		walls = new AABB[wallData.length / 2];
		for (int i = 0; i < walls.length; i++)
		{
			int pVector = i * 2, sVector = i * 2 + 1;
			Vector3f p = Vector3f.add(position, (Vector3f)wallData[pVector], null);
			walls[i] = new AABB(p, (Vector3f)wallData[sVector], new Vector3f(0, 0, 0), i < 4 ? Type.FLOOR : Type.WALL, false);
		}
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
