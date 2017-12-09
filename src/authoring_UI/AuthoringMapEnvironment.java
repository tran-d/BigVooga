package authoring_UI;

import javafx.scene.layout.HBox;

public class AuthoringMapEnvironment extends HBox{
	
	AuthoringMapEnvironment(SpritePanels SP, DraggableGrid DG){
		setPanels(SP);
		setGrid(DG);
	}

	public AuthoringMapEnvironment() {
	}

	public void setPanels(SpritePanels spritePanels) {
		this.getChildren().add(0, spritePanels);
	}

	public void setGrid(DraggableGrid myDG) {
		this.getChildren().add(1, myDG);
	}
}