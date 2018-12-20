package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxCameraShaderProgramService extends GfxShaderProgramService {
	private int cameraProgram;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private GfxShaderProgramService shaderService = new GfxShaderProgramService();

	public ShaderProgram getProgram() {
		return super.getProgram();
	}

	public int initProgram(GL4 gl4) {
		cameraProgram = shaderService.initProgram(gl4);
		System.out.println("Cameraprogram: " + cameraProgram);
		projectionMatrixLocation = getUniformLocation("projectionMatrix", gl4);
		viewMatrixLocation = getUniformLocation("viewMatrix", gl4);

		return cameraProgram;
	}

	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(cameraProgram, name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}

	public void disposeProgram(GL4 gl4) {
		super.disposeProgram(gl4);
	}

	// TODO Check bool value
	public void setProjectionMatrix(GL4 gl4, float[] matrix, int program) {
		gl4.glUseProgram(program);
		gl4.glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix, 0);
	}

	// TODO Check bool value
	public void setViewMatrix(GL4 gl4, float[] matrix, int program) {
		gl4.glUseProgram(program);
		gl4.glUniformMatrix4fv(viewMatrixLocation, 1, false, matrix, 0);
	}

}
