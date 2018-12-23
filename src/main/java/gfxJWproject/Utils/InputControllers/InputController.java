package gfxJWproject.Utils.InputControllers;

public abstract class InputController {
	public float[] viewMatrix = new float[16];
	public abstract void rotateYAxisLeft();
	public abstract void rotateYAxisRight();
	public abstract void rotateXAxisUp();
	public abstract void rotateXAxisDown();
	public abstract void zoomIn(); 
	public abstract void zoomOut() ;
}
