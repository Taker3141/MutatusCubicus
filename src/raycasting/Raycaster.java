package raycasting;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;
import renderer.MasterRenderer;
import toolbox.Maths;
import entity.*;

public class Raycaster
{
	private List<ICollidable> list = new ArrayList<ICollidable>();
	private static final float step = 0.05F;
	private Player player;

	private boolean isMouseDown = false;
	
	public Raycaster(Player p)
	{
		player = p;
	}
	
	public void castRay(int MouseX, int MouseY, MasterRenderer master, Camera c)
	{
		Vector4f rayEye = Matrix4f.transform(Matrix4f.invert(master.getProjectionMatrix(), null), new Vector4f((((float)MouseX) / Display.getWidth() * 2) - 1, (((float)MouseY) / Display.getHeight() * 2) - 1, -1, 1), null); rayEye.z = -1; rayEye.w = 0;
		Vector4f rayWorld4 = Matrix4f.transform(Matrix4f.invert(Maths.createViewMatrix(c), null), rayEye, null);
		Vector3f rayWorld = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z);
		rayWorld = rayWorld.normalise(rayWorld);
		
		Vector3f rayStep = new Vector3f(rayWorld.x * step, rayWorld.y * step, rayWorld.z * step);
		Vector3f vec = new Vector3f(c.position);
		
		boolean clickFlag = !isMouseDown && Mouse.isButtonDown(0);
		isMouseDown = Mouse.isButtonDown(0);

		endLoop:
		for(int i = 0; i < 100; i++)
		{
			vec = Vector3f.add(vec, rayStep, vec);
			for(ICollidable e : list)
			{
				if(e.isInsideHitBox(vec) != null) 
				{
					e.hover();
					if(clickFlag) player.clickAt(e, vec);
					break endLoop;
				}
			}
		}
	}
	
	public void setList(List<Entity> entityList)
	{
		for(Entity e : entityList)
		{
			list.add(e);
		}
	}
}
