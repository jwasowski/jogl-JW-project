package gfxJWproject.ThreeDimensionObjects;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.Shaders.GfxModelShaderProgramService;
import gfxJWproject.Utils.Shaders.GfxTextureShaderProgramService;

public class MarsScene implements IGfxThreeDObject {
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
	private final int m;
	private final int n;
	private float r;
	private float R;
	private float[] textureVertex;
	private int[] indices;
	private IntBuffer textureBuffer;
	

	public MarsScene(GfxTextureShaderProgramService textureProgramService, MatrixService matrixService,
			DeallocationHelper deallocator, int m, int n, float r, float R) {
		this.programService = textureProgramService;
		this.matrixService = matrixService;
		this.deallocator = deallocator;
		this.m=m;
		this.n=n;
		this.r=r;
		this.R=R;
		//TODO Remember to check the size of array
		textureVertex = new float[((m+1)*(n+1))*6]; //651*6
		indices= new int[2*n*(m + 1)];
	}

	@Override
	public int getModelProgram() {
		return textureProgram;
	}

	@Override
	public void setModelProgram(int textureProgram) {
		this.textureProgram = textureProgram;

	}
	@Override
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	@Override
	public void setTextureUnit(int textureUnit) {
		this.textureUnit = textureUnit;
	}
	@Override
	public void setTextureBuffer(IntBuffer textureBuffer) {
		this.textureBuffer = textureBuffer;
	}

	
	public void init(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		int m_ = m, n_=n;
		System.out.println("GL_RENDERER: " + gl4.glGetString(GL4.GL_RENDERER));
		System.out.println("GL_VERSION: " + gl4.glGetString(GL4.GL_VERSION));
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		matrixService.setupUnitMatrix(modelMatrix);
		System.out.println("Model matrix-init: " + Arrays.toString(modelMatrix));
		matrixService.scale(modelMatrix, 2f, 2f, 2f);
		int counter = 0;
		for (int i=0;i<=n_;i++) {
		      float phi=(float) ( (-Math.PI/2)+(Math.PI*i)/(float)n);
		      for (int j=0;j<=m_;j++){
		        float theta=(float) (2*Math.PI/m_*j);
		       //TODO Fix this algorithm data assignment
		        textureVertex[counter++]=(float) ((R*Math.cos(phi))*Math.sin(theta));
		        textureVertex[counter++]=(float) (R*Math.sin(phi));
		        textureVertex[counter++]=(float) ((R*Math.cos(phi))*Math.cos(theta));
		        textureVertex[counter++]=1.0f;
		        textureVertex[counter++]=(float)j/(float)m_;
		        textureVertex[counter++]=(float)i/(float)n_;
		       /* textureVertex[counter++]=0.25f;
		        textureVertex[counter++]=0.75f;
		        textureVertex[counter++]=0.35f;
		        textureVertex[counter++]=0.0f;*/
		      }
		    }
		
		System.out.println("TextureVertex-init: "+Arrays.toString(textureVertex));
		int k=0;
		int counter2=0;
	    for(int i=0; i<=n_ - 1; i++){
	        for(int j=0; j<=m_; j++){
	          indices[2*(i*(m_ + 1)+j)]=k;
	          indices[2*(i*(m_ + 1)+j)+1]=k+m_+1;
	          k++;
	        }
	      }
	    System.out.println("Indices-init: "+Arrays.toString(indices));
	 // Voa Setup
	 		gl4.glGenVertexArrays(1, vertexArrayObject, 0);
	 		if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
				System.err.println("Error code in MarsScene-init-1: " + gl4.glGetError());}
	 		gl4.glBindVertexArray(vertexArrayObject[0]);
	 		// Vbo vertices Setup
	 		gl4.glGenBuffers(vertexBufferObject.length, vertexBufferObject, 0);
	 		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferObject[0]);
	 		fbVertices = Buffers.newDirectFloatBuffer(textureVertex);
	 		// TODO Read more on interleaved data buffers and pointers, everything
	 		// seems ok
	 		final long verticesBufferSizeInBytes = textureVertex.length * Buffers.SIZEOF_FLOAT;
	 		// Stride defines how many bytes there are in one set of vertex position
	 		// and texture position. That's four floats per vertex position and two floats per
	 		// texture position.
	 		final int stride = 6 * Buffers.SIZEOF_FLOAT;
	 		// Offset defines how many bytes to skip, counted from beginning of
	 		// the Stride.
	 		final long textureOffset = 4 * Buffers.SIZEOF_FLOAT;
	 		final long colorOffset = 6 * Buffers.SIZEOF_FLOAT;
	 		gl4.glBufferData(GL4.GL_ARRAY_BUFFER, verticesBufferSizeInBytes, fbVertices, GL4.GL_STATIC_DRAW);
	 		gl4.glVertexAttribPointer(0, 4, GL4.GL_FLOAT, false, stride, 0);
	 		gl4.glEnableVertexAttribArray(0);
	 		/*gl4.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, stride, colorOffset);
	 		gl4.glEnableVertexAttribArray(1);*/
	 		gl4.glVertexAttribPointer(2, 2, GL4.GL_FLOAT, false, stride, textureOffset);
	 		gl4.glEnableVertexAttribArray(2);
	 		// Indices buffer Setup
	 		gl4.glGenBuffers(indexBufferObject.length, indexBufferObject, 0);
	 		gl4.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject[0]);
	 		ibIndices = Buffers.newDirectIntBuffer(indices);
	 		final int indicesBufferSizeInBytes = indices.length * Buffers.SIZEOF_INT;
	 		gl4.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indicesBufferSizeInBytes, ibIndices, GL4.GL_STATIC_DRAW);
	 		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
	 		gl4.glBindVertexArray(0);
	 		
	 		if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
	 			System.err.println("Error code in MarsScene-init-2: " + gl4.glGetError());}
	 		System.out.println("Vertices buffer capacity in Bytes: "+ fbVertices.capacity()*Buffers.SIZEOF_FLOAT);
	 		

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		texture.destroy(gl4);
		gl4.glDisableVertexAttribArray(2);
		//gl4.glDisableVertexAttribArray(1);
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
		
		
		
		gl4.glBindVertexArray(vertexArrayObject[0]);
		gl4.glUseProgram(textureProgram);
		programService.setModelMatrix(gl4, modelMatrix);
		gl4.glActiveTexture(textureUnit);
		if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
		System.err.println("Error code in MarsScene-display-1: " + gl4.glGetError());}
		//gl4.glBindTexture(GL4.GL_TEXTURE_2D, textureBuffer.get(0));
		texture.enable(gl4);
		if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
			System.err.println("Error code in MarsScene-display-2: " + gl4.glGetError());}
		texture.bind(gl4);
		if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
			System.err.println("Error code in MarsScene-display-3: " + gl4.glGetError());}
		//programService.setTextureUnit(gl4, textureUnit);
		gl4.glEnable(GL4.GL_CULL_FACE);
		gl4.glCullFace(GL4.GL_BACK);
	    gl4.glFrontFace(GL4.GL_CW);
	    gl4.glDrawElements(GL4.GL_TRIANGLE_STRIP, indices.length, GL4.GL_UNSIGNED_INT, 0);
	    if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
			System.err.println("Error code in MarsScene-display-4: " + gl4.glGetError());}
	    gl4.glDisable(GL4.GL_CULL_FACE);
	    texture.disable(gl4);
	    if(gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR){
			System.err.println("Error code in MarsScene-display-5: " + gl4.glGetError());}
		gl4.glBindVertexArray(0);
		gl4.glUseProgram(0);
		

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		gl4.glViewport(x, y, width, height);
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


}
