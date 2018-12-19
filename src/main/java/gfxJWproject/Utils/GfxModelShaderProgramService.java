package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;


public class GfxModelShaderProgramService extends GfxShaderProgramService{
	private ShaderProgram modelProgram;
	private int modelMatrixLocation;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;
	public ShaderProgram getProgram() {
		return modelProgram;
	}

	private GfxModelShaderProgramService() {

	}

	public static GfxModelShaderProgramService getInstance() {
		return GfxModelShaderProgramHolder.INSTANCE;
	}

	private static class GfxModelShaderProgramHolder {
		private static final GfxModelShaderProgramService INSTANCE = new GfxModelShaderProgramService();
	}
	public int initProgram(GL4 gl4) {
		
		vertexShader = ShaderCode.create(gl4, GL4.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "3dvertex",
				null, null, true);
		fragmentShader = ShaderCode.create(gl4, GL4.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null,
				"3dfragment", null, null, true);

		modelProgram = new ShaderProgram();
		modelProgram.add(vertexShader);
		modelProgram.add(fragmentShader);
		modelProgram.link(gl4, System.out);
		modelProgram.validateProgram(gl4, System.err);
		modelMatrixLocation = getUniformLocation("model_matrix", gl4);
	
	return modelProgram.program();
}
	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(modelProgram.id(), name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}
	//TODO Check bool value
	public void setModelMatrix(GL4 gl4, float[] matrix) {
		gl4.glUniformMatrix4fv(modelMatrixLocation, 1, false, matrix, 0);
	}
	
	public void disposeProgram(GL4 gl4) {
		modelProgram.destroy(gl4);
		// Just to be sure
		modelProgram = null;
	}
}
