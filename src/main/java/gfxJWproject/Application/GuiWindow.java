package gfxJWproject.Application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import gfxJWproject.Objects.GfxObject;

public class GuiWindow extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4227186881816424900L;

	private final GLProfile glProfile;
	private GLCapabilities glCapabilities;
	private final GLCanvas canvas;
	private FPSAnimator animatorTest;
	private List<GfxObject> dataL;
	private Iterator<GfxObject> dataIterator;
	
	

	public GuiWindow(String name, int width, int height, List<GfxObject> dataL) {
		super(name);
		glProfile = GLProfile.get(GLProfile.GL2);
		canvas = new GLCanvas(glCapabilities);
		dataIterator = dataL.iterator();
		glCapabilities = new GLCapabilities(glProfile);
		glCapabilities.setSampleBuffers(false);
		animatorTest = new FPSAnimator(60);
		animatorTest.add(canvas);
		animatorTest.start();
		this.dataL = dataL;
		addKeyListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				shutDown();
			}
		});
		setSize(width, height);
		setVisible(true);
		setFocusable(true);
		canvas.setSize(width, height);
		canvas.setFocusable(false);
		add(canvas);
		System.out.println("Chosen GLCapabilities: " + canvas.getChosenGLCapabilities());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(dataIterator.hasNext()){
			canvas.addGLEventListener(dataIterator.next());}
			else {
				deleteAllGLEvenetListeners(dataL);
				dataIterator = dataL.iterator();
				canvas.addGLEventListener(dataL.get(0));
				dataIterator.next();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			
			shutDown();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	private void shutDown(){
		System.out.println("Shut down");
		animatorTest.stop();
		dispose();
		System.exit(0);
	}
	
	private void deleteAllGLEvenetListeners(List<GfxObject> objectList){
		for(GfxObject object : objectList){
			canvas.disposeGLEventListener(object,false);
		}
	}

}
