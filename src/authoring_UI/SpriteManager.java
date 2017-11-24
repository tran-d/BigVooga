package authoring_UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteParameterFactory;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SpriteManager extends VBox implements Observer {
	private SpriteGridHandler mySpriteGridHandler;
	private VBox mySprites;
	private VBox myUserSprites;
	private AuthoringEnvironmentManager myAEM;
	private SpriteParameterFactory mySPF;
	private ArrayList<SpriteObject> mySpriteObjs = new ArrayList<SpriteObject>();
	private ArrayList<SpriteObject> myUserSpriteObjs = new ArrayList<SpriteObject>();

	protected SpriteManager(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM, SpriteObjectGridManagerI SOGM){
		myUserSpriteObjs = new ArrayList<SpriteObject>();
		mySPF = new SpriteParameterFactory();
	    myAEM = AEM;
		mySpriteGridHandler = spriteGridHandler;
		mySprites = new VBox();
		myUserSprites = new VBox();
		getParams();
		createSprites();
		this.getChildren().addAll(createSpriteTabs(), createTrash());

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
		makeSpriteDraggable(sp);
	}
	
	private void setDefaultSpriteVBox(ArrayList<SpriteObject> defaults) {
		mySprites.getChildren().clear();
		mySprites.setPrefWidth(300);
		defaults.forEach(SO->{
			createSpriteStacks(SO);
		});
		
	}
	
	private void createSpriteStacks(SpriteObject SO) {
		StackPane spriteStack = new StackPane();
		for (int i = 0; i < 10; i++) {
			SpriteObject newSO = SO.newCopy();
			spriteStack.getChildren().add(newSO);
			mySpriteGridHandler.addDragObject(newSO);
		}
		mySprites.getChildren().add(spriteStack);
	}
	
	public void setupDefaultSprites() {
		ArrayList<SpriteObject> defaults = myAEM.getDefaultGameSprites();
		setDefaultSpriteVBox(defaults);
		makeDefaultSpritesDraggable(defaults);
	}
	
//	public void addNewDefaultSprite(SpriteObject SO, int spriteLocation){
//		SpriteObject newSO = SO.newCopy();
//		mySprites.getChildren().add(spriteLocation, newSO);
//		makeSpriteDraggable(newSO);
//	}
	
	private void makeDefaultSpritesDraggable(ArrayList<SpriteObject> defaults){
		defaults.forEach(SO->{
			makeSpriteDraggable(SO);
		});
	}
	
	private void makeSpriteDraggable(SpriteObject SO){
		mySpriteGridHandler.addDragObject(SO);
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

	private TabPane createSpriteTabs() {
		TabPane mySpriteTabs = new TabPane();
		Tab defaultSpriteTab = new Tab();
		defaultSpriteTab.setText("Default Sprites");
		defaultSpriteTab.setContent(mySprites);
		defaultSpriteTab.setClosable(false);

		Tab mySpriteTab = new Tab();
		mySpriteTab.setText("User Sprites");
		mySpriteTab.setContent(myUserSprites);
		mySpriteTab.setClosable(false);

		mySpriteTabs.getTabs().addAll(defaultSpriteTab, mySpriteTab);
		mySpriteTabs.setSide(Side.RIGHT);
		mySpriteTabs.setPrefWidth(110);
		
		return mySpriteTabs;
	}
	
	private ImageView createTrash() {
		ImageView trashCan = new ImageView(new Image("trash.png"));
		trashCan.setFitWidth(45);
	    trashCan.setFitHeight(45);
		mySpriteGridHandler.addDropToTrash(trashCan);
		this.getChildren().add(trashCan);
		
		return trashCan;
	}

	//adds new user sprites
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		System.out.println("notified observer");
		System.out.println(mySprites);
		createUserSprite(arg);
	}

}
