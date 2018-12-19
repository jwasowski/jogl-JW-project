package gfxJWprojectTest.ThreeDimensionObjects;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gfxJWproject.ThreeDimensionObjects.KDron;

public class KDronTest {

	@Test
	public void test() {
		KDron testObject = new KDron();
		final float[] comparisionObject ={-.5f, -.5f, .5f, 1.0f, 1, 1, 1, 1,
				-.5f, .5f, .5f, 1.0f, 1, 0, 0, 1,
				.5f, -.5f, .5f, 1.0f, 0, 1, 0, 1,
				.5f, -.5f, .5f, 1.0f,1, 1, 0, 1,
				-.5f, -.5f, -.5f, 1.0f,0, 0, 1, 1,
				-.5f, .5f, -.5f, 1.0f, 1, 0, 0, 1,
				.5f, .5f, -.5f, 1.0f,1, 0, 1, 1,
				.5f, -.5f, -.5f, 1.0f,0, 0, 0, 1};
		testObject.createSuperArray();
		assertEquals(Arrays.toString(comparisionObject),(Arrays.toString(testObject.superArray)));
	}

}
