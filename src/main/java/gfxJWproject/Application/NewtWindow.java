package gfxJWproject.Application;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import gfxJWproject.ThreeDimensionObjects.KDron;
import gfxJWproject.Utils.GfxCameraShaderProgramService;
import gfxJWproject.Utils.MatrixService;

public class NewtWindow implements GLEventListener, KeyListener {

	private final GLProfile glp;
	// Specifies a set of OpenGL capabilities, based on your profile.
	private GLCapabilities caps;
	// Create the OpenGL rendering canvas
	private final GLWindow window;
	final private FPSAnimator animator;
	private KDron currentlyDrawn;
	private MatrixService matrixService = new MatrixService();
	private GfxCameraShaderProgramService programService = GfxCameraShaderProgramService.getInstance();
	private int cameraProgram;
	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private int width;
	private int height;

	public NewtWindow(String name, int width, int height) {
		this.width = width;
		this.height = height;
		currentlyDrawn = new KDron();
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
		//TODO Init Camera (view and projection matrices)
		currentlyDrawn.init(drawable);
		cameraProgram = programService.initProgram(gl4);
		matrixService.translate(viewMatrix, 0, 0, -2);
		programService.setViewMatrix(gl4, viewMatrix);
		matrixService.createProjectionMatrix(projectionMatrix, 60, (float) width/(float)height, 0.1f, 100.0f);
		programService.setProjectionMatrix(gl4, projectionMatrix);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		currentlyDrawn.dispose(drawable);

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		currentlyDrawn.display(drawable);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		currentlyDrawn.reshape(drawable, x, y, width, height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("Space");
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			shutDown();
		}
		//TODO Rotation, Rotation Speed, Camera movement

	}

	@Override
	public void keyReleased(KeyEvent e) {
		

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
