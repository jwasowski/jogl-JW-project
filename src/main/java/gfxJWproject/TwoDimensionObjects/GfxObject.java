package gfxJWproject.TwoDimensionObjects;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.GfxShaderProgramService;

public class GfxObject  {

	protected final int[] vao = new int[1];
	protected final int[] vboVertex = new int[1];
	protected final int[] vboColor = new int[1];
	private GfxShaderProgramService programService = GfxShaderProgramService.getInstance();
	protected int program;
	protected int valuesPerVertex = 4;
	private FloatBuffer fbVertices;
	private FloatBuffer fbColors;
	private DeallocationHelper deallocator = new DeallocationHelper();

	
	public void init(GLAutoDrawable drawable) {
		
	}

	
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void initProgram(GL2 gl4) {
		program = programService.initProgramTwoDimension(gl4);
	}
	
	public void disposeProgram(GL2 gl4){
		program = 0;
		if(programService.getProgram() != null){
		programService.disposeProgramGL2(gl4);}
	}

	public void clearResources(GL2 gl4) {
		
		gl4.glDisableVertexAttribArray(1);
		gl4.glDisableVertexAttribArray(0);
		gl4.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
		//TODO find a solution for java11
		deallocator.deallocate(fbColors);
		deallocator.deallocate(fbVertices);
				//gl4.glDeleteBuffers(1, fbColors);
		//gl4.glDeleteBuffers(1, null /*fbVertices*/);

		gl4.glBindVertexArray(0);
		gl4.glDeleteVertexArrays(1, vao,0);
	}

	public void createBuffer(final GL2 gl4Interface,
			float[] kVertices, float[] kColors) {
		// Voa Setup
		gl4Interface.glGenVertexArrays(/*vao.length*/1, vao, 0);
		System.out.println("Error code: "+gl4Interface.glGetError());
		gl4Interface.glBindVertexArray(vao[0]);
		// Vbo vertices Setup
		gl4Interface.glGenBuffers(vboVertex.length, vboVertex, 0);
		gl4Interface.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboVertex[0]);
		fbVertices = Buffers.newDirectFloatBuffer(kVertices);
		final int bufferSizeInBytesVertices = kVertices.length * Buffers.SIZEOF_FLOAT;
		gl4Interface.glBufferData(GL2.GL_ARRAY_BUFFER, bufferSizeInBytesVertices, fbVertices, GL2.GL_STATIC_DRAW);
		gl4Interface.glVertexAttribPointer(0, valuesPerVertex, GL2.GL_FLOAT, false, 0, 0);
		gl4Interface.glEnableVertexAttribArray(0);
		// Vbo colors Setup
		gl4Interface.glGenBuffers(vboColor.length, vboColor, 0);
		gl4Interface.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboColor[0]);
		fbColors = Buffers.newDirectFloatBuffer(kColors);
		final int bufferSizeInBytesColors = kColors.length * Buffers.SIZEOF_FLOAT;
		gl4Interface.glBufferData(GL2.GL_ARRAY_BUFFER, bufferSizeInBytesColors, fbColors, GL2.GL_STATIC_DRAW);
		gl4Interface.glVertexAttribPointer(1, valuesPerVertex, GL2.GL_FLOAT, false, 0, 0);
		gl4Interface.glEnableVertexAttribArray(1);

		gl4Interface.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
		gl4Interface.glBindVertexArray(0);
	}

}
