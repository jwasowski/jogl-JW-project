package gfxJWproject.Application;

import java.util.ArrayList;
import java.util.List;

import gfxJWproject.Objects.Circle;
import gfxJWproject.Objects.CircleFilled;
import gfxJWproject.Objects.GfxObject;
import gfxJWproject.Objects.StarContour;
import gfxJWproject.Objects.StarFilled;

public class AppDataObject {
	
	private List<GfxObject> dataL;
	
	public List<GfxObject> getDataL() {
		return dataL;
	}

	public AppDataObject() {
		dataL = new ArrayList<GfxObject>();
	}

	public void setupData() {
		dataL.add(new StarFilled());
		dataL.add(new StarContour());
		dataL.add(new CircleFilled());
		dataL.add(new Circle());
		System.out.println("Data Setup: Done");
	}
}
