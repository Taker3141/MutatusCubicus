package renderer.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class AnimatedModelShader extends ShaderProgram
{
	private static final int MAX_JOINTS = 50;
	private static final String VERTEX_SHADER = "src/renderer/shaders/animatedEntityVertex.vert";
	private static final String FRAGMENT_SHADER = "src/renderer/shaders/animatedEntityFragment.frag";
	
	private int locationProjectionViewMatrix;
	private int locationLightDirection;
	private int[] locationJointTransforms;
	
	public AnimatedModelShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
		super.bindAttribute(2, "in_normal");
		super.bindAttribute(3, "in_jointIndices");
		super.bindAttribute(4, "in_weights");
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationProjectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
		locationLightDirection = super.getUniformLocation("lightDirection");
		locationJointTransforms = new int[MAX_JOINTS];
		for(int i = 0; i < MAX_JOINTS; i++)
		{
			locationJointTransforms[i] = super.getUniformLocation("jointTransforms[" + i + "]");
		}
	}
	
	public void loadProjectionViewMatrix(Matrix4f projectionViewMatrix)
	{
		super.loadMatrix(locationProjectionViewMatrix, projectionViewMatrix);
	}
	
	public void loadLightDirection(Vector3f lightDirection)
	{
		super.loadVector(locationLightDirection, lightDirection);
	}
	
	public void loadJointTransforms(Matrix4f[] jointTransforms)
	{
		for(int i = 0; i < MAX_JOINTS && i < jointTransforms.length; i++)
		{
			super.loadMatrix(locationJointTransforms[i], jointTransforms[i]);
		}
	}
}
