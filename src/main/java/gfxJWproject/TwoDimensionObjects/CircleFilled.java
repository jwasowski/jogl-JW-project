package gfxJWproject.TwoDimensionObjects;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class CircleFilled extends GfxObject {

	private double pi = Math.PI;
	private int w=256;
	private float[] kVertices = new float[w*4];
	private float[] kColors = new float[w*4];

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		setupArrays();
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
		gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, 256);
		gl.glBindVertexArray(0);
		gl.glUseProgram(0);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(x, y, width, height);
		
	}
	private void setupArrays(){
		for(int i=0; i<w; i++){
			float fi = (float) (2*pi*(float)i/w);
			kVertices[4*i] = (float) Math.cos(fi) * 0.8f;
			kVertices[4*i+1] = (float)Math.sin(fi) * 0.8f;
			kVertices[4*i+2] = 0.0f;
			kVertices[4*i+3] = 1.0f;
			
			kColors[4*i] = 0.0f;
			kColors[4*i+1] = 0.4f;
			kColors[4*i+2] = 0.0f;
			kColors[4*i+3] = 1.0f;
		}}
}
