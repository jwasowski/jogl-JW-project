package gfxJWproject.ThreeDimensionObjects;

import java.nio.IntBuffer;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;

public interface IGfxThreeDObject {
//TODO Add methods for translations etc. in future versions
	public int getModelProgram();

	public void setModelProgram(int modelProgram);

	void init(GLAutoDrawable drawable);

	void dispose(GLAutoDrawable drawable);

	void display(GLAutoDrawable drawable);

	void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);

	void setTextureBuffer(IntBuffer textureName);
	// Object rotations are inverted
	void setTextureUnit(int textureUnit);
	
	void setTexture(Texture texture);
	void rotateXAxisUp();

	void rotateXAxisDown();

	void rotateYAxisLeft();

	void rotateYAxisRight();

}