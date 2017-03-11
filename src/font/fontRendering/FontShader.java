package font.fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.shaders.ShaderProgram;

public class FontShader extends ShaderProgram
{
	
	private static final String VERTEX_FILE = "src/font/fontRendering/fontVertex.vert";
	private static final String FRAGMENT_FILE = "src/font/fontRendering/fontFragment.frag";
	
	private int locationColor;
	private int locationTranslation;
	private int locationDisplaySize;
	
	public FontShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations()
	{	
		locationColor = super.getUniformLocation("color");
		locationTranslation = super.getUniformLocation("translation");
		locationDisplaySize = super.getUniformLocation("displaySize");
	}
	
	@Override
	protected void bindAttributes()
	{	
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoord");
	}
	
	protected void loadColor(Vector3f color)
	{
		super.loadVector(locationColor, color);
	}
	
	protected void loadTranslation(Vector2f translation)
	{
		super.load2DVector(locationTranslation, translation);
	}
	
	protected void loadDisplaySize(Vector2f size)
	{
		super.load2DVector(locationDisplaySize, size);
	}
}
