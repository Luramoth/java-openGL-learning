package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// a bit of explanation so that I remember, a VAO is a list of VBO's and a VBO is a list of data that defines a model
//an indices, or index buffer, is a buffer full of ints to specify which vertices connect to which vertices in a VBO

@SuppressWarnings({"FieldMayBeFinal", "Convert2Diamond"})
public class Loader {

	// make a list of the Vao's and Vbo's, so they can later be completely annihilated
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();


	// make a new vao for a 3d model
	public RawModel loadToVAO(float[] positions,float[] textureCoords,int[] incises){
		int vaoID = createVAO();// create the VAO and bind it
		bindIndicesBuffer(incises);
		storeDataInAttributeList(0, 3,  positions);// store position data in vao
		storeDataInAttributeList(1,2,textureCoords);// store UV data in VAO
		unbindVAO();// unbind it, we are done with the VAO
		return new RawModel(vaoID, incises.length);// make the new RawModel using our brand new VAO
	}

	public int loadTexture(String fileName){
		Texture texture;
		try {
			texture = TextureLoader.getTexture("PNG", Files.newInputStream(Paths.get("res/" + fileName + ".png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}

	// loop through the vao's and vbo's and delete them
	public void cleanUP(){
		for (int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}

	// actually create the VAO
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();// generate the VAO ID
		vaos.add(vaoID);// add it the VAOS list, so it can be kept track of
		GL30.glBindVertexArray(vaoID);// bind the array so it can be worked on
		return vaoID;
	}

	// create the VBO, so it can be later added to the VAO
	private void storeDataInAttributeList(int attributeNumber,int coordinateSize, float[] data){
		int vboID = GL15.glGenBuffers();// generate the VBO ID
		vbos.add(vboID);// add the VBO ID to the VBO list

		// get the buffer and  bind it to the current VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);// tell openGL to use this buffer with its ID
		FloatBuffer buffer = storeDataInFloatBuffer(data); // make the data into a buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);// but that buffer into the VBO
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0,0);// tell openGL where everything is
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);// and unbind the VBO because we are done with it
	}

	// unbind the VAO when it's not needed anymore
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}


	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();// make vbo
		vbos.add(vboID);// put it in the list

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,vboID);// tell openGL to use this as the indices array
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	// put the data in a FloatBuffer, so it can be later read  and used in the VBO
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);// put the data in the buffer
		buffer.flip();// flip the data so OpenGL knows to read and not to write
		return buffer;
	}
}
