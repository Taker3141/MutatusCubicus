package renderer;

import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.SimpleModel;
import renderer.shaders.TerrainShader;
import renderer.textures.TerrainTexturePack;
import terrain.Terrain;
import toolbox.Maths;

public class TerrainRenderer
{
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader s, Matrix4f projectionMatrix)
	{
		shader = s;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains)
	{
		for(Terrain terrain : terrains)
		{
			if(terrain == null) continue;
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTerrain();
		}
	}
	
	private void prepareTerrain(Terrain t)
	{
		SimpleModel s = t.getModel();
		GL30.glBindVertexArray(s.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(t);
	}
	
	private void bindTextures(Terrain t)
	{
		TerrainTexturePack pack = t.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, pack.getBackgroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, pack.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, pack.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, pack.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getBlendMap().getTextureID());
	}
	
	private void unbindTerrain()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain t)
	{
		Matrix4f transformation = Maths.createTransformationMatrix(new Vector3f(t.getX(), 0, t.getZ()), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformation);
	}
}
