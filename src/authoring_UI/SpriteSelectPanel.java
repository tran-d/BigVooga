package authoring_UI;

import java.util.ArrayList;

import authoring.AbstractSpriteObject;
import authoring.SpriteObject;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SpriteSelectPanel extends VBox {

	private String myName;
//	private SpriteManager mySM;
	private SpriteGridHandler mySGH;

	SpriteSelectPanel(String name, SpriteGridHandler SGH){
		super();
		myName = name;
//		mySM = SM;
		mySGH = SGH;
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
	private void setDefaultSpriteVBox(ArrayList<AbstractSpriteObject> defaults) {
		this.getChildren().clear();
		defaults.forEach(SO -> {
			this.getChildren().add(SO);
		});

	}
	
	public void setupDefaultSprites(ArrayList<AbstractSpriteObject> defaults) {
		setDefaultSpriteVBox(defaults);
		makeDefaultSpritesDraggable(defaults);
		makeDefaultSpritesClickable(defaults);
	}
	
	private void makeDefaultSpritesDraggable(ArrayList<AbstractSpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(AbstractSpriteObject SO) {
		mySGH.addDragObject(SO);
	}
	
	private void makeDefaultSpritesClickable(ArrayList<AbstractSpriteObject> defaults) {
		defaults.forEach(SO -> {
			makeSpriteClickable(SO);
		});
	}
	
	private void makeSpriteClickable(AbstractSpriteObject SO) {
        mySGH.addSpriteMouseClick(SO);
	}
	
	public void addNewSprite(SpriteObject SO){
		
	}
	
	public void addNewDefaultSprite(AbstractSpriteObject SO, int spriteLocation) {
		AbstractSpriteObject newSO = SO.newCopy();
		this.getChildren().add(spriteLocation, newSO);
		makeSpriteDraggable(newSO);
		makeSpriteClickable(newSO);
	}
	
	public void addNewDefaultSprite(AbstractSpriteObject SO) {
		AbstractSpriteObject newSO = SO.newCopy();
		int size = this.getChildren().size();
		this.getChildren().add(size, newSO);
		makeSpriteDraggable(newSO);
		makeSpriteClickable(newSO);
	}
	

	
}
