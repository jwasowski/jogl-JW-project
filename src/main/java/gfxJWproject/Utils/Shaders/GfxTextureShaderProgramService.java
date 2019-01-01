package gfxJWproject.Utils.Shaders;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GfxTextureShaderProgramService extends GfxModelShaderProgramService{
	private int textureProgram;
	private int textureUnitLocation;
	public GfxModelShaderProgramService modelShaderService = new GfxModelShaderProgramService();

	public ShaderProgram getProgram() {
		return super.getProgram();
	}

	public int initProgram(GL4 gl4) {
		textureProgram = modelShaderService.initProgram(gl4);
		textureUnitLocation = getUniformLocation("texture_unit", gl4);

		return textureProgram;
	}

	public int getUniformLocation(String name, GL4 gl4) {
		int location = -1;
		location = gl4.glGetUniformLocation(textureProgram, name);
		if (location < 0) {
			System.err.println("ERROR: Cannot find uniform location: " + name);
		}
		return location;
	}

	public void setTextureUnit(GL4 gl4, int t) {
		gl4.glUniform1i(textureUnitLocation, t);
	}

	public void disposeProgram(GL4 gl4) {
		super.disposeProgram(gl4);
	}
}
