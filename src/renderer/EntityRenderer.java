package renderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import entity.Entity;
import renderer.models.SimpleModel;
import renderer.models.TexturedModel;
import renderer.shaders.StaticShader;
import renderer.textures.ModelTexture;

public class EntityRenderer
{
	private StaticShader shader;
	
	public EntityRenderer(StaticShader s, Matrix4f projectionMatrix)
	{
		shader = s;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities, boolean renderTransparency)
	{
		TexturedModel[] models = new TexturedModel[entities.size()];
		entities.keySet().toArray(models);
		if(renderTransparency) Arrays.sort(models);
		for(TexturedModel m : models)
		{
			if(renderTransparency != m.getTexture().hasTransparency()) continue;
			prepareTexturedModel(m);
			List<Entity> batch = entities.get(m);
			for(Entity e:batch)
			{
				prepareInstance(e);
				GL11.glDrawElements(GL11.GL_TRIANGLES, m.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model)
	{
		SimpleModel s = model.getModel();
		GL30.glBindVertexArray(s.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadFakeLightningVariable(texture.isUseFakeLightning());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		shader.loadTransparency(texture.hasTransparency());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexturedModel()
	{
		MasterRenderer.enableBackfaceCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity e)
	{
		shader.loadTransformationMatrix(e.getTransformationMatrix());
	}
}
