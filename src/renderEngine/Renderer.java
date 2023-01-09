package renderEngine;

import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

	// prepare the frame by clearing it first with a refreshing aggressive magenta!
	public void prepare(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1,0,1,1);
	}

	public void render(TexturedModel texturedModel){
		RawModel model = texturedModel.getRawModel();

		GL30.glBindVertexArray(model.getVaoID());// grab the VAO ID and bind it because we want to do something with it
		GL20.glEnableVertexAttribArray(0);// we enable the vertex attrib array because it's time to read motherfucker!

		GL20.glEnableVertexAttribArray(1);// enable the texture attrib array
		GL13.glActiveTexture(GL13.GL_TEXTURE0);// set the active te GL_TEXTURE0, otherwise known as the texture that the shader will pick up
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());// bind the texture to the model

		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);// we are done, disable the attrib list
		GL20.glDisableVertexAttribArray(1);// done with texture coords as well, disable it
		GL30.glBindVertexArray(0);// unbind the VAO because we are once again done using it
	}
}
