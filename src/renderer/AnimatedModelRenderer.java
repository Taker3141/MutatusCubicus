package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import entity.Camera;
import renderer.models.AnimatedModel;
import renderer.shaders.AnimatedModelShader;
import toolbox.Maths;

public class AnimatedModelRenderer
{
	private AnimatedModelShader shader;
	private Matrix4f projectionMatrix;

	public AnimatedModelRenderer(Matrix4f projectionMatrix)
	{
		this.shader = new AnimatedModelShader();
		this.projectionMatrix = projectionMatrix;
	}
	
	public void render(AnimatedModel entity, Camera camera, Vector3f lightDir)
	{
		prepare(camera, lightDir);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getTexture().getID());
		entity.getModel().bind(0, 1, 2, 3, 4);
		shader.loadJointTransforms(entity.getJointTransforms());
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		entity.getModel().unbind(0, 1, 2, 3, 4);
		finish();
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
	}
	
	private void prepare(Camera camera, Vector3f lightDir)
	{
		shader.start();
		shader.loadProjectionViewMatrix(Matrix4f.mul(projectionMatrix, Maths.createViewMatrix(camera), null));
		shader.loadLightDirection(lightDir);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	private void finish()
	{
		shader.stop();
	}
}
