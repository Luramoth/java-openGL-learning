package testgame;

import Entities.Camera;
import Entities.Entity;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManger;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
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


		RawModel model = null;
		try {
			model = OBJLoader.loadOBJ("monkeytest", loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ModelTexture texture = new ModelTexture(loader.loadTexture("colorGrid"));
		TexturedModel texturedModel = new TexturedModel(model, texture);

		Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50),0f,0f,0f,1f);

		Camera camera = new Camera();

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
			//entity.increasePosition(0, 0, 0);
			//entity.increaseRotation(1,1,0);

			camera.move();

			shader.start();

			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);


			shader.stop();
			DisplayManger.updateDisplay();
		}

		shader.cleanUP();
		loader.cleanUP();
		DisplayManger.closeDisplay();
	}
}