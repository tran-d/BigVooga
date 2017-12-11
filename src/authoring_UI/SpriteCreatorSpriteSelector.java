package authoring_UI;

import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import authoring.SpriteObject;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

public class SpriteCreatorSpriteSelector extends TabPane implements Observer {

	private static final String SPRITES = "Sprites";
	private static final String DIALOGUES = "Dialogues";
	private static final String DEFAULT = "Default";
	private static final String USER = "User";
	private static final String IMPORTED = "Imported";
	private static final String IMPORTEDINVENTORY = "Imported Inventory";
	private static final String INVENTORY = "Inventory";

	private DraggableGrid myGrid;
	private final int NUM_COLUMNS = 10;

	private AuthoringEnvironmentManager myAEM;
	private SpriteCreatorSpriteManager mySM;
	private SpriteCreatorGridHandler mySpriteGridHandler;

	protected SpriteCreatorSpriteSelector(SpriteCreatorSpriteManager SM, SpriteCreatorGridHandler spriteGridHandler,
			AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		mySM = SM;
		mySpriteGridHandler = spriteGridHandler;
		createSpriteTabs();
	}

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
	}

	private void createSpriteTabs() {
		TabPane spritesTabPane = new TabPane();
		Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController());
		Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
		Tab importedSpriteTab = createSubTab(IMPORTED, myAEM.getImportedSpriteController());
		spritesTabPane.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		spritesTabPane.setSide(Side.RIGHT);

		//
		Tab spritesTab = createElementTab(SPRITES, spritesTabPane);
		spritesTab.setClosable(false);
		this.getTabs().add(spritesTab);

		this.setSide(Side.TOP);
	}

	private Tab createElementTab(String tabName, TabPane tabPane) {
		Tab elementTab = new Tab();
		elementTab.setText(tabName);
		elementTab.setContent(tabPane);
		elementTab.setClosable(false);
		return elementTab;
	}

	private Tab createSubTab(String tabName, SpriteSet controller) {
		Tab subTab = new Tab();
		subTab.setText(tabName);
		subTab.setContent(makeCategoryTabPane(controller));
		subTab.setOnSelectionChanged((event) -> {
			if (subTab.isSelected()) {
				subTab.setContent(makeCategoryTabPane(controller));
			}
		});
		subTab.setClosable(false);
		return subTab;
	}

	private TabPane makeCategoryTabPane(SpriteSet controller) {
		TabPane categoryTabPane = new TabPane();
		categoryTabPane.setSide(Side.LEFT);
		for (Entry<String, List<AbstractSpriteObject>> cat : controller.getAllSpritesAsMap().entrySet()) {
			Tab catTab = new Tab();
			catTab.setClosable(false);
			catTab.setText(cat.getKey());
			catTab.setContent(makeGrid(cat.getValue()));
			catTab.setOnSelectionChanged(event -> {
				if (catTab.isSelected()) {
					catTab.setContent(makeGrid(cat.getValue()));
				}
			});
			categoryTabPane.getTabs().add(catTab);
		}
		return categoryTabPane;
	}

	private ScrollPane makeGrid(List<AbstractSpriteObject> sprites) {
		GridPane gp = new GridPane();
		int totalRows = (int) Math.ceil(sprites.size() / 10);
		int DEFAULT_MIN_ROWS = 15;
		totalRows = (totalRows < DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
		int counter = 0;
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < 10; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				if (counter < sprites.size()) {
					AbstractSpriteObject toPopulate = sprites.get(counter);
					System.out.println("Adding " + toPopulate);
					this.mySpriteGridHandler.addSpriteMouseClick(toPopulate);
					sp.getChildren().add(toPopulate);
					counter++;

					gp.add(sp, j, i);
				}
			}
		}

		ScrollPane SP = new ScrollPane(gp);
		return SP;
	}

	public void updateSpriteTabs() {
		this.getTabs().remove(0);
		createSpriteTabs();

		// userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
		// spritesTabPane.getTabs().removeAll();
		// spritesTabPane.getTabs().addAll(defaultSpriteTab, userSpriteTab,
		// importedSpriteTab);
		// (defaultSpriteTab, userSpriteTab, importedSpriteTab);
		System.out.println("userspritestab updated");
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		createUserSprite(arg);
	}

}
