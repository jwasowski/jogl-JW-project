package gfxJWproject.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxModelShaderProgramService extends GfxCameraShaderProgramService {
	private int modelProgram;
	private int modelMatrixLocation;

	public GfxCameraShaderProgramService cameraShaderService = new GfxCameraShaderProgramService();

	public ShaderProgram getProgram() {
		return super.getProgram();
	}

	public int initProgram(GL4 gl4) {
		modelProgram = cameraShaderService.initProgram(gl4);
		modelMatrixLocation = getUniformLocation("modelMatrix", gl4);

		return modelProgram;
	}

	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(modelProgram, name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}

	// TODO Check bool value
	public void setModelMatrix(GL4 gl4, float[] matrix) {
		gl4.glUniformMatrix4fv(modelMatrixLocation, 1, false, matrix, 0);
	}

	public void disposeProgram(GL4 gl4) {
		super.disposeProgram(gl4);
	}
}
