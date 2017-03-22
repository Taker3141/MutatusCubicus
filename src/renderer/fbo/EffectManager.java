package renderer.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import renderer.DisplayManager;

public class EffectManager
{
	private ImageRenderer renderer;
	private FboTestShader shader;
	public boolean greenFilter;
	
	public EffectManager()
	{
		shader = new FboTestShader();
		renderer = new ImageRenderer();
	}
	
	public void render(int texture)
	{
		shader.start();
		shader.setGreen(greenFilter);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp()
	{
		renderer.cleanUp();
		shader.cleanUp();
	}
}
