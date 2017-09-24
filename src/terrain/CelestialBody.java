package terrain;

import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.SimpleModel;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;

public class CelestialBody implements Terrain
{
	protected SimpleModel simpleModel;
	protected TerrainTexturePack textures;
	protected TerrainTexture blendMap;
	public Vector3f position = new Vector3f();
	
	public CelestialBody(float scale, float maxHeight, String heightMapFile, String blendMapFile, TerrainTexturePack textures)
	{
		simpleModel = generateModel(scale, maxHeight, 32, heightMapFile);
		this.textures = textures;
		blendMap = new TerrainTexture(MainManagerClass.loader.loadTexture(blendMapFile));
	}
	
	private SimpleModel generateModel(float radius, float maximumHeight, int resolution, String heightMapFile)
	{
		//float[][] heights = Terrain.readHeightMap(heightMapFile, maximumHeight, true);
		int horizontalCount = resolution;
		int verticalCount = resolution / 2;
		int vertexCount = (horizontalCount + 1) * (verticalCount + 1);
		float[] vertices = new float[vertexCount * 3];
		float[] normals = new float[vertexCount * 3];
		float[] textureCoords = new float[vertexCount * 2];
		int[] indices = new int[vertexCount * 6];
		int vertexPointer = 0;
		for(float v = 0; v < verticalCount + 1; v++)
		{
			float phi = (float) (Math.PI * (v / verticalCount));
			for(float u = 0; u < horizontalCount + 1; u++)
			{
				float theta = (float)(2 * Math.PI * (u / horizontalCount));
				vertices[vertexPointer * 3 + 0] = (float) (radius * Math.sin(phi) * Math.cos(theta));
				vertices[vertexPointer * 3 + 1] = (float) (radius * Math.cos(phi));
				vertices[vertexPointer * 3 + 2] = (float) (radius * Math.sin(phi) * Math.sin(theta));
				Vector3f normal = ((Vector3f)(new Vector3f(vertices[vertexPointer * 3 + 0], vertices[vertexPointer * 3 + 1], vertices[vertexPointer * 3 + 2]))).normalise(null);
				normals[vertexPointer * 3 + 0] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2 + 0] = u / horizontalCount;
				textureCoords[vertexPointer * 2 + 1] = v / (verticalCount);
				vertexPointer++;
			}
		}
		vertexPointer = 0;
		for(int v = 0; v < verticalCount; v++)
		{
			for(int u = 0; u < horizontalCount; u++)
			{
				int topLeft = (v * (horizontalCount + 1)) + u;
				int topRight = topLeft + 1;
				int bottomLeft = ((v + 1) * (horizontalCount + 1)) + u;
				int bottomRight = bottomLeft + 1;
				
				indices[vertexPointer++] = topRight;
				indices[vertexPointer++] = bottomLeft;
				indices[vertexPointer++] = topLeft;
				
				indices[vertexPointer++] = bottomRight;
				indices[vertexPointer++] = bottomLeft;
				indices[vertexPointer++] = topRight;
			}
		}
		return MainManagerClass.loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	@Override
	public SimpleModel getModel()
	{
		return simpleModel;
	}
	
	@Override
	public TerrainTexturePack getTexturePack()
	{
		return textures;
	}
	
	@Override
	public TerrainTexture getBlendMap()
	{
		return blendMap;
	}
	
	@Override
	public float getHeight(float x, float z)
	{
		return 0;
	}
	
	@Override
	public Vector3f getPosition()
	{
		return position;
	}
}
