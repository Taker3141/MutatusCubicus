package entity;

import org.lwjgl.util.vector.Vector3f;

public class Light
{
	public Vector3f position;
	public Vector3f color;
	public Vector3f attenuation;
	
	public Light(Vector3f position, Vector3f color, Vector3f attenuation)
	{
		super();
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}
	
	public Light(Vector3f position, Vector3f color)
	{
		this(position, color, new Vector3f(1, 0, 0));
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
