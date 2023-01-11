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

		obj = ObjUtils.convertToRenderable(obj);

		System.out.println("number of faces: " + obj.getNumFaces());

		int[] faceVertexIndices = ObjData.getFaceVertexIndicesArray(obj);

		// index vertices, normals, and texture coordinates
		float vertices[] = ObjData.getVerticesArray(obj);
		float texCoords[] = ObjData.getTexCoordsArray(obj, 2);
		float normals[] = ObjData.getNormalsArray(obj);

		//flip UV coords on the Y axis BECAUSE THIS DANG LOADER DOESENT DO IT FOR YTIOYIROUTOJ NKMNDKSNGDKLSNLKFDSANKLJFLK
		for (int i = 1; i < texCoords.length; i += 2){
			texCoords[i] = 1 - texCoords[i];
		}

		return loader.loadToVAO(vertices, texCoords, normals, faceVertexIndices);
	}
}
