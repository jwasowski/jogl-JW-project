package gfxJWprojectTest.ThreeDimensionObjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KDronTest {

	@Test
	public void test() {
		String ver = System.getProperty("java.version");
		String mod = ver.substring(2, 3);
		assertEquals("8",mod);
	}

}
