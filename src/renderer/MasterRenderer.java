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
import entity.Player;
import gui.Overlay;
import renderer.models.TexturedModel;
import renderer.shaders.StaticShader;
import renderer.shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrain.Terrain;

public class MasterRenderer
{
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 1e-1F;
	private static final float FAR_PLANE = 1e+3F;
	private static final float VERY_FAR_PLANE = 1e+7F;
	private static final float SKY_RED = 0F;
	private static final float SKY_GREEN = 0F;
	private static final float SKY_BLUE = 0F;
	private Matrix4f projection;
	private Matrix4f farProjection;
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private SkyboxRenderer skyboxRenderer;
	private GuiRenderer guiRenderer;
	
	public MasterRenderer()
	{
		enableBackfaceCulling();
		projection = createProjectionMatrix(FOV, FAR_PLANE, NEAR_PLANE);
		farProjection = createProjectionMatrix(FOV, VERY_FAR_PLANE, 500);
		renderer = new EntityRenderer(shader, projection, farProjection);
		terrainRenderer = new TerrainRenderer(terrainShader, projection);
		skyboxRenderer = new SkyboxRenderer(MainManagerClass.loader, projection);
		guiRenderer = new GuiRenderer(MainManagerClass.loader);
	}
	
	public static void enableBackfaceCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableBackfaceCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera, Player p, List<Overlay> overlays)
	{
		prepare();
		terrainShader.start();
		terrainShader.loadLight(lights);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera);
		shader.start();
		shader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
		shader.loadLight(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities, false);
		shader.stop();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		shader.start();
		shader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
		shader.loadLight(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities, true);
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(Overlay o : overlays)
		{
			guiRenderer.render(o.getElements(), false);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain t)
	{
		if(!terrains.contains(t)) terrains.add(t);
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
	
	private Matrix4f createProjectionMatrix(float fov, float farPlane, float nearPlane)
	{
		float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
		float yScale = (float)((1F / Math.tan(Math.toRadians(fov / 2F))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustumLength = farPlane - nearPlane;
		
		Matrix4f m = new Matrix4f();
		m.m00 = xScale;
		m.m11 = yScale;
		m.m22 = -((farPlane + nearPlane) / frustumLength);
		m.m23 = -1;
		m.m32 = -((2 * nearPlane * farPlane) / frustumLength);
		m.m33 = 0;
		return m;
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
