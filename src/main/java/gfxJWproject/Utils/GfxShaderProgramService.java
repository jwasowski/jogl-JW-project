package gfxJWproject.Utils;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxShaderProgramService {
	private ShaderProgram program;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int modelMatrixLocation;

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

	public int initProgramTwoDimension(GL2 gl2) {
		if (program == null) {
			vertexShader = ShaderCode.create(gl2, GL2.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "vertex",
					null, null, true);
			fragmentShader = ShaderCode.create(gl2, GL2.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null,
					"fragment", null, null, true);

			program = new ShaderProgram();
			program.add(vertexShader);
			program.add(fragmentShader);
			program.link(gl2, System.out);
			program.validateProgram(gl2, System.err);
		}
		return program.program();
	}

	public int initProgramThreeDimension(GL4 gl4) {
		if (program == null) {
			vertexShader = ShaderCode.create(gl4, GL4.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "3dvertex",
					null, null, true);
			fragmentShader = ShaderCode.create(gl4, GL4.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null,
					"3dfragment", null, null, true);

			program = new ShaderProgram();
			program.add(vertexShader);
			program.add(fragmentShader);
			program.link(gl4, System.out);
			program.validateProgram(gl4, System.err);
			projectionMatrixLocation = getUniformLocation("projection_matrix", gl4);
			viewMatrixLocation = getUniformLocation("view_matrix", gl4);
			modelMatrixLocation = getUniformLocation("model_matrix", gl4);
		}
		return program.program();
	}

	public void disposeProgramGL2(GL2 gl4) {
		program.destroy(gl4);
		// Just to be sure
		program = null;
	}

	public void disposeProgramGL4(GL4 gl4) {
		program.destroy(gl4);
		// Just to be sure
		program = null;
	}

	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(program.id(), name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}
//TODO Create Matrix buffer
	public void setModelMatrix(GL4 gl4, FloatBuffer matrix) {
		gl4.glUniformMatrix4fv(modelMatrixLocation, 1, false, matrix);
	}

	public void setProjectionMatrix(GL4 gl4, FloatBuffer matrix) {
		gl4.glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix);
	}

	public void setViewMatrix(GL4 gl4, FloatBuffer matrix) {
		gl4.glUniformMatrix4fv(viewMatrixLocation, 1, false, matrix);
	}
}
