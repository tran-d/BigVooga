package authoring_UI;

import java.util.ArrayList;

import authoring.SpriteObject;
import javafx.scene.layout.VBox;

public class SpriteSelectPanel extends VBox {

	private String myName;
	private SpriteManager mySM;
	private SpriteGridHandler mySGH;

	SpriteSelectPanel(String name, SpriteManager SM, SpriteGridHandler SGH){
		super();
		myName = name;
		mySM = SM;
		mySGH = SGH;
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
		makeDefaultSpritesClickable(defaults);
	}
	
	private void makeDefaultSpritesDraggable(ArrayList<SpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(SpriteObject SO) {
		mySGH.addDragObject(SO);
	}
	
	private void makeDefaultSpritesClickable(ArrayList<SpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteClickable(SO);
		});
	}
	
	private void makeSpriteClickable(SpriteObject SO) {
        mySGH.addSpriteMouseClick(SO);
	}
	
	public void addNewDefaultSprite(SpriteObject SO, int spriteLocation) {
		SpriteObject newSO = SO.newCopy();
		this.getChildren().add(spriteLocation, newSO);
		makeSpriteDraggable(newSO);
		makeSpriteClickable(newSO);
	}
	

	
}
