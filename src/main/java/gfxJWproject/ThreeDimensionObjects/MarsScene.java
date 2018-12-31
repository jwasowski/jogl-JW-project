package gfxJWproject.ThreeDimensionObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GLAutoDrawable;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.Shaders.GfxModelShaderProgramService;

public class MarsScene implements IGfxThreeDObject {
	private GfxModelShaderProgramService programService;
	private int modelProgram;
	protected final int[] vertexArrayObject = new int[1];
	protected final int[] vertexBufferObject = new int[1];
	public float[] modelMatrix = new float[16];
	protected FloatBuffer fbVertices;
	protected IntBuffer ibBIndices;
	private DeallocationHelper deallocator;
	protected final int[] indexBufferObject = new int[1];
	private MatrixService matrixService;

	public MarsScene(GfxModelShaderProgramService modelProgramService, MatrixService matrixService,
			DeallocationHelper deallocator) {
		this.programService = modelProgramService;
		this.matrixService = matrixService;
		this.deallocator = deallocator;
	}

	@Override
	public int getModelProgram() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setModelProgram(int modelProgram) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateXAxisUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateXAxisDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateYAxisLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateYAxisRight() {
		// TODO Auto-generated method stub

	}

}
