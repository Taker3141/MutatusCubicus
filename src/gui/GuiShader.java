package gui;

import org.lwjgl.util.vector.Matrix4f;
import renderer.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram
{
	public static final String VERTEX_FILE = "src/gui/guiVertexShader.vert";
	public static final String FRAGMENT_FILE = "src/gui/guiFragmentShader.frag";
	
	private int locationTransformationMatrix;
	private int locationLayer;
	private int locationOffsetX;
	private int locationOffsetY;
	private int locationHeight;
	private int locationWidth;
	
	public GuiShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix)
	{
		super.loadMatrix(locationTransformationMatrix, transformationMatrix);
	}
	
	public void loadLayer(int layer)
	{
		super.loadInt(locationLayer, layer);
	}
	
	public void loadOffset(float offsetX, float offsetY)
	{
		super.loadFloat(locationOffsetX, offsetX);
		super.loadFloat(locationOffsetY, offsetY);
	}
	
	public void loadHeight(float height)
	{
		super.loadFloat(locationHeight, height);
	}
	
	public void loadWidth(float width)
	{
		super.loadFloat(locationWidth, width);
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationLayer = super.getUniformLocation("layer");
		locationOffsetX = super.getUniformLocation("offsetX");
		locationOffsetY = super.getUniformLocation("offsetY");
		locationHeight = super.getUniformLocation("height");
		locationWidth = super.getUniformLocation("width");
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}	
}
