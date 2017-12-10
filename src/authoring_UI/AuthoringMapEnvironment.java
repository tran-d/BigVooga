package authoring_UI;

import javafx.scene.layout.HBox;
import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.Sprite.Parameters.*;
import authoring.Sprite.AnimationSequences.*;
import authoring.Sprite.UtilityTab.*;
import authoring.Sprite.InventoryTab.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;

public class AuthoringMapEnvironment extends HBox{
	
	public AuthoringMapEnvironment(SpritePanels SP, DraggableGrid DG){
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