package renderer.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import renderer.DisplayManager;

public class EffectManager
{
	private static float PI = 3.14159265358F;
	
	private ImageRenderer renderer;
	private FboTestShader shader;
	private float x = 0;
	private float y = 0;
	private float deltaT = 0;
	private boolean warping = false;
	
	public EffectManager()
	{
		shader = new FboTestShader();
		renderer = new ImageRenderer();
	}
	
	public void render(int texture)
	{
		if (warping)
		{
			x = (float) Math.cos(deltaT) * 0.25F + 0.25F;
			y = (float) Math.tan((deltaT / 2) + (PI / 4)) * 0.25F + 0.25F;
			if(deltaT > 2 * PI) warping = false;
			deltaT += 2 * DisplayManager.getFrameTimeSeconds();
		}
		else
		{
			x = 0.5F;
			y = 0.5F;
		}
		shader.start();
		shader.loadWarp(x, y);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void warp()
	{
		warping = true;
		deltaT = 0;
	}
	
	public void cleanUp()
	{
		renderer.cleanUp();
		shader.cleanUp();
	}
}
