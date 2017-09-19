package renderer.fbo;

import loader.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderer.models.SimpleModel;

public class PostProcessing
{
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static SimpleModel quad;
	public static EffectManager effects;
	
	public static void init(Loader loader)
	{
		quad = loader.loadToVAO(POSITIONS, 2);
		effects = new EffectManager();
	}
	
	public static void doPostProcessing(int colorTexture)
	{
		start();
		effects.render(colorTexture);
		end();
	}
	
	public static void cleanUp()
	{	
		effects.cleanUp();
	}
	
	private static void start()
	{
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
}