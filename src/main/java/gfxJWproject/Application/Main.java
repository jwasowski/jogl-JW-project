package gfxJWproject.Application;

public class Main {

	/**
	 * 
	 */

	public static void main(String[] args) {
		
		/*AppDataObject setupData = new AppDataObject();
		setupData.setupData();*/
		int width = 800, height = 600;
		@SuppressWarnings("unused")
		//AWTSwingWindow frame = new AWTSwingWindow("JoGL Test", width, height,setupData.getDataL());
		NewtWindow frame = new NewtWindow("JoGL KDron", width, height);
		
	}

}
