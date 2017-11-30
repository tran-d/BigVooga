package authoring_UI;

import javafx.scene.layout.HBox;

public class AuthoringMapEnvironment extends HBox{
	
	private Menu myMenu;
	private DraggableGrid myDG;
	private SpriteManager mySM;
	
	AuthoringMapEnvironment(){
		super();
	}
	
	AuthoringMapEnvironment(Menu menu, DraggableGrid DG, SpriteManager SM){
		this();
		setMenu(menu);
		setGrid(DG);
		setSpriteManager(SM);
	}

	public Menu getMenu() {
		return myMenu;
	}

	public void setMenu(Menu myMenu) {
		this.myMenu = myMenu;
		this.getChildren().add(0, myMenu);
	}

	public DraggableGrid getGrid() {
		return myDG;
	}

	public void setGrid(DraggableGrid myDG) {
		this.myDG = myDG;
		this.getChildren().add(1, myDG);
	}

	public SpriteManager getSpriteManager() {
		return mySM;
	}

	public void setSpriteManager(SpriteManager mySM) {
		this.mySM = mySM;
		this.getChildren().add(2, mySM);
	}
	

}
