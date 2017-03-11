package entity;

import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private float distanceFromPlayer = 20;
	
	public Vector3f position = new Vector3f(0, 10, 0);
	public float pitch = 20;
	public float yaw;
	public float roll;
	private Player player;
	private boolean isFirstPerson = false;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	public void update()
	{
		calculatePitch();
		calculateAngleAroundPlayer();
		if(position.y - 0.5 < MainGameLoop.w.height(position.x, position.z)) 
		{
			float minHeight = MainGameLoop.w.height(position.x, position.z) + 0.5F;
			float sin = (minHeight - player.position.y) / distanceFromPlayer;
			if(sin > 1) sin = 1;
			pitch = (float)Math.toDegrees(Math.asin(sin));
		}
		if (!isFirstPerson)
		{
			calculateZoom();
			calculateCameraPosition((float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch))), (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch))));
			yaw = 180 - (player.rotY);
		}
		else
		{
			position = new Vector3f(player.position.x, player.position.y + 0.5F, player.position.z);
			yaw = -player.rotY + 180;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) && !isFirstPerson)
		{
			pitch = 20;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) 
		{
			isFirstPerson = true;
			player.invisible = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))
		{
			isFirstPerson = false;
			player.invisible = false;
			pitch = 20;
		}
	}
	
	private void calculateCameraPosition(float hDistance, float vDistance)
	{
		position.x = player.position.x - (float) (hDistance * Math.sin(Math.toRadians(player.rotY)));
		position.y = player.position.y + vDistance;
		position.z = player.position.z - (float) (hDistance * Math.cos(Math.toRadians(player.rotY)));
	}
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.01F;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer < 1) distanceFromPlayer = 1;
		if (distanceFromPlayer > 50) distanceFromPlayer = 50;
	}
	
	private void calculatePitch()
	{
		if (Mouse.isButtonDown(2))
		{
			float pitchChange = Mouse.getDY() * 0.1F;
			if(position.y - 1 < MainGameLoop.w.height(position.x, position.z) && pitchChange > 0 && !isFirstPerson) return;
			pitch -= pitchChange;
			if (pitch < -90) pitch = -90;
			if (pitch > 90) pitch = 90;
		}
	}
	
	private void calculateAngleAroundPlayer()
	{
		if (Mouse.isButtonDown(2))
		{
			float angleChange = Mouse.getDX() * 0.3F;
			player.rotY -= angleChange;
		}
	}
	
	public boolean isFirstPerson()
	{
		return isFirstPerson;
	}
}
