package gfxJWproject.Utils.BufferObjects;

public class ColorVertex {
	public float[] position = new float[4];
	public float[] color = new float[4];

	public ColorVertex(float[] position, float[] color) {
		this.position = position;
		this.color = color;
	}

	public int sizeOfArrays() {
		return position.length + color.length;
	}

	public float[] returnPair() {
		float[] returnArray = new float[sizeOfArrays()];
		for (int i = 0; i < returnArray.length / 2; i++) {
			returnArray[i] = position[i];
			returnArray[i + position.length] = color[i];
		}
		return returnArray;
	}

}
