package gfxJWproject.Application;

public class Main {

	/**
	 * 
	 */

	public static void main(String[] args) {
		
		/*AppDataObject setupData = new AppDataObject();
		setupData.setupData();*/
		int width = 1024, height = 768;
		@SuppressWarnings("unused")
		//AWTSwingWindow frame = new AWTSwingWindow("JoGL Test", width, height,setupData.getDataL());
		//NewtWindow frame = new NewtWindow("JoGL KDron", width, height);
		NewtWindowMars frame = new NewtWindowMars("JoGL Mars Orbit Scene", width, height);
		
	}

}
