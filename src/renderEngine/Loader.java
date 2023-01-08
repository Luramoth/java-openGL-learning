package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

	// make a list of the Vao's and Vbo's, so they can later be completely annihilated
	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<Integer>();


	// make a new vao for a 3d model
	public RawModel loadToVAO(float[] positions){
		int vaoID = createVAO();// create the VAO and bind it
		storeDataInAttributeList(0, positions);// store the data inside the VAO
		unbindVAO();// unbind it, we are done with the VAO
		return new RawModel(vaoID, positions.length/3);// make the new RawModel using our brand new VAO
	}

	// loop through the vao's and vbo's and delete them
	public void cleanUP(){
		for (int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
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
	private void storeDataInAttributeList(int attributeNumber, float[] data){
		int vboID = GL15.glGenBuffers();// generate the VBO ID
		vbos.add(vboID);// add the VBO ID to the VBO list

		// get the buffer and  bind it to the current VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);// tell openGL to use this buffer with its ID
		FloatBuffer buffer = storeDataInFloatBuffer(data); // make the data into a buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);// but that buffer into the VBO
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0,0);// tell openGL where everything is
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);// and unbind the VBO because we are done with it
	}

	// unbind the VAO when it's not needed anymore
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}

	// put the data in a FloatBuffer, so it can be later read  and used in the VBO
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);// put the data in the buffer
		buffer.flip();// flip the data so OpenGL knows to read and not to write
		return buffer;
	}
}
