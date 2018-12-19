package gfxJWproject.Utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;


public class GfxTwoDShaderProgramService {
	private ShaderProgram twoDProgram;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;
	public ShaderProgram getProgram() {
		return twoDProgram;
	}

	private GfxTwoDShaderProgramService() {

	}

	public static GfxTwoDShaderProgramService getInstance() {
		return GfxTwoDShaderProgramHolder.INSTANCE;
	}

	private static class GfxTwoDShaderProgramHolder {
		private static final GfxTwoDShaderProgramService INSTANCE = new GfxTwoDShaderProgramService();
	}
	public int initProgram(GL2 gl2) {
		if (twoDProgram == null) {
			vertexShader = ShaderCode.create(gl2, GL2.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "vertex",
					null, null, true);
			fragmentShader = ShaderCode.create(gl2, GL2.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null,
					"fragment", null, null, true);

			twoDProgram = new ShaderProgram();
			twoDProgram.add(vertexShader);
			twoDProgram.add(fragmentShader);
			twoDProgram.link(gl2, System.out);
			twoDProgram.validateProgram(gl2, System.err);
		}
		return twoDProgram.program();
	}
	
	public void disposeProgram(GL2 gl2) {
		twoDProgram.destroy(gl2);
		// Just to be sure
		twoDProgram = null;
	}
}
