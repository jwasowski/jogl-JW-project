package gfxJWproject.ThreeDimensionObjects;

import com.jogamp.opengl.GLAutoDrawable;

public interface IGfxThreeDObject {
//TODO Add methods for translations etc. in future versions
	public int getModelProgram();

	public void setModelProgram(int modelProgram);

	void init(GLAutoDrawable drawable);

	void dispose(GLAutoDrawable drawable);

	void display(GLAutoDrawable drawable);

	void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);

	// Object rotations are inverted
	void rotateXAxisUp();

	void rotateXAxisDown();

	void rotateYAxisLeft();

	void rotateYAxisRight();

}