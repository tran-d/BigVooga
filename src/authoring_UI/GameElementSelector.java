package authoring_UI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteParameterFactory;
import authoring.SpriteParameterI;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.MenuOptionsTemplate;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import tools.DisplayLanguage;

public class GameElementSelector extends TabPane implements Observer {
	
	private static final String SPRITES = "Sprites";
	private static final String DIALOGUES = "Dialogues";
	private static final String DEFAULT = "Default";
	private static final String USER = "User";
	private static final String IMPORTED = "Imported";
	private static final String IMPORTEDINVENTORY = "Imported Inventory";
	private static final String INVENTORY = "Inventory";
	
	private DraggableGrid myGrid;
//	private SpriteSelectPanel mySprites;
//	private SpriteSelectPanel myUserSprites;
	private final int NUM_COLUMNS = 10;

	private AuthoringEnvironmentManager myAEM;
//	private SpriteParameterFactory mySPF;
//	private List<AbstractSpriteObject> mySpriteObjs = new ArrayList<AbstractSpriteObject>();
//	private List<SpriteObject> myUserSpriteObjs = new ArrayList<SpriteObject>();
//	private GameDataHandler myGDH;
//	private SpriteObjectGridManagerI mySOGM;
	private SpriteGridHandler mySpriteGridHandler;
	private Tab dialoguesTab;

	protected GameElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM) {
//		mySPF = new SpriteParameterFactory();
		myAEM = AEM;
		mySpriteGridHandler = spriteGridHandler;
		this.setPrefHeight(275);
//		myGDH = AEM.getGameDataHandler();
//		myAEM.getDefaultSpriteController().getAllSprites().forEach(sprite->{
//			System.out.println("Sprite exists, name: "+sprite.getName());
//		});
//		mySprites = myAEM.getDefaultSpriteController().getSpritePanel(mySpriteGridHandler);
//		myUserSprites = myAEM.getCustomSpriteController().getSpritePanel(mySpriteGridHandler);
		createSpriteTabs();
	}

//	private void createSprites() {
//	}

//	public void getUserSpriteParam(String url) {
//		SpriteObject userSprite = new SpriteObject(url);
//		List<SpriteParameterI> param = new ArrayList<SpriteParameterI>();
//		param.add(mySPF.makeParameter("canFight", false));
//		param.add(mySPF.makeParameter("health", 17.0));
//		param.add(mySPF.makeParameter("name", "Ryan"));
//		for (SpriteParameterI SP : param) {
//			userSprite.addParameter(SP);
//		}
//		myUserSpriteObjs.add(userSprite);
//		createUserSprite(userSprite);
//	}

	/**
	 * creates new user sprite
	 * 
	 * @author taekwhunchung
	 * @author Samuel
	 * @param sp
	 */

	public void createUserSprite(Object spObj) {
		if (!(spObj instanceof SpriteObject)) {
			throw new RuntimeException("Its not a sprite");
		}
		SpriteObject sp = (SpriteObject) spObj;
		try {
			this.myAEM.addUserSprite(sp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// addNewUserDefinedSprite(sp, myUserSprites.getChildren().size());
	}
	// private void addNewDefaultSprite(SpriteObject sp) {
	// mySprites.addNewDefaultSprite(sp);
	// }

//	private void addNewUserDefinedSprite(SpriteObject sp, int spLocation) {
//		myUserSprites.addNewDefaultSprite(sp, spLocation);
//	}

//	public void setupDefaultSprites() {
//		ArrayList<SpriteObject> defaults = myAEM.getDefaultGameSprites();
//		mySprites.setupDefaultSprites(defaults);
//	}

//	public void setupUserDefinedSprites() {
//		ArrayList<SpriteObject> defaults = myAEM.getUserDefinedSprites();
//		myUserSprites.setupDefaultSprites(defaults);
//	}

//	public void getParams(){
//		List<String> urls = new ArrayList<String>();
//		urls.add("/tree.png");
//		urls.add("/brick.png");
//		urls.add("/pikachu.png");
//		urls.add("/water.png");
//		 urls.add("/Link.png");
//		double i = 10.0;
//		List<String> s = new ArrayList<String>();
//		List<String> names = new ArrayList<String>();
//		int width = 1;
//		int height = 1;
//		names.add("tree");
//		names.add("brick");
//		names.add("pikachu");
//		names.add("water");
//		s.add("hello");
//		s.add("world");
//		s.add("bye");
//		for (int h = 0; h < 4; h++) {
//			AbstractSpriteObject SO = new SpriteObject();
//			SO.setImageURL(urls.get(h));
//			SO.setName(names.get(h));
//			List<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();
//			myParams.add(mySPF.makeParameter("canFight", true));
//			myParams.add(mySPF.makeParameter("health", i));
//			myParams.add(mySPF.makeParameter("name", s.get(0)));
//			for (SpriteParameterI SP : myParams) {
//				SO.addParameter(SP);
//			}
//			SO.setNumCellsWidthNoException(width);
//			SO.setNumCellsHeightNoException(height);
//			mySpriteObjs.add(SO);
//			
//
//			
//			
//			
//		
//			// try {
//			// throw new IOException("Dont break");
//			
//			// } catch (IOException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			// }
//			
//			
//			try {
////				myGDH.saveDefaultSprite(SO);
//				System.out.println("Saved " + SO.getImageURL());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		SpriteObject SO = new SpriteObject();
//		SO.setName("testinginventory");
//		SO.addParameter(mySPF.makeParameter("hellothere" , "aweaponforalesscivilizedage"));
//		SO.setInventory(mySpriteObjs);
//		SO.setNumCellsHeightNoException(2);
//		SO.setNumCellsWidthNoException(2);
//		SO.setImageURL(urls.get(4));
//		
//		try {
////			myGDH.saveDefaultSprite(SO);
//			System.out.println("Saved " + SO.getImageURL());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	

		
		
		
		
		

		// SpriteObject SO = new SpriteObject(urls.get(4));
		// ArrayList<SpriteParameterI> myParams = new
		// ArrayList<SpriteParameterI>();
		// myParams.add(mySPF.makeParameter("canFight", true));
		// myParams.add(mySPF.makeParameter("health", 10));
		// myParams.add(mySPF.makeParameter("arrows", 10));
		// myParams.add(mySPF.makeParameter("name", s.get(0)));
		// myParams.add(mySPF.makeParameter("stamina", 50));
		// for (SpriteParameterI SP: myParams){
		// SO.addParameter(SP);
		// }
		// mySpriteObjs.add(SO);

//	}

	private void createSpriteTabs() {
		TabPane spritesTabPane = new TabPane();
		TabPane dialoguesTabPane = new TabPane();
		TabPane inventoryTabPane = new TabPane();
		Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController());
		Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
		Tab importedSpriteTab = createSubTab(IMPORTED, myAEM.getImportedSpriteController());
		Tab inventorySpriteTab = createSubTab(INVENTORY, myAEM.getInventoryController());
		Tab importedInventorySpriteTab = createSubTab(IMPORTEDINVENTORY, myAEM.getImportedInventorySpriteController());
//		Tab defaultDialogueTab = createSubTab(DEFAULT);
//		Tab userDialogueTab = createSubTab(USER);
//		Tab importedDialogueTab = createSubTab(IMPORTED);
		spritesTabPane.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		spritesTabPane.setSide(Side.RIGHT);
//		dialoguesTabPane.getTabs().addAll(defaultDialogueTab, userDialogueTab, importedDialogueTab);
		dialoguesTabPane.setSide(Side.RIGHT);
		
		inventoryTabPane.setSide(Side.RIGHT);
		inventoryTabPane.getTabs().addAll(inventorySpriteTab, importedInventorySpriteTab);
		
		Tab spritesTab = createElementTab(SPRITES, spritesTabPane);
		spritesTab.setClosable(false);
		dialoguesTab = createElementTab(DIALOGUES, dialoguesTabPane);
		dialoguesTab.setClosable(false);
		Tab inventoryTab = createElementTab(INVENTORY, inventoryTabPane);
		inventoryTab.setClosable(false);
		this.getTabs().addAll(spritesTab, dialoguesTab, inventoryTab);
		
		this.setSide(Side.TOP);
	}
	
	private Tab createSubTab(String tabName, SpriteSet controller) {
		Tab subTab = new Tab();
		subTab.setText(tabName);
//		subTab.textProperty().bind(DisplayLanguage.createStringBinding(tabName));
//		defaultSpriteTab.setContent(mySprites);
		subTab.setContent(makeCategoryTabPane(controller));
		subTab.setOnSelectionChanged((event)->{
			if (subTab.isSelected()){
				subTab.setContent(makeCategoryTabPane(controller));
			}
		});
		subTab.setClosable(false);
		return subTab;
	}
	
	private TabPane makeCategoryTabPane(SpriteSet controller){
		TabPane categoryTabPane = new TabPane();
		categoryTabPane.setSide(Side.LEFT);
		for (Entry<String, List<AbstractSpriteObject>> cat: controller.getAllSpritesAsMap().entrySet()){
			Tab catTab = new Tab();
			catTab.setClosable(false);
			catTab.setText(cat.getKey());
			catTab.setContent(makeGrid(cat.getValue()));
			catTab.setOnSelectionChanged(event->{
				if (catTab.isSelected()){
					catTab.setContent(makeGrid(cat.getValue()));
				}
			});
			categoryTabPane.getTabs().add(catTab);
		}
		return categoryTabPane;
	}
	
	private Tab createElementTab(String tabName, TabPane tabPane) {
		Tab elementTab = new Tab();
		elementTab.setText(tabName);
//		elementTab.textProperty().bind(DisplayLanguage.createStringBinding(tabName));
		elementTab.setContent(tabPane);
		elementTab.setClosable(false);
		return elementTab;
	}

	private ScrollPane makeGrid(List<AbstractSpriteObject> sprites) {
		GridPane gp = new GridPane();
		int totalRows = (int) Math.ceil(sprites.size()/10);
		int DEFAULT_MIN_ROWS = 15;
		totalRows = (totalRows<DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
		int counter =0;
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < 10; j++) {				
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED,
						CornerRadii.EMPTY, BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				if (counter<sprites.size()) {
					AbstractSpriteObject toPopulate = sprites.get(counter);
					System.out.println("Adding " + toPopulate);
					this.mySpriteGridHandler.addSpriteDrag(toPopulate);
					this.mySpriteGridHandler.addSpriteMouseClick(toPopulate);
//					this.mySpriteGridHandler.add
					sp.getChildren().add(toPopulate);
//				} else {
//					if (i%5==0) {
//					SpriteObject SO = new SpriteObject();
//					System.out.println("Name: "+toPopulate.getName());
//					System.out.println("ImageURL: "+toPopulate.getImageURL());
//					 try {
//						String current = new java.io.File( "." ).getCanonicalPath();
//						System.out.println(current);
//						File f = new File("");
//						System.out.println("Abs: "+f.getAbsolutePath());
//						System.out.println("Canon :"+f.getCanonicalPath());
//						System.out.println("Basic: "+f.getPath());
//						File g; 
//						while true{
//							File[] files = 
//						}
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					toPopulate.setImage(new Image("/brick.png"));
//					sp.getChildren().add(SO);
//					}
//				}
				counter++;
				
				gp.add(sp, j, i);
			}
		}
	}

		ScrollPane SP = new ScrollPane(gp);
		return SP;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
//		System.out.println("notified observer");
//		System.out.println(mySprites);
		createUserSprite(arg);
	}
	
	public Tab getDialoguesTab() {
		return dialoguesTab;
	}
}