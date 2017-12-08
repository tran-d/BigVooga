package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents a tab listing dialogues to edit
 * 
 * @author DavidTran
 *
 */
public class DialogueTab extends Tab {

	private static final double DIALOGUE_SPACING = 25;
	private static final double PADDING = 25;

	private VBox dialogueLister;
	private ScrollPane sp;

	public DialogueTab(String name) {
		this.textProperty().bind(DisplayLanguage.createStringBinding(name));

		dialogueLister = makeVBox((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, WelcomeScreen.HEIGHT,
				DIALOGUE_SPACING);
		dialogueLister.setAlignment(Pos.TOP_CENTER);
		dialogueLister.setPadding(new Insets(PADDING));

		sp = new ScrollPane();
		sp.setContent(dialogueLister);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		this.setContent(sp);
	}

	private VBox makeVBox(double width, double height, double spacing) {
		VBox hb = new VBox(spacing);
		hb.setPrefWidth(width);
		hb.setPrefHeight(height);
		return hb;
	}

	public void addDialogue(int index, Button btn) {
		if (dialogueLister.getChildren().size() > index) {
			dialogueLister.getChildren().remove(index);
			dialogueLister.getChildren().add(index, btn);
		} else
			dialogueLister.getChildren().add(btn);
		// System.out.println(dialogueLister.getChildren().size() + " " + index);

	}

	public void deleteDialogue(int index) {

		dialogueLister.getChildren().remove(index);

	}

}
