package gfxJWprojectTest.Utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gfxJWproject.Utils.BufferObjects.ColorVertex;

public class ColorVertexTest {
	@Test
	public void test() {
		ColorVertex testObject = new ColorVertex(new float[] { -.5f, -.5f, .5f, 1.0f }, new float[] { 1, 1, 1, 1 });
		final float[] comparisionObject = { -.5f, -.5f, .5f, 1.0f, 1, 1, 1, 1 };
		assertEquals(Arrays.toString(testObject.returnPair()), Arrays.toString(comparisionObject));
	}
}
