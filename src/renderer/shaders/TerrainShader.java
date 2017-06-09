package renderer.shaders;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;
import entity.Camera;
import entity.Light;

public class TerrainShader extends ShaderProgram
{
	private static final int MAX_LIGHTS = 4;
	private static final String VERTEX_FILE = "src/renderer/shaders/terrainVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/renderer/shaders/terrainFragmentShader.frag";
	private int locationMatrix;
	private int locationProjection;
	private int locationView;
	private int locationLightPosition[];
	private int locationLightColor[];
	private int locationLightAttenuation[];
	private int locationShineDamper;
	private int locationReflectivity;
	private int locationBackground;
	private int locationR;
	private int locationG;
	private int locationB;
	private int locationMap;
	private int locationSkyColor;
	
	public TerrainShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoord");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjection = super.getUniformLocation("projectionMatrix");
		locationView = super.getUniformLocation("viewMatrix");
		locationShineDamper = super.getUniformLocation("shineDamper");
		locationReflectivity = super.getUniformLocation("reflectivity");
		locationBackground = super.getUniformLocation("backgroundTexture");
		locationR = super.getUniformLocation("rTexture");
		locationG = super.getUniformLocation("gTexture");
		locationB = super.getUniformLocation("bTexture");
		locationMap = super.getUniformLocation("blendMap");
		locationSkyColor = super.getUniformLocation("skyColor");
		
		locationLightPosition = new int[MAX_LIGHTS];
		locationLightColor = new int[MAX_LIGHTS];
		locationLightAttenuation = new int[MAX_LIGHTS];
		for(int i = 0; i < MAX_LIGHTS; i++)
		{

			locationLightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			locationLightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			locationLightAttenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationMatrix, matrix);
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
	
	public void loadLight(List<Light> l)
	{
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			if(i < l.size())
			{
				super.loadVector(locationLightPosition[i], l.get(i).position);
				super.loadVector(locationLightColor[i], l.get(i).color);
				super.loadVector(locationLightAttenuation[i], l.get(i).attenuation);
			}
			else
			{
				super.loadVector(locationLightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(locationLightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(locationLightAttenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadShineVariables(float damper, float reflectvity)
	{
		super.loadFloat(locationShineDamper, damper);
		super.loadFloat(locationReflectivity, reflectvity);
	}
	
	public void connectTextureUnits()
	{
		super.loadInt(locationBackground, 0);
		super.loadInt(locationR, 1);
		super.loadInt(locationG, 2);
		super.loadInt(locationB, 3);
		super.loadInt(locationMap, 4);
	}
	
	public void loadSkyColor(float r, float b, float g)
	{
		super.loadVector(locationSkyColor, new Vector3f(r, g, b));
	}
}
