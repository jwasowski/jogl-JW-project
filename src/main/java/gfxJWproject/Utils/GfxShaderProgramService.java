package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public  class GfxShaderProgramService {
	private ShaderProgram program;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;
	public  ShaderProgram getProgram(){
		return program;
	}

	public  int initProgram(GL4 gl4){
		vertexShader = ShaderCode.create(gl4, GL4.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "3dvertex", null,
				null, true);
		fragmentShader = ShaderCode.create(gl4, GL4.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null, "3dfragment",
				null, null, true);
		program = new ShaderProgram();
		program.add(vertexShader);
		program.add(fragmentShader);
		program.link(gl4, System.out);
		program.validateProgram(gl4, System.err);
		System.out.println("Superprogram: "+program.id());
		return program.program();
	}

	public  void disposeProgram(GL4 gl4){
		program.destroy(gl4);
		// Just to be sure
		program = null;
	}
}
