package renderer.fbo;

import org.lwjgl.util.vector.Vector2f;
import renderer.shaders.ShaderProgram;

public class FboTestShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/renderer/fbo/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/renderer/fbo/fragmentShader.frag";
	
	private int locationGreen;
	
	public FboTestShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void setGreen(boolean green)
	{
		super.loadBoolean(locationGreen, green);
	}
	
	@Override
	protected void getAllUniformLocations()
	{
		locationGreen = super.getUniformLocation("green");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
