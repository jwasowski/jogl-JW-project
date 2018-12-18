package gfxJWproject.TwoDimensionObjects;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class StarFilled extends GfxObject {
	

	private float[] kVertices = { 0.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 0.3f, 0.0f, 1.0f,
			0.1f, 0.0f, 0.0f, 1.0f,
			0.4f, 0.0f, 0.0f, 1.0f,
			0.15f, -0.2f, 0.0f, 1.0f,
			0.25f, -0.5f, 0.0f, 1.0f,
			0.0f, -0.3f, 0.0f, 1.0f,
			-0.25f, -0.5f, 0.0f,1.0f,
			-0.15f, -0.2f, 0.0f, 1.0f,
			-0.4f, -0.0f, 0.0f, 1.0f,
			-0.1f, -0.0f, 0.0f, 1.0f,
			0.0f, 0.3f, 0.0f, 1.0f
										};
	private float[] kColors = { 0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f,
			0.0f, 0.4f, 0.0f, 1.0f};
	

	
	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		System.out.println("GL_RENDERER: " + gl.glGetString(GL2.GL_RENDERER));
		System.out.println("GL_VERSION: " + gl.glGetString(GL2.GL_VERSION));
		initProgram(gl);
		createBuffer(gl, kVertices, kColors);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		clearResources(gl);
		disposeProgram(gl);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glUseProgram(program);
		gl.glBindVertexArray(vao[0]);
		gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, 12);
		gl.glBindVertexArray(0);
		gl.glUseProgram(0);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(x, y, width, height);

	}

}
