package entity;

import org.lwjgl.util.vector.Vector3f;

public class Light
{
	public Vector3f position;
	public Vector3f color;
	public Light(Vector3f position, Vector3f color)
	{
		super();
		this.position = position;
		this.color = color;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public Vector3f getColor()
	{
		return color;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
	}
}
