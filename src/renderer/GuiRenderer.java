package renderer;

import gui.GuiShader;
import gui.element.GuiBar;
import gui.element.GuiElement;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.SimpleModel;

public class GuiRenderer
{
	private final SimpleModel baseQuad;
	private GuiShader shader;
	
	public GuiRenderer(Loader loader)
	{
		float[] positions = {0, 1, 0, 0, 1, 1, 1, 0};
		baseQuad = loader.loadToVAO(positions, 2);
		shader = new GuiShader();
	}
	
	public void render(List<GuiElement> elements)
	{
		prepare();
		shader.start();
		GL30.glBindVertexArray(baseQuad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		for(GuiElement element : elements)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, element.getTextureID());
			Matrix4f matrix = createGuiTransformationMatrix(element.position, element.size);
			shader.loadTransformationMatrix(matrix);
			if(element instanceof GuiBar)
			{
				shader.loadOffset(((GuiBar)element).offset);
				shader.loadHeight(((GuiBar)element).height);
			}
			else
			{
				shader.loadOffset(0);
				shader.loadHeight(1);
			}
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, baseQuad.getVertexCount());
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1F);
	}

	private Matrix4f createGuiTransformationMatrix(Vector2f position, Vector2f size)
	{
		float widthFactor = 1 / (float)Display.getWidth();
		float heightFactor = 1 / (float)Display.getHeight();
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(new Vector3f(-1, -1, 0), matrix, matrix);
		Matrix4f.translate(new Vector2f(widthFactor * 2 * position.x, heightFactor * 2 * position.y), matrix, matrix);
		Matrix4f.scale(new Vector3f(widthFactor, heightFactor, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(size.x * 2, size.y * 2, 1), matrix, matrix);
		return matrix;
	}
}
