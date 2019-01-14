package gfxJWproject.Application;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject;
import gfxJWproject.ThreeDimensionObjects.KDron;
import gfxJWproject.ThreeDimensionObjects.MarsScene;
import gfxJWproject.ThreeDimensionObjects.SpacePlane;
import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.TextureService;
import gfxJWproject.Utils.InputControllers.InputController;
import gfxJWproject.Utils.InputControllers.OrthoController;
import gfxJWproject.Utils.InputControllers.ProjectionController;
import gfxJWproject.Utils.Shaders.GfxModelShaderProgramService;
import gfxJWproject.Utils.Shaders.GfxTextureShaderProgramService;

public class NewtWindowMars implements GLEventListener, KeyListener {
	private final GLProfile glp;
	// Specifies a set of OpenGL capabilities, based on your profile.
	private GLCapabilities caps;
	// Create the OpenGL rendering canvas
	private final GLWindow window;
	final private FPSAnimator animator;
	private IGfxThreeDObject marsScene;
	private IGfxThreeDObject spacePlane;
	private MatrixService matrixService;
	private GfxTextureShaderProgramService textureProgramService;
	private DeallocationHelper deallocator;
	private int program;
	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private int width;
	private int height;
	//private TextureData textureData;
	private short lastInput;
	private InputController currentController;
	private InputController orthoController;
	private InputController projController;
	private TextureService textureService;
	private IntBuffer textureName = GLBuffers.newDirectIntBuffer(1);
	private List<TextureData> textureData = new ArrayList<TextureData>();

	public NewtWindowMars(String name, int width, int height) {
		this.width = width;
		this.height = height;
		textureProgramService = new GfxTextureShaderProgramService();
		matrixService = new MatrixService();
		deallocator = new DeallocationHelper();
		marsScene = new MarsScene(textureProgramService, matrixService, deallocator, 20, 30, 2.0f, 0.75f);
		spacePlane = new SpacePlane(textureProgramService, matrixService, deallocator, 30, 40);
		orthoController = new OrthoController(marsScene, matrixService);
		projController = new ProjectionController(marsScene, matrixService);
		/** Remember to control value of numberOfTexture */
		textureService = new TextureService(deallocator, 1);
		currentController = projController;
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);
		window = GLWindow.create(caps);
		animator = new FPSAnimator(window, 60, true);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent arg0) {
				shutDown();
			};
		});
		window.addGLEventListener(this);
		window.setSize(width, height);
		window.setTitle(name);
		window.setVisible(true);
		window.addKeyListener(this);
		animator.start();

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		program = textureProgramService.initProgram(gl4);
		marsScene.setModelProgram(program);
		marsScene.init(drawable);

		String url = "/textures/mars_1k_color.jpg";
		textureData.add(textureService.initTexture(gl4, url, 0));
		//textureData.add(textureService.initTexture(gl4, url, 0));
		textureProgramService.setTextureUnit(gl4,
				GL4.GL_TEXTURE0/* textureName.get(0) */);
		marsScene.setTextureName(textureService.textureName);
		
		//spacePlane.setTextureName(textureService.textureName.duplicate());
		textureProgramService.setViewMatrix(gl4, currentController.viewMatrix, program);
		projectionMatrix = matrixService.createProjectionMatrix(60, (float) width / (float) height, 0.5f, 100.0f);
		System.out.println("Projection matrix-init: " + Arrays.toString(projectionMatrix));
		textureProgramService.setProjectionMatrix(gl4, projectionMatrix, program);
		System.out.println("AutoSwapStatus: " + window.getAutoSwapBufferMode());
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		gl4.glDepthFunc(GL4.GL_LESS);
		gl4.glClearColor(0.25f, 0.75f, 0.35f, 0.0f);
		//gl4.glClearColor(0.8f, 0.9f, 1.0f, 0.0f);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		marsScene.dispose(drawable);
		textureService.disposeTextureBuffer();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		marsScene.display(drawable);
		//spacePlane.display(drawable);
		// System.out.println("View matrix-display: " +
		// Arrays.toString(currentController.viewMatrix));
		textureProgramService.setViewMatrix(gl4, currentController.viewMatrix, program);
		textureProgramService.setProjectionMatrix(gl4, projectionMatrix, program);
		gl4.glUseProgram(0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		projectionMatrix = matrixService.createProjectionMatrix(60, (float) width / (float) height, 0.1f, 100.0f);
		System.out.println("Projection matrix-reshape: " + Arrays.toString(projectionMatrix));
		textureProgramService.setProjectionMatrix(gl4, projectionMatrix, program);
		marsScene.reshape(drawable, 0, 0, width, height);
	}

	private void shutDown() {
		// Use a dedicate thread to run the stop() to ensure that the
		// animator stops before program exits.
		new Thread() {
			@Override
			public void run() {
				System.out.println("Shut Down");
				animator.stop(); // stop the animator loop
				System.exit(0);
			}
		}.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("Space");
			lastInput = KeyEvent.VK_SPACE;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			shutDown();
		}
		// Rotate around Y axis
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			currentController.rotateYAxisLeft();
			lastInput = KeyEvent.VK_LEFT;
		}
		// Rotate around Y axis
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			currentController.rotateYAxisRight();
			lastInput = KeyEvent.VK_RIGHT;
		}
		// Rotate around X axis
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			currentController.rotateXAxisUp();
			lastInput = KeyEvent.VK_UP;
		}
		// Rotate around X axis
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentController.rotateXAxisDown();
			lastInput = KeyEvent.VK_DOWN;
		}
		// Zoom in
		if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			currentController.zoomIn();
			lastInput = KeyEvent.VK_PAGE_UP;
		}
		// Zoom out
		if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			currentController.zoomOut();
			lastInput = KeyEvent.VK_PAGE_DOWN;
		}
		// Perspective
		if (e.getKeyCode() == KeyEvent.VK_HOME) {
			if (lastInput != KeyEvent.VK_HOME) {
				projectionMatrix = matrixService.createProjectionMatrix(60, (float) width / (float) height, 0.1f,
						100.0f);
				lastInput = KeyEvent.VK_HOME;
				currentController = projController;
			}
		}
		// Orthogonal
		if (e.getKeyCode() == KeyEvent.VK_END) {
			if (lastInput != KeyEvent.VK_END) {
				projectionMatrix = matrixService.createOrthoMatrix(-5, 5, -5, 5, -5, 5);
				lastInput = KeyEvent.VK_END;
				currentController = orthoController;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
