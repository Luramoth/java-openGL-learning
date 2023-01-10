package renderEngine;

import de.javagl.obj.*;
import models.RawModel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class OBJLoader {

	private static Obj obj;

	public static RawModel loadOBJ(String fileName, Loader loader) throws Exception {

		// read OBJ
		System.out.println("loading OBJ");
		InputStream objInputStream = Files.newInputStream(Paths.get("res/" + fileName + ".obj"));

		// put it in OBJ class
		obj = ObjReader.read(objInputStream);

		// map texture coords so they are unique
		//obj = ObjUtils.makeTexCoordsUnique(obj);

		obj = ObjUtils.convertToRenderable(obj);

		int[] faceVertexIndices = ObjData.getFaceVertexIndicesArray(obj);

		// index vertices, normals, and texture coordinates
		float vertices[] = ObjData.getVerticesArray(obj);
		float texCoords[] = ObjData.getTexCoordsArray(obj, 2);
		float normals[] = ObjData.getNormalsArray(obj);

		// Obtain the data from the OBJ, as direct buffers:
		//FloatBuffer vertices = ObjData.getVertices(obj);
		//FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
		//FloatBuffer normals = ObjData.getNormals(obj);

		System.out.println("UVs:");
		System.out.println(createString(texCoords, 2));


		return loader.loadToVAO(vertices, texCoords, faceVertexIndices);
	}

	private static String createString(IntBuffer buffer, int stride)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<buffer.capacity(); i+=stride)
		{
			for (int j=0; j<stride; j++)
			{
				if (j > 0)
				{
					sb.append(", ");
				}
				sb.append(buffer.get(i+j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static String createString(FloatBuffer buffer, int stride)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<buffer.capacity(); i+=stride)
		{
			for (int j=0; j<stride; j++)
			{
				if (j > 0)
				{
					sb.append(", ");
				}
				sb.append(buffer.get(i+j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static String createString(float array[], int stride)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<array.length; i+=stride)
		{
			for (int j=0; j<stride; j++)
			{
				if (j > 0)
				{
					sb.append(", ");
				}
				sb.append(array[i+j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}



	public static Obj getObj() {
		return obj;
	}

	// function to load .obj files
	/*public static RawModel loadObjModel (String fileName, Loader loader){
		FileReader fr;// make a file reader

		try {
			fr = new FileReader("res/"+fileName+".obj");// try to load the file, if cant, then throw an error
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file!");
			throw new RuntimeException(e);
		}

		BufferedReader reader = new BufferedReader(fr);

		String line;

		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();

		float[] verticesArray;
		float[] normalsArray;
		float[] textureArray = null;
		int[] indicesArray;

		try{

			while (true){
				line = reader.readLine();// read line
				String[] currentLine = line.split(" ");// split it at each space

				if (line.startsWith("v ")){ // if it starts with "v" then write it as a vertex

					Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));// make the vertex using the data

					vertices.add(vertex);// add the vertex to the list

				}else if (line.startsWith("vt ")){ // if it starts  with "vt" then write it as a UV

					Vector2f texture = new Vector2f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));// make the UV using the data

					textures.add(texture);// add the UV to the list

				}else if (line.startsWith("vn ")){ // if it starts with "vn" then write it as a normal

					Vector3f normal = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));// make the normal using the data

					normals.add(normal);// add the normal to the list

				}else if (line.startsWith("f ")){// if it starts with an "f" then stop and create the UV and normal array
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}

			while(line != null){// if the line isent null, loop
				if (!line.startsWith("f ")){// if the line doesn't start with "f" then read the next line
					line = reader.readLine();

					continue;
				}

				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);

				line = reader.readLine();

			}

			reader.close();

		}catch (Exception e){
			e.printStackTrace();
		}

		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];

		int vertexPointer = 0;

		for (Vector3f vertex:vertices){
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}

		for (int i=0; i < indices.size(); i++){
			indicesArray[1] = indices.get(i);
		}

		return loader.loadToVAO(verticesArray, textureArray, indicesArray);
	}*/

	/*private  static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,List<Vector3f> normals,float[] textureArray, float[] normalsArray){

		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;

		Vector3f currentNorm =  normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;


	}*/
}
