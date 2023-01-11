package renderEngine;

import Entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	private Matrix4f projectionMatrix;

	public Renderer(StaticShader shader) {
		createProjectionMatrix();

		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	// prepare the frame by clearing it first with a refreshing aggressive magenta!
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(1,0,1,1);
	}

	public void render(Entity entity, StaticShader shader){
		TexturedModel texturedModel = entity.getModel();
		RawModel rawModel = texturedModel.getRawModel();

		GL30.glBindVertexArray(rawModel.getVaoID());// grab the VAO ID and bind it because we want to do something with it
		GL20.glEnableVertexAttribArray(0);// we enable the vertex attrib array because it's time to read motherfucker!
		GL20.glEnableVertexAttribArray(1);// enable the texture attrib array
		GL20.glEnableVertexAttribArray(2);// enable normal attrib array

		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());

		shader.loadTransformationMatrix(transformationMatrix);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);// set the active te GL_TEXTURE0, otherwise known as the texture that the shader will pick up
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());// bind the texture to the model

		// Render!
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(0);// we are done, disable the attrib list
		GL20.glDisableVertexAttribArray(1);// done with texture coords as well, disable it
		GL20.glDisableVertexAttribArray(2);// and normals, we don't need normals where we're going
		GL30.glBindVertexArray(0);// unbind the VAO because we are once again done using it
	}

	private void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
