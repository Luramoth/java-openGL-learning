package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

	// prepare the frame by clearing it first with a refreshing aggressive magenta!
	public void prepare(){
		GL11.glClearColor(1,0,1,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	public void render(RawModel model){
		GL30.glBindVertexArray(model.getVaoID());// grab the VAO ID and bind it because we want to do something with it
		GL20.glEnableVertexAttribArray(0);// we enable the vertex attrib array because it's time to read motherfucker!
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0 , model.getVertexCount());// Render!
		GL20.glDisableVertexAttribArray(0);// we are done, disable the attrib list
		GL30.glBindVertexArray(0);// unbind the VAo because we are once again done using it
	}
}
