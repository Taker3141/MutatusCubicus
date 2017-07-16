package renderer;

import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import entity.Orbit;
import renderer.shaders.OrbitShader;

public class OrbitRenderer
{
	private OrbitShader shader;
	private Matrix4f projectionMatrix;
	private Matrix4f farProjectionMatrix;
	
	public OrbitRenderer(OrbitShader s, Matrix4f projectionMatrix, Matrix4f farProjectionMatrix)
	{
		this.projectionMatrix = projectionMatrix;
		this.farProjectionMatrix = farProjectionMatrix;
		shader = s;
	}
	
	public void render(List<Orbit> orbits)
	{
		for(Orbit o : orbits)
		{
			if(!o.visible) continue;
			shader.loadProjectionMatrix(projectionMatrix);
			prepareOrbit(o);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, o.getVertexCount());
			shader.loadProjectionMatrix(farProjectionMatrix);
			GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, o.getVertexCount());
			unbindOrbit();
		}
	}
	
	private void prepareOrbit(Orbit o)
	{
		GL30.glBindVertexArray(o.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	private void unbindOrbit()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
}
