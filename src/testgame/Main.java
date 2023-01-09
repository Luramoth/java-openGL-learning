package testgame;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManger;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		DisplayManger.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

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

		while (!Display.isCloseRequested()){
			renderer.prepare();
			//game logic
			//render!
			shader.start();
			renderer.render(texturedModel);
			shader.stop();
			DisplayManger.updateDisplay();
		}

		shader.cleanUP();
		loader.cleanUP();
		DisplayManger.closeDisplay();
	}
}