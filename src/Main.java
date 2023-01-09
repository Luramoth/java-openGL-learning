import org.lwjgl.opengl.Display;
import renderEngine.DisplayManger;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		DisplayManger.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		float[] vertices = {
				-0.5f, 0.5f, 0f,//v0
				-0.5f, -0.5f, 0f,//v1
				0.5f, -0.5f, 0f,//v2
				0.5f, 0.5f, 0f,//v3
		};

		int[] indices = {
				0,1,3,// top left triangle
				3,1,2// bottom left right triangle
		};

		RawModel model = loader.loadToVAO(vertices, indices);

		while (!Display.isCloseRequested()){
			renderer.prepare();
			//game logic
			//render!
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManger.updateDisplay();
		}

		shader.cleanUP();
		loader.cleanUP();
		DisplayManger.closeDisplay();
	}
}