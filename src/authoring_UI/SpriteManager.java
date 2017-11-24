package authoring_UI;

import java.util.ArrayList;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteObjectI;
import authoring.SpriteParameterFactory;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SpriteManager extends TabPane {
	private DraggableGrid myGrid;
	private VBox mySprites;
	private VBox myUserSprites;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private SpriteParameterFactory mySPF;
	private ArrayList<SpriteObject> mySpriteObjs = new ArrayList<SpriteObject>();
	private ArrayList<SpriteObject> myUserSpriteObjs = new ArrayList<SpriteObject>();

	
	protected SpriteManager() {

	myUserSpriteObjs = new ArrayList<SpriteObject>();
	}

	protected SpriteManager(DraggableGrid grid, AuthoringEnvironmentManager AEM, SpriteObjectGridManagerI SOGM) {
		mySPF = new SpriteParameterFactory();
		myAEM = AEM;
		mySOGM = SOGM;
		myGrid = grid;
		mySprites = new VBox();
		myUserSprites = new VBox();
		getParams();
		createSprites();
		createSpriteTabs();
		this.setPrefWidth(110);

	}

	private void createSprites() {
		SpriteObject s1 = mySpriteObjs.get(0);
		SpriteObject s2 = mySpriteObjs.get(1);
		SpriteObject s3 = mySpriteObjs.get(2);
		SpriteObject s4 = mySpriteObjs.get(3);
		myAEM.addDefaultSprite(s1);
		myAEM.addDefaultSprite(s2);
		myAEM.addDefaultSprite(s3);
		myAEM.addDefaultSprite(s4);
		setupDefaultSprites();
	}

	public void getUserSpriteParam(String url) {
		SpriteObject userSprite = new SpriteObject(url);
		ArrayList<SpriteParameterI> param = new ArrayList<SpriteParameterI>();
		param.add(mySPF.makeParameter("canFight", false));
		param.add(mySPF.makeParameter("health", 17.0));
		param.add(mySPF.makeParameter("name", "Ryan"));

		for (SpriteParameterI SP : param) {
			userSprite.addParameter(SP);
		}

		myUserSpriteObjs.add(userSprite);
		createUserSprite(userSprite);
	}

	/**
	 * creates new user sprite
	 * 
	 * @author taekwhunchung
	 * @param sp
	 */

	public void createUserSprite(Object spObj) {
		if (!(spObj instanceof SpriteObject)){
			throw new RuntimeException("Its not a sprite");
		}
		SpriteObject sp = (SpriteObject) spObj;
		myAEM.addUserSprite(sp);

		myUserSprites.getChildren().add(sp);
		myGrid.addDragObject(sp);
	}
	
	public void construct(DraggableGrid grid, AuthoringEnvironmentManager AEM, SpriteObjectGridManagerI SOGM){
		mySPF = new SpriteParameterFactory();
	    myAEM = AEM;
	    mySOGM = SOGM;
		myGrid = grid;
		mySprites = new VBox();
		myUserSprites = new VBox();
		getParams();
		createSprites();
        	createSpriteTabs();
        	this.setPrefWidth(110);
	}
	
	private void setDefaultSpriteVBox(ArrayList<SpriteObject> defaults) {
		mySprites.getChildren().clear();
		mySprites.setPrefWidth(300);
		defaults.forEach(SO->{
			mySprites.getChildren().addAll(SO);
		});
		
	}
	
	public void setupDefaultSprites() {
		ArrayList<SpriteObject> defaults = myAEM.getDefaultGameSprites();
		setDefaultSpriteVBox(defaults);
		makeDefaultSpritesDraggable(defaults);
	}
	
	public void addNewDefaultSprite(SpriteObject SO, int spriteLocation){
		SpriteObject newSO = SO.newCopy();
		mySprites.getChildren().add(spriteLocation, newSO);
		makeSpriteDraggable(newSO);
	}
	
	private void makeDefaultSpritesDraggable(ArrayList<SpriteObject> defaults){
		defaults.forEach(SO->{
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(SpriteObject SO){
		myGrid.addDragObject(SO);
	}
	
	public void getParams() {
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("tree.png");
		urls.add("brick.png");
		urls.add("pikachu.png");
		urls.add("water.png");

		double i = 10.0;
		ArrayList<String> s = new ArrayList<String>();
		s.add("hello");
		s.add("world");
		s.add("bye");
		for (int h = 0; h < 4; h++) {
			SpriteObject SO = new SpriteObject(urls.get(h));
			ArrayList<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();
			myParams.add(mySPF.makeParameter("canFight", true));
			myParams.add(mySPF.makeParameter("health", i));
			myParams.add(mySPF.makeParameter("name", s.get(0)));
			for (SpriteParameterI SP : myParams) {
				SO.addParameter(SP);
			}
			mySpriteObjs.add(SO);
		}
	}

	private void createImageStack(String imageName) {
		StackPane imageStack = new StackPane();
		for (int k = 0; k < 10; k++) {
			ImageView image = new ImageView(new Image(imageName));
			image.setFitWidth(45);
			image.setFitHeight(45);
			imageStack.getChildren().add(image);
			// myGrid.addDragObject(image);
		}
		// mySprites.getChildren().add(imageStack);
	}

	private void createSpriteTabs() {
		Tab defaultSpriteTab = new Tab();
		defaultSpriteTab.setText("Default Sprites");
		defaultSpriteTab.setContent(mySprites);
		defaultSpriteTab.setClosable(false);

		Tab mySpriteTab = new Tab();
		mySpriteTab.setText("User Sprites");
		mySpriteTab.setContent(myUserSprites);
		mySpriteTab.setClosable(false);

		this.getTabs().addAll(defaultSpriteTab, mySpriteTab);
		this.setSide(Side.RIGHT);
	}

}
