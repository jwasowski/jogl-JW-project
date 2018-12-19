package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxCameraShaderProgramService extends GfxShaderProgramService {
	private ShaderProgram cameraProgram;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private ShaderCode vertexShader;
	private ShaderCode fragmentShader;

	public ShaderProgram getProgram() {
		return cameraProgram;
	}

	private GfxCameraShaderProgramService() {

	}

	public static GfxCameraShaderProgramService getInstance() {
		return GfxCameraShaderProgramHolder.INSTANCE;
	}

	private static class GfxCameraShaderProgramHolder {
		private static final GfxCameraShaderProgramService INSTANCE = new GfxCameraShaderProgramService();
	}
	@Override
	public int initProgram(GL4 gl4) {

		vertexShader = ShaderCode.create(gl4, GL4.GL_VERTEX_SHADER, this.getClass(), "shaders", null, "3dvertex", null,
				null, true);
		fragmentShader = ShaderCode.create(gl4, GL4.GL_FRAGMENT_SHADER, this.getClass(), "shaders", null, "3dfragment",
				null, null, true);

		cameraProgram = new ShaderProgram();
		cameraProgram.add(vertexShader);
		cameraProgram.add(fragmentShader);
		cameraProgram.link(gl4, System.out);
		cameraProgram.validateProgram(gl4, System.err);
		projectionMatrixLocation = getUniformLocation("projection_matrix", gl4);
		viewMatrixLocation = getUniformLocation("view_matrix", gl4);

		return cameraProgram.program();
	}

	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(cameraProgram.id(), name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}

	public void disposeProgram(GL4 gl4) {
		cameraProgram.destroy(gl4);
		// Just to be sure
		cameraProgram = null;
	}
	//TODO Check bool value
	public void setProjectionMatrix(GL4 gl4, float[] matrix) {
		gl4.glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix, 0);
	}
	//TODO Check bool value
	public void setViewMatrix(GL4 gl4, float[] matrix) {
		gl4.glUniformMatrix4fv(viewMatrixLocation, 1, false, matrix, 0);
	}
	
}
