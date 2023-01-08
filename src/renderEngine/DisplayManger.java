package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManger {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 60;

	public static void createDisplay(){

		// add come settings to OopenGL as attributes of the display manager such as version, wether or not it's compatable with future versions
		// and the fact it uses the core profile instead of compatability
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

		// try to create a display, and if you cant, throw an error
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));// make the display
			Display.create(new PixelFormat(),attribs);// create the display with our new settings
			Display.setTitle("yeah");// set the title to something very important
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}

		GL11.glViewport(0,0,WIDTH,HEIGHT);// tell opengl to use our display
	}

	public static void updateDisplay(){
		Display.sync(FPS_CAP);
		Display.update();
	}

	public static void closeDisplay(){

		Display.destroy();
	}
}
