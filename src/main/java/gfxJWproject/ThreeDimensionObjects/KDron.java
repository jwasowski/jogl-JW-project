package gfxJWproject.ThreeDimensionObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.GfxModelShaderProgramService;
import gfxJWproject.Utils.GfxShaderProgramService;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.BufferObjects.ColorVertex;

public class KDron {
	public GfxModelShaderProgramService programService = new GfxModelShaderProgramService();
	//TODO Check program init value
	public int modelProgram;
	protected final int[] vertexArrayObject = new int[1];
	protected final int[] vertexBufferObject = new int[1];
	final float[] modelMatrix = new float[16];
	protected FloatBuffer fbVertices;
	protected FloatBuffer fbIndices;
	private DeallocationHelper deallocator = new DeallocationHelper();
	protected final int[] indexBufferObject = new int[1];
	private MatrixService matrixService = new MatrixService();
	// TODO Create lightweight ColorVertex objects
	private ColorVertex[] test = new ColorVertex[] {
			new ColorVertex(new float[] { -.5f, -.5f, .5f, 1.0f }, new float[] { 1, 1, 1, 1 }),
			new ColorVertex(new float[] { -.5f, .5f, .5f, 1.0f }, new float[] { 1, 0, 0, 1 }),
			new ColorVertex(new float[] { .5f, -.5f, .5f, 1.0f }, new float[] { 0, 1, 0, 1 }),
			new ColorVertex(new float[] { .5f, -.5f, .5f, 1.0f }, new float[] { 1, 1, 0, 1 }),
			new ColorVertex(new float[] { -.5f, -.5f, -.5f, 1.0f }, new float[] { 0, 0, 1, 1 }),
			new ColorVertex(new float[] { -.5f, .5f, -.5f, 1.0f }, new float[] { 1, 0, 0, 1 }),
			new ColorVertex(new float[] { .5f, .5f, -.5f, 1.0f }, new float[] { 1, 0, 1, 1 }),
			new ColorVertex(new float[] { .5f, -.5f, -.5f, 1.0f }, new float[] { 0, 0, 0, 1 }) };

	// Indexing VBOs
	private float[] kIndices = { 0, 1, 2, 0, 2, 3, 4, 0, 3, 4, 3, 7, 4, 5, 1, 4, 1, 0, 3, 2, 6, 3, 6, 7, 1, 5, 6, 1, 6, 2,
			7, 6, 5, 7, 5, 4 };
	public float[] superArray; 
	public float[] helper;
	public void init(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		System.out.println("GL_RENDERER: " + gl4.glGetString(GL4.GL_RENDERER));
		System.out.println("GL_VERSION: " + gl4.glGetString(GL4.GL_VERSION));
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		if(modelProgram == 0){
		modelProgram = programService.initProgram(gl4);}
		System.out.println("Modelprogram: "+modelProgram);
		matrixService.setupUnitMatrix(modelMatrix);
		// Voa Setup
		gl4.glGenVertexArrays(1, vertexArrayObject, 0);
		System.out.println("Error code: " + gl4.glGetError());
		gl4.glBindVertexArray(vertexArrayObject[0]);
		// Vbo vertices Setup
		gl4.glGenBuffers(vertexBufferObject.length, vertexBufferObject, 0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferObject[0]);
		createSuperArray();
		fbVertices = Buffers.newDirectFloatBuffer(superArray);

		final int verticesBufferSizeInBytesVertices = test.length * test[0].sizeOfArrays() * Buffers.SIZEOF_FLOAT;
		gl4.glBufferData(GL4.GL_ARRAY_BUFFER, verticesBufferSizeInBytesVertices, fbVertices, GL4.GL_STATIC_DRAW);
		gl4.glVertexAttribPointer(0, 4, GL4.GL_FLOAT, false, test[0].sizeOfArrays(), 0);
		gl4.glEnableVertexAttribArray(0);
		gl4.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, test[0].sizeOfArrays(), test[0].position.length);
		gl4.glEnableVertexAttribArray(1);
		// Indices buffer Setup
		gl4.glGenBuffers(indexBufferObject.length, indexBufferObject, 0);
		gl4.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject[0]);
		fbIndices = Buffers.newDirectFloatBuffer(kIndices);
		final int indicesBufferSizeInBytesVertices = indexBufferObject.length * Buffers.SIZEOF_FLOAT;
		gl4.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indicesBufferSizeInBytesVertices, fbIndices, GL4.GL_STATIC_DRAW);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		gl4.glBindVertexArray(0);
	}

	public void dispose(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glDisableVertexAttribArray(1);
		gl4.glDisableVertexAttribArray(0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		// TODO java11 solution
		deallocator.deallocate(fbIndices);
		deallocator.deallocate(fbVertices);
		//deallocator.deallocate(matrixBuffer);
		gl4.glBindVertexArray(0);
		gl4.glDeleteVertexArrays(1, vertexArrayObject, 0);
		modelProgram = 0;
		if (programService.getProgram() != null) {
			programService.disposeProgram(gl4);
		}
	}

	public void display(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		gl4.glUseProgram(modelProgram);
		gl4.glBindVertexArray(vertexArrayObject[0]);
		programService.setModelMatrix(gl4, modelMatrix);
		gl4.glDrawElements(GL4.GL_TRIANGLES, 36, GL4.GL_UNSIGNED_INT, 0);
		gl4.glBindVertexArray(0);
		gl4.glUseProgram(0);

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glViewport(x, y, width, height);
	}

	public void createSuperArray(){
		superArray = new float[test.length * test[0].sizeOfArrays()];
		helper = new float[test[0].sizeOfArrays()];
		for (int i = 0, j = 0; i < test.length * test[0].sizeOfArrays(); i = i + test.length) {
			helper = test[j].returnPair();
			for (int k = 0; k < helper.length; k++) {
				superArray[i + k] = helper[k];
			}
			if (j <= test.length)
				j++;
		}
	}

}
