package gfxJWproject.ThreeDimensionObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.Shaders.GfxTextureShaderProgramService;

public class SpacePlane implements IGfxThreeDObject {
	private GfxTextureShaderProgramService programService;
	private int textureProgram;
	protected final int[] vertexArrayObject = new int[1];
	protected final int[] vertexBufferObject = new int[1];
	public float[] modelMatrix = new float[16];
	protected FloatBuffer fbVertices;
	protected IntBuffer ibIndices;
	private DeallocationHelper deallocator;
	protected final int[] indexBufferObject = new int[1];
	private MatrixService matrixService;
	private Texture texture;
	private int textureUnit;
	private float m;
	private float n;
	/*private float[] textureVertex ={-(float)(m),0.0f,-(float)(n),1.0f,-(float)(m),-(float)(n),
	        (float)(m),0.0f,-(float)(n),1.0f,(float)(m),-(float)(n),
	        (float)(m),0.0f,(float)(n),1.0f,(float)(m),(float)(n),
	        -(float)(m),0.0f,(float)(n),1.0f,-(float)(m),(float)(n)};*/
	private float[] textureVertex = 
		{-m, 0.0f,-n, 1.0f, 	1.0f,0.0f,1.0f,1.0f,
	       m, 0.0f,-n, 1.0f, 	1.0f,0.0f,1.0f,1.0f,
	        m, 0.0f,n, 1.0f, 	1.0f,0.0f,1.0f,1.0f,
	        -m, 0.0f,n, 1.0f, 	1.0f,0.0f,1.0f,1.0f};
	private int[] indices = { 0, 1, 3, 2};
	private IntBuffer textureName;

	public SpacePlane(GfxTextureShaderProgramService textureProgramService, MatrixService matrixService,
			DeallocationHelper deallocator, float m, float n) {
		this.programService = textureProgramService;
		this.matrixService = matrixService;
		this.deallocator = deallocator;
		this.m = m;
		this.n = n;
		textureVertex[0] = -m;
		textureVertex[2] = -n;
		textureVertex[8] = m;
		textureVertex[10] = -n;
		textureVertex[16] = m;
		textureVertex[18] = n;
		textureVertex[24] = -m;
		textureVertex[26] = n;
	}

	@Override
	public int getModelProgram() {
		// TODO Auto-generated method stub
		return textureProgram;
	}

	@Override
	public void setModelProgram(int textureProgram) {
		this.textureProgram = textureProgram;

	}
	//TODO Fix textureVertex Data, problem is with loading the data to gpu
	@Override
	public void init(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		matrixService.setupUnitMatrix(modelMatrix);
		matrixService.translate(modelMatrix, 0, -4, 0);
		gl4.glGenVertexArrays(1, vertexArrayObject, 0);
 		System.err.println("Error code: init " + gl4.glGetError());
 		gl4.glBindVertexArray(vertexArrayObject[0]);
 		// Vbo vertices Setup
 		gl4.glGenBuffers(vertexBufferObject.length, vertexBufferObject, 0);
 		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferObject[0]);
 		System.out.println("textureVertexArrayContents: ");
 		DoubleStream ds = IntStream.range(0, textureVertex.length)
                .mapToDouble(i -> textureVertex[i]);
 		System.out.println("Contents vertexData");
 		ds.forEach(System.out::println);
 		// TODO Read more on interleaved data buffers and pointers, everything
 		// seems ok
 		final long verticesBufferSizeInBytes = textureVertex.length * Buffers.SIZEOF_FLOAT;
 		fbVertices = Buffers.newDirectFloatBuffer(textureVertex);
 		//fbVertices.put(textureVertex);
 		// Stride defines how many bytes there are in one set of vertex position
 		// and texture position. That's four floats per vertex position and two floats per
 		// texture position.
 		final int stride = 8 * Buffers.SIZEOF_FLOAT;
 		// Offset defines how many bytes to skip, counted from beginning of
 		// the Stride.
 		final long textureOffset = 4 * Buffers.SIZEOF_FLOAT;
 		final long colorOffset = 4 * Buffers.SIZEOF_FLOAT;
 		System.out.println("Buffer contents:");
 		for(int i=0;i<textureVertex.length;i++){
 			System.out.println(fbVertices.get(i));
 		}
 		gl4.glBufferData(GL4.GL_ARRAY_BUFFER, verticesBufferSizeInBytes, fbVertices, GL4.GL_STATIC_DRAW);
 		gl4.glVertexAttribPointer(0, 4, GL4.GL_FLOAT, false, stride, 0);
 		gl4.glEnableVertexAttribArray(0);
 		gl4.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, stride, colorOffset);
 		gl4.glEnableVertexAttribArray(1);
 		// Indices buffer Setup
 		gl4.glGenBuffers(indexBufferObject.length, indexBufferObject, 0);
 		gl4.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject[0]);
 		ibIndices = Buffers.newDirectIntBuffer(indices);
 		final int indicesBufferSizeInBytes = indices.length * Buffers.SIZEOF_INT;
 		gl4.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indicesBufferSizeInBytes, ibIndices, GL4.GL_STATIC_DRAW);
 		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
 		gl4.glBindVertexArray(0);
		

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
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
		textureProgram = 0;
		if (programService.getProgram() != null) {
			programService.disposeProgram(gl4);
		}

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		if(gl4.glGetError() != 0){
		System.err.println("Error code: " + gl4.glGetError());}
		gl4.glUseProgram(textureProgram);
		gl4.glBindVertexArray(vertexArrayObject[0]);
		gl4.glActiveTexture(textureUnit);
		//matrixService.rotateAboutXAxis(modelMatrix, 5);
		programService.setModelMatrix(gl4, modelMatrix);
		//gl4.glActiveTexture(GL4.GL_TEXTURE0/*textureName.get(0)*/);
		//gl4.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
		texture.enable(gl4);
		texture.bind(gl4);
		
		/*gl4.glEnable(GL4.GL_CULL_FACE);
		gl4.glCullFace(GL4.GL_BACK);
	    gl4.glFrontFace(GL4.GL_CW);*/
	    gl4.glDrawElements(GL4.GL_TRIANGLE_STRIP, /*indices.length*/4 /*2*(m+1)*/, GL4.GL_UNSIGNED_INT, 0);
	    gl4.glDisable(GL4.GL_CULL_FACE);
		texture.disable(gl4);
		gl4.glBindVertexArray(0);
		gl4.glUseProgram(0);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glViewport(x, y, width, height);

	}

	@Override
	public void setTextureBuffer(IntBuffer textureName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateXAxisUp() {
		matrixService.rotateAboutXAxis(modelMatrix, -5);

	}

	@Override
	public void rotateXAxisDown() {
		matrixService.rotateAboutXAxis(modelMatrix, 5);

	}

	@Override
	public void rotateYAxisLeft() {
		matrixService.rotateAboutYAxis(modelMatrix, -5);

	}

	@Override
	public void rotateYAxisRight() {
		matrixService.rotateAboutYAxis(modelMatrix, 5);

	}

	@Override
	public void setTextureUnit(int textureUnit) {
		this.textureUnit = textureUnit;
		
	}

	@Override
	public void setTexture(Texture texture) {
		this.texture = texture;
		
	}

}
