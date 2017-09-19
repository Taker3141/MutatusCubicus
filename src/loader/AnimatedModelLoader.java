package loader;

import java.io.File;
import main.MainManagerClass;
import animation.Joint;
import renderer.Vao;
import renderer.models.AnimatedModel;
import renderer.textures.ModelTexture;

public class AnimatedModelLoader
{
	public static AnimatedModel loadEntity(File modelFile, File textureFile)
	{
		AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelFile, 3);
		Vao model = createVao(entityData.getMeshData());
		ModelTexture texture = loadTexture(textureFile.getPath());
		SkeletonData skeletonData = entityData.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);
		return new AnimatedModel(model, texture, headJoint, skeletonData.jointCount);
	}
	
	private static ModelTexture loadTexture(String textureFile)
	{
		return new ModelTexture(MainManagerClass.loader.loadTexture(textureFile));
	}

	private static Joint createJoints(JointData data)
	{
		Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children)
		{
			joint.addChild(createJoints(child));
		}
		return joint;
	}
	
	private static Vao createVao(MeshData data)
	{
		Vao vao = Vao.create();
		vao.bind();
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, data.getVertices(), 3);
		vao.createAttribute(1, data.getTextureCoords(), 2);
		vao.createAttribute(2, data.getNormals(), 3);
		vao.createIntAttribute(3, data.getJointIds(), 3);
		vao.createAttribute(4, data.getVertexWeights(), 3);
		vao.unbind();
		return vao;
	}
}
