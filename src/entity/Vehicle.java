package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import terrain.Terrain;
import world.World;

public class Vehicle extends Movable
{
	public Vehicle(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(World.createModel("car/car", "texture/metal", 0.2F), position, rotX, rotY, rotZ, scale, list, mass);
		Entity roof = new SubEntity(World.createModel("car/roof", "texture/glass", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, list, this);
		roof.model.getTexture().setHasTransparency(true);
		new SubEntity(World.createModel("car/door", "texture/metal", 0.2F), new Vector3f(0.86F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/doorR", "texture/metal", 0.2F), new Vector3f(-0.76F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-1.5F, 0, 0), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0, 0, 2.5F), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-1.5F, 0, 2.5F), 0, 0, 0, 1, list, this);
		hitBox = new AABB(new Vector3f(0, 0, 0), new Vector3f(1.5F, 1.2F, 2.5F), new Vector3f(-0.75F, 0, -1.25F));
	}
}
