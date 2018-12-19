package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public abstract class GfxShaderProgramService {

	public abstract ShaderProgram getProgram();

	public abstract int initProgram(GL4 gl4);

	public abstract void disposeProgram(GL4 gl4);

	public int getUniformLocation(String name, ShaderProgram program, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(program.id(), name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}

	
}
