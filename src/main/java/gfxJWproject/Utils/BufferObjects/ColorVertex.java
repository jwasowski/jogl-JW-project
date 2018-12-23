package gfxJWproject.Utils.BufferObjects;

/**Obsolete  class, doesn't serve anything right now
 * @deprecated*/
public class ColorVertex {
	public float[] position = new float[4];
	public float[] color = new float[4];

	public ColorVertex(float[] position, float[] color) {
		this.position = position;
		this.color = color;
	}
	/** Returns sum of position and color array lengths. 
	 * @return sum of both arrays length*/
	public int sizeOfArrays() {
		return position.length + color.length;
	}
	/** Returns position and color as one array float array.
	 * @return float array of combined position and color arrays*/
	public float[] returnPair() {
		float[] returnArray = new float[sizeOfArrays()];
		for (int i = 0; i < returnArray.length / 2; i++) {
			returnArray[i] = position[i];
			returnArray[i + position.length] = color[i];
		}
		return returnArray;
	}

}
