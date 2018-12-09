package gfxJWproject.Utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxShaderProgramService {
	private ShaderProgram program;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;

	public ShaderProgram getProgram() {
		return program;
	}

	private GfxShaderProgramService() {

	}

	public static GfxShaderProgramService getInstance() {
		return GfxShaderProgramHolder.INSTANCE;
	}

	private static class GfxShaderProgramHolder {
		private static final GfxShaderProgramService INSTANCE = new GfxShaderProgramService();
	}

	public int initProgram(GL2 gl4) {
		if (program == null) {
			vertexShader = ShaderCode.create(gl4, GL2.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "vertex",
					null, null, true);
			fragmentShader = ShaderCode.create(gl4, GL2.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null,
					"fragment", null, null, true);

			program = new ShaderProgram();
			program.add(vertexShader);
			program.add(fragmentShader);
			program.link(gl4, System.out);
			program.validateProgram(gl4, System.err);
		}
		return program.program();
	}

	public void disposeProgram(GL2 gl4) {
		/*gl4.glUseProgram(0);
		gl4.glDetachShader(program.id(), vertexShader.id());
		gl4.glDetachShader(program.id(), fragmentShader.id());
		gl4.glDeleteShader(vertexShader.id());
		gl4.glDeleteShader(fragmentShader.id());
		gl4.glDeleteProgram(program.id());*/
		program.destroy(gl4);
		// Just to be sure
		program = null;
		
	}
}
