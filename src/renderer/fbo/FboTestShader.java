package renderer.fbo;

import org.lwjgl.util.vector.Vector2f;
import renderer.shaders.ShaderProgram;

public class FboTestShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/renderer/fbo/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/renderer/fbo/fragmentShader.frag";
	
	private int locationWarp;
	
	public FboTestShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadWarp(float x, float y)
	{
		super.load2DVector(locationWarp, new Vector2f(x, y));
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationWarp = super.getUniformLocation("warp");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
	
}
