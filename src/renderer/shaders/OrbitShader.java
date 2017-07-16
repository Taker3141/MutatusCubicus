package renderer.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import toolbox.Maths;
import entity.Camera;

public class OrbitShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/renderer/shaders/orbitVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/renderer/shaders/orbitFragmentShader.frag";
	private int locationProjection;
	private int locationView;
	private int locationLineColor;
	private int locationSkyColor;
	
	public OrbitShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "count");
	}
	
	@Override
	protected void getAllUniformLocations()
	{
		locationProjection = super.getUniformLocation("projectionMatrix");
		locationView = super.getUniformLocation("viewMatrix");
		locationLineColor = super.getUniformLocation("lineColor");
		locationSkyColor = super.getUniformLocation("skyColor");
	}
	
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationProjection, matrix);
	}
	
	public void loadViewMatrix(Camera c)
	{
		Matrix4f matrix = Maths.createViewMatrix(c);
		super.loadMatrix(locationView, matrix);
	}
	
	public void loadLineColor(float r, float b, float g)
	{
		super.loadVector(locationLineColor, new Vector3f(r, g, b));
	}
	
	public void loadSkyColor(float r, float b, float g)
	{
		super.loadVector(locationSkyColor, new Vector3f(r, g, b));
	}
}
