package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManger {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

	public static void createDisplay(){

		ContextAttribs attribs =  new ContextAttribs(3,2);
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat());
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}

		GL11.glViewport(0,0,WIDTH,HEIGHT);
	}

	public static void updateDisplay(){}

	public static void closeDisplay(){

		Display.destroy();
	}
}
