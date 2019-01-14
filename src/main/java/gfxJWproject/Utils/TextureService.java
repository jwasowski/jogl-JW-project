package gfxJWproject.Utils;

import java.io.IOException;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureService {
	public IntBuffer textureName;
	private DeallocationHelper deallocator;
	//TODO Make it work for multiple textures
	public TextureService(DeallocationHelper deallocator, int numberOfTextures){
		this.deallocator = deallocator;
		if(numberOfTextures>0){
		textureName = GLBuffers.newDirectIntBuffer(numberOfTextures);}
		else {
		textureName = GLBuffers.newDirectIntBuffer(1);
		}
	}
	
	public TextureData initTexture(GL4 gl4, String url, int bufferIndex){
		try {
			
			TextureData data = TextureIO.newTextureData(gl4.getGLProfile(),
					this.getClass().getResource(url), false, TextureIO.JPG);
			int level = 0;
			gl4.glGenTextures(1, textureName);
			gl4.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(bufferIndex));
			gl4.glTexImage2D(GL4.GL_TEXTURE_2D, level, data.getInternalFormat(), data.getWidth(), data.getHeight(),
					data.getBorder(), data.getPixelFormat(), data.getPixelType(), data.getBuffer());
			/*gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
			gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, level);*/
			gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
			gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
			gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
			gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
			
			System.out.println("Texture est. size: " + data.getEstimatedMemorySize());
			System.out.println("Texture Height: " + data.getHeight());
			System.out.println("Texture Width: " + data.getWidth());
			System.out.println("Texture format: " + data.getInternalFormat());
			System.out.println("Texture border: " + data.getBorder());
			System.out.println("Texture format: " + data.getPixelFormat());
			System.out.println("Texture pixel-type: " + data.getPixelType());
			
			gl4.glBindTexture(GL4.GL_TEXTURE_2D, 0);
			
			return data;
		} catch (GLException e) {
			System.err.println("Error loading texture");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading texture");
			e.printStackTrace();
		}
		return null;
	}
	
	public void disposeTextureBuffer(){
		if ("8".equals(System.getProperty("java.version").substring(2, 3))
				|| "9".equals(System.getProperty("java.version"))) {
			deallocator.deallocate(textureName);
		} else {
			System.err.println(
					"Java version: " + System.getProperty("java.version") + " is not supported by buffer deallocator.");
		}
	}
}
