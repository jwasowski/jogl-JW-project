package gfxJWproject.Utils.InputControllers;

import gfxJWproject.ThreeDimensionObjects.IGfxThreeDObject;
import gfxJWproject.Utils.MatrixService;

public class OrthoController extends InputController {
 private IGfxThreeDObject managedObject;
 private MatrixService matrixService;
 
 
 public OrthoController(IGfxThreeDObject managedObject, MatrixService matrixService){
	 this.managedObject = managedObject;
	 this.matrixService = matrixService;
	 matrixService.setupUnitMatrix(super.viewMatrix);
 }
	@Override
	public void rotateYAxisLeft() {
		managedObject.rotateYAxisLeft();	
	}

	@Override
	public void rotateYAxisRight() {
		managedObject.rotateYAxisRight();
		
	}

	@Override
	public void rotateXAxisUp() {
		managedObject.rotateXAxisUp();
		
	}

	@Override
	public void rotateXAxisDown() {
		managedObject.rotateXAxisDown();
		
	}

	@Override
	public void zoomIn() {
		matrixService.translate(super.viewMatrix, 0, 0, 0.2f);
	}

	@Override
	public void zoomOut() {
		matrixService.translate(super.viewMatrix, 0, 0, -0.2f);
		
	}

}
