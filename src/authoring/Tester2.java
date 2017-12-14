package authoring;

import javafx.application.Application;
import javafx.stage.Stage;

public class Tester2 extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		SpriteParameterFactory SPF = new SpriteParameterFactory();
		StringSpriteParameter BSPString = (StringSpriteParameter) SPF.makeParameter("hello", "hi");
		
		SpriteObject S1 = new SpriteObject();
		S1.addParameter(BSPString);
		
		SpriteObject S2 = new SpriteObject();
		S2.addParameter(BSPString);
		
		if (S1.equals(S2)) {
			;
		}
		
		BooleanSpriteParameter BSPBoolean = new BooleanSpriteParameter("hello", true);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
