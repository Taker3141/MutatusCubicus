package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import entity.Camera;
import entity.Entity;
import entity.Light;
import renderer.models.TexturedModel;
import renderer.shaders.StaticShader;
import renderer.shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrain.Terrain;

public class MasterRenderer
{
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1F;
	private static final float FAR_PLANE = 1000;
	private static final float SKY_RED = 0.3F;
	private static final float SKY_GREEN = 0.5F;
	private static final float SKY_BLUE = 1.0F;
	private Matrix4f projection;
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private SkyboxRenderer skyboxRenderer;
	
	public MasterRenderer()
	{
		enableBackfaceCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projection);
		terrainRenderer = new TerrainRenderer(terrainShader, projection);
		skyboxRenderer = new SkyboxRenderer(MainManagerClass.loader, projection);
	}
	
	public static void enableBackfaceCulling()
	{
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableBackfaceCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera)
	{
		prepare();
		shader.start();
		shader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
		shader.loadLight(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadLight(lights);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera);
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain t)
	{
		terrains.add(t);
	}
	
	public void processEntities(Entity e)
	{
		TexturedModel entityModel = e.model;
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
		{
			batch.add(e);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(e);
			entities.put(entityModel, newBatch);
		}
	}
	
	private void createProjectionMatrix()
	{
		float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
		float yScale = (float)((1F / Math.tan(Math.toRadians(FOV / 2F))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustumLength = FAR_PLANE - NEAR_PLANE;
		
		projection = new Matrix4f();
		projection.m00 = xScale;
		projection.m11 = yScale;
		projection.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
		projection.m23 = -1;
		projection.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
		projection.m33 = 0;
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, 1F);
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return projection;
	}
}
