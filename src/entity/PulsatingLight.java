package entity;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;

public class PulsatingLight extends Light
{
	public float frequency;
	public Vector3f lightColor;

	public PulsatingLight(Vector3f position, Vector3f color, Vector3f attenuation, float f)
	{
		super(position, color, attenuation);
		lightColor = color;
		frequency = f;
	}
	
	@Override
	public void update()
	{
		float cycle = DisplayManager.getTime() % (1 / frequency);
		if(cycle > (1 / (2 * frequency))) color = lightColor; else color = new Vector3f();
	}
}
