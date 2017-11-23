package authoring_UI;

import java.util.ArrayList;
import authoring.BooleanSpriteParameter;
import authoring.DoubleSpriteParameter;
import authoring.SpriteParameterI;
import authoring.StringSpriteParameter;
import javafx.scene.layout.VBox;

public class FEParameterFactory extends VBox {

	protected FEParameterFactory(ArrayList<SpriteParameterI> spriteParams) {
		for (SpriteParameterI BEParam : spriteParams) {
			createFEParameter(BEParam);
		}
		this.setSpacing(5);
	}
	
	private void createFEParameter(SpriteParameterI BEParam) {
		if (BEParam instanceof BooleanSpriteParameter) {
			FEBooleanParameter newBoolean = new FEBooleanParameter(BEParam);
			this.getChildren().add(newBoolean);
		} else if (BEParam instanceof StringSpriteParameter) {
			FEStringParameter newString = new FEStringParameter(BEParam);
			this.getChildren().add(newString);
		} else if (BEParam instanceof DoubleSpriteParameter) {
			FEDoubleParameter newDouble = new FEDoubleParameter(BEParam);
			this.getChildren().add(newDouble);
		}
	}
	
	
	
}
