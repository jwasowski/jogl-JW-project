package gfxJWprojectTest.ThreeDimensionObjects;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;

public class MarsSceneTest {

	@Test
	public void test() {
		//ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(getClass().getResource("resources/textures/mars_1k_color.jpg"));
		InputStream inputStream = getClass().getResourceAsStream("resources/textures/mars_1k_color.jpg");
		File file = new File(inputStream.toString());
		assertEquals(true, file.exists());
	}

}
