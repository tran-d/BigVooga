package authoring_UI;

import java.util.ResourceBundle;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

/**
 * Class for sprite tab, which is a tab for each sprite that the user is
 * creating
 * 
 * @author taekwhunchung
 *
 */
public class SpriteTab extends Tab {

	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;

	private ResourceBundle spriteCreatorResources;
	private HBox parentBox;
	private SpriteStatePanel myStatePanel;
	private SpriteImagePanel myImagePanel;
	private SpriteToolPanel myToolBoxPanel;
	private AuthoringEnvironmentManager myAEM;
	private SpriteCreatorManager mySCM;
	private SpriteCreatorSpriteManager mySM;

	protected SpriteTab(AuthoringEnvironmentManager AEM, SpriteCreatorManager SCM, SpriteCreatorImageGrid imageGrid,
			SpriteCreatorSpriteManager SM) {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);
		this.setText(spriteCreatorResources.getString("SpriteTab"));
		myAEM = AEM;
		mySCM = SCM;
		mySM = SM;

		parentBox = addParentHBox();
		this.setContent(parentBox);

		myStatePanel = new SpriteStatePanel(myAEM, SCM);
		myImagePanel = new SpriteImagePanel(myAEM, imageGrid, mySM, SCM);
		myToolBoxPanel = new SpriteToolPanel();

		parentBox.getChildren().addAll(myStatePanel, myImagePanel, myToolBoxPanel);

		// StatePanel
		// ImageStack
		// ToolBox

	}

	private HBox addParentHBox() {
		HBox hb = new HBox();
		hb.setStyle("-fx-background-color: transparent;");
		hb.setPrefWidth(PANE_WIDTH);
		hb.setPrefHeight(WelcomeScreen.HEIGHT);

		return hb;
	}

}
