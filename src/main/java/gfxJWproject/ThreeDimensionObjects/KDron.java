package gfxJWproject.ThreeDimensionObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.Shaders.GfxModelShaderProgramService;

public class KDron implements IGfxThreeDObject {
	private GfxModelShaderProgramService programService;
	
	private int modelProgram;
	protected final int[] vertexArrayObject = new int[1];
	protected final int[] vertexBufferObject = new int[1];
	public float[] modelMatrix = new float[16];
	protected FloatBuffer fbVertices;
	protected IntBuffer ibIndices;
	private DeallocationHelper deallocator;
	protected final int[] indexBufferObject = new int[1];
	private MatrixService matrixService;

	public KDron(GfxModelShaderProgramService modelProgramService, MatrixService matrixService, DeallocationHelper deallocator){
		this.programService = modelProgramService;
		this.matrixService = matrixService;
		this.deallocator = deallocator;
	}
	
	public int getModelProgram() {
		return modelProgram;
	}

	public void setModelProgram(int modelProgram) {
		this.modelProgram = modelProgram;
	}

	// Indexing VBOs
	private float[] vertexColorArray = { -1.0f, -1.0f, 1.0f, 1.0f, 1, 1, 1, 1, -1.0f, 0.0f, 1.0f, 1.0f, 1, 0, 0, 1,
			-1.0f, 0.0f, 0.0f, 1.0f, 0, 1, 0, 1, -1.0f, 0.0f, -1.0f, 1.0f, 1, 1, 0, 1, 0.0f, 1.0f, -1.0f, 1.0f, 0, 0, 1,
			1, 1.0f, 0.0f, -1.0f, 1.0f, 1, 0, 0, 1, 1.0f, 0.0f, 0.0f, 1.0f, 1, 0, 1, 1, 1.0f, -1.0f, -1.0f, 1.0f, 0, 1,
			0, 1, 1.0f, 0.0f, 1.0f, 1.0f, 1, 0, 1, 1, -1.0f, -1.0f, -1.0f, 1.0f, 1, 1, 1, 1, 1.0f, -1.0f, 1.0f, 1.0f, 0,
			1, 1, 1, 0.0f, -1.0f, 1.0f, 1.0f, 0, 0, 1, 1 };
	private int[] kIndices = { 0, 1, 2, 0, 2, 9, 2, 3, 9, 2, 3, 4, 2, 4, 6, 4, 6, 5, 5, 4, 3, 3, 5, 9, 5, 9, 7, 10, 8,
			6, 10, 6, 7, 6, 5, 7, 1, 0, 11, 8, 10, 11, 1, 2, 11, 6, 7, 11, 0, 9, 7, 7, 0, 10, 2, 6, 11, 8, 6, 11 };

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#init(com.jogamp.opengl.GLAutoDrawable)
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		//TODO Check if there is a need for change in buffers model, when there will be more 3D objects
		final GL4 gl4 = drawable.getGL().getGL4();
		System.out.println("GL_RENDERER: " + gl4.glGetString(GL4.GL_RENDERER));
		System.out.println("GL_VERSION: " + gl4.glGetString(GL4.GL_VERSION));
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		System.out.println("Modelprogram: " + modelProgram);
		matrixService.setupUnitMatrix(modelMatrix);
		System.out.println("Model matrix: " + Arrays.toString(modelMatrix));
		// Voa Setup
		gl4.glGenVertexArrays(1, vertexArrayObject, 0);
		System.err.println("Error code: " + gl4.glGetError());
		gl4.glBindVertexArray(vertexArrayObject[0]);
		// Vbo vertices Setup
		gl4.glGenBuffers(vertexBufferObject.length, vertexBufferObject, 0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferObject[0]);
		fbVertices = Buffers.newDirectFloatBuffer(vertexColorArray);
		// TODO Read more on interleaved data buffers and pointers, everything
		// seems ok
		final long verticesBufferSizeInBytes = vertexColorArray.length * Buffers.SIZEOF_FLOAT;
		// Stride defines how many bytes there are in one set of vertex position
		// and color. That's four floats per vertex position and four floats per
		// color.
		final int stride = 8 * Buffers.SIZEOF_FLOAT;
		// Offset defines how many bytes to skip, counted from beginning of
		// the Stride.
		final long colorOffset = 4 * Buffers.SIZEOF_FLOAT;
		gl4.glBufferData(GL4.GL_ARRAY_BUFFER, verticesBufferSizeInBytes, fbVertices, GL4.GL_STATIC_DRAW);
		gl4.glVertexAttribPointer(0, 4, GL4.GL_FLOAT, false, stride, 0);
		gl4.glEnableVertexAttribArray(0);
		gl4.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, stride, colorOffset);
		gl4.glEnableVertexAttribArray(1);
		// Indices buffer Setup
		gl4.glGenBuffers(indexBufferObject.length, indexBufferObject, 0);
		gl4.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject[0]);
		ibIndices = Buffers.newDirectIntBuffer(kIndices);
		final int indicesBufferSizeInBytes = kIndices.length * Buffers.SIZEOF_INT;
		gl4.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indicesBufferSizeInBytes, ibIndices, GL4.GL_STATIC_DRAW);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		gl4.glBindVertexArray(0);
	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#dispose(com.jogamp.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Find better way to deallocate buffers
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glDisableVertexAttribArray(1);
		gl4.glDisableVertexAttribArray(0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		if ("8".equals(System.getProperty("java.version").substring(2, 3))
				|| "9".equals(System.getProperty("java.version"))) {
			deallocator.deallocate(ibIndices);
			deallocator.deallocate(fbVertices);
		} else {
			System.err.println(
					"Java version: " + System.getProperty("java.version") + " is not supported by buffer deallocator.");
		}
		gl4.glBindVertexArray(0);
		gl4.glDeleteVertexArrays(1, vertexArrayObject, 0);
		modelProgram = 0;
		if (programService.getProgram() != null) {
			programService.disposeProgram(gl4);
		}
	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#display(com.jogamp.opengl.GLAutoDrawable)
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		gl4.glUseProgram(modelProgram);
		gl4.glBindVertexArray(vertexArrayObject[0]);
		programService.setModelMatrix(gl4, modelMatrix);
		gl4.glDrawElements(GL4.GL_TRIANGLES, kIndices.length, GL4.GL_UNSIGNED_INT, 0);
		gl4.glBindVertexArray(0);
		gl4.glUseProgram(0);

	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#reshape(com.jogamp.opengl.GLAutoDrawable, int, int, int, int)
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glViewport(x, y, width, height);
	}
// Object rotations are inverted
	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#rotateXAxisUp()
	 */
	@Override
	public void rotateXAxisUp() {
		matrixService.rotateAboutXAxis(modelMatrix, -5);
	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#rotateXAxisDown()
	 */
	@Override
	public void rotateXAxisDown() {
		matrixService.rotateAboutXAxis(modelMatrix, 5);
	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#rotateYAxisLeft()
	 */
	@Override
	public void rotateYAxisLeft() {
		matrixService.rotateAboutYAxis(modelMatrix, -5);
	}

	/* (non-Javadoc)
	 * @see gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject#rotateYAxisRight()
	 */
	@Override
	public void rotateYAxisRight() {
		matrixService.rotateAboutYAxis(modelMatrix, 5);
	}

}
