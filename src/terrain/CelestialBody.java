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
	
	public CelestialBody(float scale, float maxHeight, String heightMapFile, String blendMapFile, TerrainTexturePack textures)
	{
		simpleModel = generateModel(scale, maxHeight, 32, heightMapFile);
		this.textures = textures;
		blendMap = new TerrainTexture(MainManagerClass.loader.loadTexture(blendMapFile));
	}
	
	private SimpleModel generateModel(float radius, float maximumHeight, int resolution, String heightMapFile)
	{
		//float[][] heights = Terrain.readHeightMap(heightMapFile, maximumHeight, true);
		int vertexCount = (resolution * resolution) / 2;
		float[] vertices = new float[vertexCount * 3];
		float[] normals = new float[vertexCount * 3];
		float[] textureCoords = new float[vertexCount * 2];
		int[] indices = new int[vertexCount * 6];
		int vertexPointer = 0;
		for(float v = 0; v < resolution / 2; v++)
		{
			float circleRadius = radius * (float)Math.sin(Math.PI * (v / (resolution / 2)));
			for(float u = 0; u < resolution; u++)
			{
				vertices[vertexPointer * 3 + 0] = circleRadius * (float)Math.cos(2 * Math.PI * (u / resolution));
				vertices[vertexPointer * 3 + 1] = (u / resolution) - radius / 2;
				vertices[vertexPointer * 3 + 2] = circleRadius * (float)Math.sin(2 * Math.PI * (u / resolution));
				Vector3f normal = ((Vector3f)(new Vector3f(vertices[vertexPointer * 3 + 0], vertices[vertexPointer * 3 + 1], vertices[vertexPointer * 3 + 2]).scale(-1))).normalise(null);
				normals[vertexPointer * 3 + 0] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2 + 0] = u / resolution;
				textureCoords[vertexPointer * 2 + 1] = v / (resolution / 2);
				vertexPointer++;
			}
		}
		vertexPointer = 0;
		for(int v = 0; v < (resolution / 2) - 1; v++)
		{
			for(int u = 0; u < resolution - 1; u++)
			{
				int topLeft = (v * resolution) + u;
				int topRight = topLeft + 1;
				int bottomLeft = ((v + 1) * resolution) + u;
				int bottomRight = bottomLeft + 1;
				indices[vertexPointer++] = topLeft;
				indices[vertexPointer++] = bottomLeft;
				indices[vertexPointer++] = topRight;
				indices[vertexPointer++] = topRight;
				indices[vertexPointer++] = bottomLeft;
				indices[vertexPointer++] = bottomRight;
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
	public float getX()
	{
		return 0;
	}
	
	@Override
	public float getZ()
	{
		return 0;
	}
}
