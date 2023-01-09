package shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
public abstract class ShaderProgram {
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderProgram(String vertexFile, String fragmentFile){
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);// load and compile the vertex shader
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);// do the same for the fragment shader
		programID = GL20.glCreateProgram();// create the program and return its ID

		//attach both shaders to the program
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);

		bindAttributes();

		// link and validate
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}

	public void start(){
		GL20.glUseProgram(programID);
	}

	public void stop(){
		GL20.glUseProgram(0);
	}

	public void cleanUP(){
		stop();// make sure no programs are currently running

		// detach shaders from their programs
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);

		// delete the shaders and the program from memory
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	protected  abstract  void bindAttributes();

	@SuppressWarnings("SameParameterValue")
	protected  void bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	// open shader files
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();// try to open the file, if not possible, throw an error
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}

		int shaderID = GL20.glCreateShader(type);// create the shader

		GL20.glShaderSource(shaderID, shaderSource);// specify the source of the shader
		GL20.glCompileShader(shaderID);// ..and compile it

		// if it didn't work, throw and error
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}

		return shaderID;// return the ID of the shader
	}
}
