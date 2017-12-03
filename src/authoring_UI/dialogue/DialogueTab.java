package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DialogueTab extends Tab {
	
	private VBox dialogueView;
	private static final double DIALOGUE_SPACING = 25;
	private ScrollPane sp;
	
	public DialogueTab(String name) {
		this.setText(name);
		
		dialogueView = makeVBox((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, WelcomeScreen.HEIGHT, DIALOGUE_SPACING);
		dialogueView.setAlignment(Pos.TOP_CENTER);
		
		sp = new ScrollPane();
		sp.setContent(dialogueView);
		sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		this.setContent(sp);
		this.fillTab();
	}
	
	public VBox makeVBox(double width, double height, double spacing) {
		VBox hb = new VBox(spacing);
		hb.setPrefWidth(width);
		hb.setPrefHeight(height);
		return hb;
	}
	
	public void fillTab() {
		dialogueView.getChildren().add(new HBox());
		dialogueView.getChildren().add(new Button("Dialogue #1"));
		dialogueView.getChildren().add(new Button("Dialogue #2"));
		
	}

}
