package gfxJWproject.Utils;

import com.jogamp.opengl.math.FloatUtil;

public class MatrixService {
	// TODO check all methods
	FloatUtil mathService = new FloatUtil();
	public void setupUnitMatrix(float [] matrix){
		FloatUtil.makeIdentity(matrix);
	}
	
	public void createProjectionMatrix(float[] matrix, float fovY, float aspectRatio, float nearPlane, float farPlane) {
		//TODO rewrite method
		
	}
	public float[] multiplyBy(float[] matrixA, float[] matrixB){
		return FloatUtil.multMatrix(matrixA,matrixB);
	}
	
	public void scale(float[] matrix, float x, float y, float z){
		//TODO Check bool value
		FloatUtil.makeScale(matrix, false, x, y, z);
	}
	
	public void translate(float[] matrix, float x, float y, float z){
		//TODO Check bool value
		FloatUtil.makeTranslation(matrix, false, x, y, z);
	}
	
	public void rotateAxes(float[] matrix, float degrees, float x, float y, float z, float[] tmpVec3f){
		float radians =(float) (degrees*Math.PI/180.0f);
		FloatUtil.makeRotationAxis(matrix, 0, radians, x, y, z, tmpVec3f);
	}
	
}
