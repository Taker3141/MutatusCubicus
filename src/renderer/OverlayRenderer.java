package renderer;

import org.lwjgl.opengl.GL11;

public class OverlayRenderer extends GuiRenderer
{
	public OverlayRenderer(Loader loader)
	{
		super(loader);
	}
	
	@Override
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
