package gfxJWproject.Application;

public class Main {

	/**
	 * 
	 */

	public static void main(String[] args) {
		
		AppDataObject setupData = new AppDataObject();
		setupData.setupData();
		int width = 640, height = 480;
		@SuppressWarnings("unused")
		GuiWindow frame = new GuiWindow("JoGL Test", width, height,setupData.getDataL());
		
	}

}
