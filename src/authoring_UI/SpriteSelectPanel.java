package authoring_UI;

import java.util.ArrayList;

import authoring.SpriteObject;
import javafx.scene.layout.VBox;

public class SpriteSelectPanel extends VBox {

	private String myName;
	private SpriteManager mySM;
	private DraggableGrid myDG;

	SpriteSelectPanel(String name, SpriteManager SM, DraggableGrid DG){
		super();
		myName = name;
		mySM = SM;
		myDG = DG;
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
	private void setDefaultSpriteVBox(ArrayList<SpriteObject> defaults) {
		this.getChildren().clear();
		defaults.forEach(SO -> {
			this.getChildren().addAll((SpriteObject) SO);
		});

	}
	
	public void setupDefaultSprites(ArrayList<SpriteObject> defaults) {
		setDefaultSpriteVBox(defaults);
		makeDefaultSpritesDraggable(defaults);
	}
	
	private void makeDefaultSpritesDraggable(ArrayList<SpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(SpriteObject SO) {
		myDG.addDragObject(SO);
	}
	
	public void addNewDefaultSprite(SpriteObject SO) {
		SpriteObject newSO = SO.newCopy();
		this.getChildren().add(newSO);
		makeSpriteDraggable(newSO);
	}
	

	
}
