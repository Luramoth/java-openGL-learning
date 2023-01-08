import org.lwjgl.opengl.Display;
import renderEngine.DisplayManger;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		DisplayManger.createDisplay();

		while (!Display.isCloseRequested()){

			//game logic
			//render!
			DisplayManger.updateDisplay();
		}

		DisplayManger.closeDisplay();
	}
}