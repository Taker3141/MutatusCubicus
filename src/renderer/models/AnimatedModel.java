package renderer.models;

import org.lwjgl.util.vector.Matrix4f;
import renderer.Vao;
import renderer.textures.ModelTexture;
import animation.Animation;
import animation.Animator;
import animation.Joint;

public class AnimatedModel
{
	private final Vao model;
	private final ModelTexture texture;
	private final Joint rootJoint;
	private final int jointCount;
	private final Animator animator;
	
	public AnimatedModel(Vao model, ModelTexture texture, Joint rootJoint, int jointCount)
	{
		this.model = model;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		rootJoint.calcInverseBindTransform(new Matrix4f());
	}

	public Vao getModel()
	{
		return model;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}

	public Joint getRootJoint()
	{
		return rootJoint;
	}

	public void delete()
	{
		model.delete();
	}

	public void doAnimation(Animation animation)
	{
		animator.doAnimation(animation);
	}

	public void update()
	{
		animator.update();
	}
	
	public Matrix4f[] getJointTransforms()
	{
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}

	private void addJointsToArray(Joint headJoint, Matrix4f[] jointMatrices)
	{
		jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();
		for (Joint childJoint : headJoint.children)
		{
			addJointsToArray(childJoint, jointMatrices);
		}
	}
	
}
