package shaders;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	// function to tell the shader what to set the inputs to
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
}
