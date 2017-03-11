package renderer.models;

public class SimpleModel
{
	private int vaoID;
	private int vertexCount;
	
	public SimpleModel(int vao, int vertex)
	{
		vaoID = vao;
		vertexCount = vertex;
	}

	public int getVaoID()
	{
		return vaoID;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
}
