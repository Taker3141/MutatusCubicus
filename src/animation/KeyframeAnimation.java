package animation;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import entity.Entity;

public class KeyframeAnimation
{
	private Entity e;
	public Keyframe[] k;
	private Vector3f originalLocation;
	private Vector3f originalRotation;
	private float originalScale;
	private float time = 0;
	private float totalTime = 0;
	private float[] frameTimes;
	public boolean isRunning = true;
	
	public KeyframeAnimation(Entity e, Keyframe[] k)
	{
		this.e = e;
		this.k = k;
		originalLocation = e.position;
		originalRotation = new Vector3f(e.rotX, e.rotY, e.rotZ);
		originalScale = e.scale;
		for(Keyframe frame : k)
		{
			totalTime += frame.delta;
		}
		frameTimes = new float[k.length];
		frameTimes[0] = 0;
		for(int i = 1; i < frameTimes.length; i++)
		{
			frameTimes[i] = frameTimes[i - 1] + k[i - 1].delta;
		}
	}
	
	public void tick()
	{
		if(!isRunning) return;
		time += DisplayManager.getFrameTimeSeconds();
		int i1 = frameTimes.length - 1;
		while(time < frameTimes[i1]) i1--;
		if(time > totalTime) time = i1 = 0;
		int i2 = (i1 + 1) >= frameTimes.length ? 0 : i1 + 1;
		float timeSinceLastFrame = time - frameTimes[i1];
		float diff = frameTimes[i2] - frameTimes[i1];
		float q = timeSinceLastFrame / diff;
		e.position = Vector3f.add(originalLocation, Vector3f.add(k[i1].location, scale(q, Vector3f.sub(k[i2].location, k[i1].location, null)), null), null);
		e.rotX = originalRotation.x + k[i1].rotation.x + q * (k[i2].rotation.x - k[i1].rotation.x);
		e.rotY = originalRotation.y + k[i1].rotation.y + q * (k[i2].rotation.y - k[i1].rotation.y);
		e.rotZ = originalRotation.z + k[i1].rotation.z + q * (k[i2].rotation.z - k[i1].rotation.z);
		e.scale = originalScale + k[i1].scale + q * (k[i2].scale - k[i1].scale);
	}
	
	private Vector3f scale(float scalar, Vector3f v)
	{
		return new Vector3f(v.x * scalar, v.y * scalar, v.z * scalar);
	}
	
	public static class Keyframe
	{
		public Vector3f location;
		public Vector3f rotation;
		public float scale;
		public float delta;
		
		public Keyframe(Vector3f location, Vector3f rotation, float scale, float delta)
		{
			this.location = location;
			this.rotation = rotation;
			this.scale = scale;
			this.delta = delta;
		}
	}
}
