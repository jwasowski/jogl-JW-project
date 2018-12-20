package gfxJWproject.Utils;

import com.jogamp.opengl.math.FloatUtil;

public class MatrixService {
	// TODO check all methods
	FloatUtil mathService = new FloatUtil();

	public void setupUnitMatrix(float[] matrix) {
		FloatUtil.makeIdentity(matrix);
	}

	public void createProjectionMatrix(float[] matrix, float fovY, float aspectRatio, float nearPlane, float farPlane) {
		// TODO check method
		float yScale = (float) (1.0 / Math.tan(fovY * Math.PI / 360.0));
		float xScale = yScale/aspectRatio;
		float frustumLength = farPlane-nearPlane;
		
		matrix[0]=xScale;
		matrix[5]= yScale;
		matrix[10] = -((farPlane + nearPlane) / frustumLength);
	    matrix[11] = -1;
	    matrix[14] = -((2 * nearPlane * farPlane) / frustumLength);
	}

	public float[] multiplyBy(float[] matrixA, float[] matrixB) {
		return FloatUtil.multMatrix(matrixA, matrixB);
	}

	public void scale(float[] matrix, float x, float y, float z) {
		// TODO Check bool value
		FloatUtil.makeScale(matrix, true, x, y, z);
	}

	public void translate(float[] matrix, float x, float y, float z) {
		// TODO Check bool value
		FloatUtil.makeTranslation(matrix, true, x, y, z);
	}

	public void rotateAxes(float[] matrix, float degrees, float x, float y, float z, float[] tmpVec3f) {
		float radians = (float) (degrees * Math.PI / 180.0f);
		FloatUtil.makeRotationAxis(matrix, 0, radians, x, y, z, tmpVec3f);
	}

}
