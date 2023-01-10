package testgame;

import Entities.Entity;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManger;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		boolean wireframe;

		DisplayManger.createDisplay();

		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		float[] vertices = {
				-0.5f, 0.5f, 0f,	//v0
				-0.5f, -0.5f, 0f,	//v1
				0.5f, -0.5f, 0f,	//v2
				0.5f, 0.5f, 0f,		//v3
		};

		int[] indices = {
				0,1,3,	// top left triangle
				3,1,2	// bottom left right triangle
		};

		float[] textureCoords = {
				0,0,	//v0
				0,1,	//v1
				1,1,	//v2
				1,0		//v3
		};

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("lura"));
		TexturedModel texturedModel = new TexturedModel(model, texture);

		Entity entity = new Entity(texturedModel, new Vector3f(0,0,-1),0f,0f,0f,1f);

		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		while (!Display.isCloseRequested()){
			renderer.prepare();
			//game logic

			wireframe = Keyboard.isKeyDown(Keyboard.KEY_0);

			if (wireframe){
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			}else {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			}

			//render!
			entity.increasePosition(0, 0, -0.1f);
			entity.increaseRotation(100,100,100);

			shader.start();
			renderer.render(entity, shader);
			shader.stop();
			DisplayManger.updateDisplay();
		}

		shader.cleanUP();
		loader.cleanUP();
		DisplayManger.closeDisplay();
	}
}