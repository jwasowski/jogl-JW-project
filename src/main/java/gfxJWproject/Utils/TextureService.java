package gfxJWproject.Utils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.ImageType;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import gfxJWproject.Utils.Shaders.GfxTextureShaderProgramService;

public class TextureService {
	public IntBuffer textureBuffer;
	private DeallocationHelper deallocator;
	private GfxTextureShaderProgramService programService;
	// TODO Fix constructor
	public TextureService(DeallocationHelper deallocator, int numberOfTextures,GfxTextureShaderProgramService programService ) {
		this.deallocator = deallocator;
		this.programService = programService;
		/*
		 * if (numberOfTextures > 0) { textureBuffer =
		 * GLBuffers.newDirectIntBuffer(numberOfTextures); } else {
		 * textureBuffer = GLBuffers.newDirectIntBuffer(1); }
		 */
	}

	public Texture/* Data */ initTexture(GL4 gl4, String url, int bufferIndex) {
		try {
			//programService.setTextureUnit(gl4, 0);
			gl4.glEnable(GL4.GL_TEXTURE_2D);
			TextureData data = TextureIO.newTextureData(gl4.getGLProfile(), this.getClass().getResource(url), false,
					/*ImageType.T_TGA*/ImageType.T_PNG);
			System.out.println(data.toString());
			/*gl4.glTexImage2D(GL4.GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getWidth(), data.getHeight(),
					data.getBorder(), data.getInternalFormat(), data.getPixelType(), data.getBuffer());*/
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-0: " + gl4.glGetError());
			}
			System.out.println("PixelFOrmat: "+data.getPixelFormat()+" IntFormat: "+data.getInternalFormat()+" PixelType: "+data.getPixelType());
			
			Texture texture = TextureIO.newTexture(data);
			
			System.out.println("TextureTarget: " + texture.getTarget() + " GL4.GL_TEXTURE_2D: " + GL4.GL_TEXTURE_2D);
			texture.setTexParameteri(gl4, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-1: " + gl4.glGetError());
			}
			texture.setTexParameteri(gl4, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-2: " + gl4.glGetError());
			}
			texture.setTexParameteri(gl4, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-3: " + gl4.glGetError());
			}
			texture.setTexParameteri(gl4, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-4: " + gl4.glGetError());
			}

			System.out.println(texture.toString());
			
			//texture.enable(gl4);
			
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-5: " + gl4.glGetError());
			}
			
			texture.bind(gl4);
			
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-6: " + gl4.glGetError());
			}
			if (gl4.glGetError() != 0 || gl4.glGetError() != GL4.GL_NO_ERROR) {
				System.err.println("Error code in TextureService-init-7: " + gl4.glGetError());
			}
			// gl4.glBindTexture(GL4.GL_TEXTURE_2D, 0);

			return texture;
		} catch (GLException e) {
			System.err.println("GLError loading texture");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOError loading texture");
			e.printStackTrace();
		}
		System.err.println("Returning null texture.");
		return null;
	}
	
	public Texture createRgbaFloatTexture(GL4 gl, FloatBuffer buffer, int width, int height) {
		programService.setTextureUnit(gl, 0);
		boolean flipVertically = false;

	    int numTextures = 1;
	    int[] textureHandles = new int[numTextures];
	    gl.glGenTextures(numTextures, textureHandles, 0);

	    final int glTextureHandle = textureHandles[0];

	    gl.glBindTexture(GL.GL_TEXTURE_2D, glTextureHandle);

	    int mipmapLevel = 0;
	    int internalFormat = 32856;
	    int numBorderPixels = 0;
	    int pixelFormat = 6408;
	    int pixelType = 5121;
	    boolean mipmap = false;
	    boolean dataIsCompressed = false;
	    gl.glTexImage2D(GL4.GL_TEXTURE_2D, mipmapLevel, internalFormat, width, height, numBorderPixels, pixelFormat, pixelType, buffer);

	    gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
	    gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
	    gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
	    gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
	    gl.glGenerateMipmap(GL4.GL_TEXTURE_2D); // Don't really need mipmaps, but we'll create anyway for now.
	    Texture texture = new Texture(glTextureHandle, GL4.GL_TEXTURE_2D, width, height, width, height, flipVertically);

	    return texture;
	  } 

	public void disposeTextureBuffer() {
		if ("8".equals(System.getProperty("java.version").substring(2, 3))
				|| "9".equals(System.getProperty("java.version"))) {
			deallocator.deallocate(textureBuffer);
		} else {
			System.err.println(
					"Java version: " + System.getProperty("java.version") + " is not supported by buffer deallocator.");
		}
	}
}
