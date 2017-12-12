package authoring_UI;

import java.util.ResourceBundle;

import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SpriteToolPanel extends VBox {

	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private ResourceBundle spriteCreatorResources;

	protected SpriteToolPanel() {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);

		this.setPrefSize(200, WelcomeScreen.HEIGHT);
		Button b1 = new Button(spriteCreatorResources.getString("TestTool1"));
		Button b2 = new Button(spriteCreatorResources.getString("TestTool2"));
		this.getChildren().addAll(b1, b2);
	}
}
