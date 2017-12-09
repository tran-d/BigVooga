package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.BooleanSpriteParameter;
import authoring.DoubleSpriteParameter;
import authoring.SpriteParameterI;
import authoring.StringSpriteParameter;
import javafx.scene.layout.VBox;

public class FEParameterFactory extends VBox {

	protected FEParameterFactory(List<SpriteParameterI> newParams) {
		for (SpriteParameterI BEParam : newParams) {
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

	public static FEParameter makeFEParameter(SpriteParameterI SPI) {
		FEParameter ret = null;
		if (SPI instanceof BooleanSpriteParameter) {
			ret = new FEBooleanParameter(SPI);
		} else if (SPI instanceof StringSpriteParameter) {
			ret = new FEStringParameter(SPI);
		} else if (SPI instanceof DoubleSpriteParameter) {
			ret = new FEDoubleParameter(SPI);
		}
		return ret;
	}
}
