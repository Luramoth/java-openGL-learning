import org.lwjgl.opengl.Display;
import renderEngine.DisplayManger;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		DisplayManger.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();

		float[] vertices = {
				// left bottom triangle
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				// right top triangle
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f
		};

		RawModel model = loader.loadToVAO(vertices);

		while (!Display.isCloseRequested()){
			renderer.prepare();
			//game logic
			//render!
			renderer.render(model);
			DisplayManger.updateDisplay();
		}

		loader.cleanUP();
		DisplayManger.closeDisplay();
	}
}