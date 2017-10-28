package entity.building;

import java.util.List;
import java.util.Random;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.IHitBox;
import renderer.models.TexturedModel;
import terrain.Terrain;
import world.World;
import entity.Entity;
import entity.SlimeCell;

public class LabBuilding extends Building
{
	
	public LabBuilding(Vector3f position, List<Entity> list)
	{
		super(World.createModel("lab", "texture/concrete", 0), position, 0, 0, 0, 54.8F, list);
		loadSection(Section.FINAL_ASSEMBLY);
	}
	
	@Override
	protected void initializeWalls()
	{
		walls = new AABB[1];
		walls[0] = new AABB(new Vector3f(position.x, position.y + 0.9F * scale + 0.15F, position.z - scale * 2), new Vector3f(scale * 2, 0.3F, scale * 2), new Vector3f(0, 0.2F, 0), IHitBox.Type.FLOOR, false);
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		
	}
	
	public void loadSection(Section section)
	{
		switch(section)
		{
			case FINAL_ASSEMBLY:
				float x = position.x, y = 0.01F + position.y + 0.9F * scale, z = position.z;
				new Entity(World.createModel("square", "texture/lab/final_assembly", 0), new Vector3f(x + scale, y, z - scale), 0, 0, 0, scale, entityList);
				new Entity(World.createModel("lab/slime_tank", "texture/metal", 0.2F), new Vector3f(x + 87.5F, y, z - 108.8F), 0, 0, 0, scale / 10.3F, entityList);
				TexturedModel slimeCast = World.createModel("lab/slime_cast", "texture/metal", 0.2F);
				new Entity(slimeCast, new Vector3f(x + 82.1F, y, z - 87.6F), 0, 0, 0, 1, entityList);
				new Entity(slimeCast, new Vector3f(x + 82.1F, y, z - 98.6F), 0, 0, 0, 1, entityList);
				new Entity(slimeCast, new Vector3f(x + 71.2F, y, z - 87.6F), 0, 0, 0, 1, entityList);
				new Entity(slimeCast, new Vector3f(x + 71.2F, y, z - 98.6F), 0, 0, 0, 1, entityList);
				Random r = new Random(314);
				for(int i = 0; i < 70; i++) new SlimeCell(World.createModel("lab/storage_cell", "texture/metal", 0.2F), new Vector3f(x + 18 + 1.5F * (i % 35), y + (i / 35) * 1.5F, z - 0.2F), 0, 0, 0, 0.5F, 1.5F, entityList).configureHitbox(3);
				for(int i = 0; i < 20; i++) 
				{
					SlimeCell cell = new SlimeCell(World.createModel("lab/test_cell", "texture/metal", 0.2F), new Vector3f(x + 85 + 21 * (i / 10), y, z - (0.2F + 5F * (i % 10))), 0, 90, 0, 1, 5, entityList);
					if(i < 10) cell.addField(new Vector3f(1, 1, 0), new Vector3f(0, 0, -1), new Vector3f(90, 0, 0));
					else cell.addField(new Vector3f(1, 1, 0), new Vector3f(), new Vector3f(90, 0, 0));
					cell.configureHitbox(3 - (i / 10));
					cell.setOpen(r.nextInt() % 2 == 0);
				}
				break;
		}
	}
	
	public enum Section
	{
		FINAL_ASSEMBLY
	}
}
