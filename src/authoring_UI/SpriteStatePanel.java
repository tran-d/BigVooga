package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class SpriteStatePanel extends VBox {

	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	private VBox stateBox;
	private TabPane spritePane;
	private SpriteCreatorDisplayPanel myDP;
	private AuthoringEnvironmentManager myAEM;
	private SpriteCreatorManager mySCM;

	protected SpriteStatePanel(AuthoringEnvironmentManager AEM, SpriteCreatorManager SCM) {
		this.setPrefWidth(PANE_WIDTH / 2);
		this.setMaxWidth(PANE_WIDTH / 2);
		this.setPrefHeight(WelcomeScreen.HEIGHT);
		this.setMaxWidth(PANE_WIDTH);
		this.setStyle("-fx-background-color: transparent;");

		myAEM = AEM;
		mySCM = SCM;
		
		
		stateBox = createStateBox();
		spritePane = mySCM.getSpriteSelector();
		
		this.getChildren().addAll(stateBox,spritePane);
	}

	private TabPane addCategoryTabs() {
		TabPane tp = new TabPane();
		Tab defaultTab = makeTab("Default");
		Tab userTab = makeTab("User");
		Tab importTab = makeTab("Imported");

		;
		ScrollPane defaultSP = new ScrollPane(new SpriteCreatorGrid(myAEM.getDefaultSpriteController().getAllSprites()));
		defaultTab.setContent(defaultSP);

		ScrollPane userSP = new ScrollPane(new SpriteCreatorGrid(myAEM.getCustomSpriteController().getAllSprites()));
		userTab.setContent(userSP);

		ScrollPane importSP = new ScrollPane();
		importTab.setContent(importSP);

		tp.getTabs().addAll(defaultTab, userTab, importTab);
		return tp;

	}

	private Tab makeTab(String tabName) {

		return new Tab(tabName);
	}

	private VBox createStateBox() {
		myDP = mySCM.getDisplayPanel();
		myDP.setMaxHeight(WelcomeScreen.HEIGHT / 2);
		myDP.setPrefHeight(WelcomeScreen.HEIGHT / 2);
		myDP.setPrefWidth(PANE_WIDTH / 2);
		myDP.setStyle("-fx-background-color: transparent;");
		return myDP;
	}

}
