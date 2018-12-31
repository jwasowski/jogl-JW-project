package gfxJWproject.Application;

import java.util.Arrays;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject;
import gfxJWproject.ThreeDimensionObjects.KDron;
import gfxJWproject.ThreeDimensionObjects.MarsScene;
import gfxJWproject.Utils.DeallocationHelper;
import gfxJWproject.Utils.MatrixService;
import gfxJWproject.Utils.Shaders.GfxModelShaderProgramService;

public class NewtWindowMars implements GLEventListener{
	private final GLProfile glp;
	// Specifies a set of OpenGL capabilities, based on your profile.
	private GLCapabilities caps;
	// Create the OpenGL rendering canvas
	private final GLWindow window;
	final private FPSAnimator animator;
	private IGfxThreeDObject currentlyDrawn;
	private MatrixService matrixService;
	private GfxModelShaderProgramService modelProgramService;
	private DeallocationHelper deallocator;
	private int program;
	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private int width;
	private int height;
	
	public NewtWindowMars(String name, int width, int height) {
		this.width = width;
		this.height = height;
		modelProgramService = new GfxModelShaderProgramService();
		matrixService = new MatrixService();
		deallocator = new DeallocationHelper();
		currentlyDrawn = new MarsScene(modelProgramService, matrixService, deallocator);
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
		animator.start();

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		program = modelProgramService.initProgram(gl4);
		currentlyDrawn.setModelProgram(program);
		currentlyDrawn.init(drawable);
		System.out.println("View matrix-init: " + Arrays.toString(viewMatrix));
		modelProgramService.cameraShaderService.setViewMatrix(gl4, viewMatrix, program);
		projectionMatrix = matrixService.createProjectionMatrix(60, (float) width / (float) height, 0.5f, 100.0f);
		System.out.println("Projection matrix-init: " + Arrays.toString(projectionMatrix));
		modelProgramService.cameraShaderService.setProjectionMatrix(gl4, projectionMatrix, program);
		gl4.glEnable(GL4.GL_DEPTH_TEST);
		gl4.glDepthFunc(GL4.GL_LESS);
		gl4.glClearColor(0.8f, 0.9f, 1.0f, 0.0f);
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		currentlyDrawn.dispose(drawable);		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL4 gl4 = drawable.getGL().getGL4();
		currentlyDrawn.display(drawable);
		modelProgramService.cameraShaderService.setViewMatrix(gl4, viewMatrix, program);
		modelProgramService.cameraShaderService.setProjectionMatrix(gl4, projectionMatrix, program);
		gl4.glUseProgram(0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL4 gl4 = drawable.getGL().getGL4();
		projectionMatrix = matrixService.createProjectionMatrix(60, (float) width / (float) height, 0.1f, 100.0f);
		System.out.println("Projection matrix-reshape: " + Arrays.toString(projectionMatrix));
		modelProgramService.cameraShaderService.setProjectionMatrix(gl4, projectionMatrix, program);
		currentlyDrawn.reshape(drawable, 0, 0, width, height);
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
}
