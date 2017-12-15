package authoring_UI.SpriteCreatorTab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteNameManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.drawing.ImageCanvasPane;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.MainAuthoringGUI;
import authoring_UI.SpriteGridHandler;
import authoring_UI.ViewSideBar;
import engine.VoogaException;
import engine.utilities.data.GameDataHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.DisplayLanguage;

public class SpriteCreatorGridManager extends SpriteObjectGridManager {
	
	private static final String TOOLSANDNAMES_PATH = "authoring/drawing/drawingTools/drawingTools";
	private static final String NAME_FIELD = "NameField";
		private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	private static final String PATH = "resources/";
	private static final String CATEGORY_FIELD = "CategoryField";
	private static final String CREATE_IMAGE_BUTTON = "CreateImageButton";
	private static final String CREATE_SPRITE_BUTTON = "CreateSpriteButton";
	private static int ROWS = 3;
	private static int COLUMNS = 3;
//	private String fileName;
	private BiFunction<Image, String, AbstractSpriteObject> getSpriteTypeFunction;
	private TextField nameField;
	private TextField categoryField;
	private AbstractSpriteObject newSprite;
	private AuthoringEnvironmentManager myAEM;
	private static ResourceBundle paintResources = ResourceBundle.getBundle(TOOLSANDNAMES_PATH);
	
	
	public SpriteCreatorGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public SpriteCreatorGridManager(){
		super(ROWS, COLUMNS);
	}
	public SpriteCreatorGridManager(SpriteGridHandler SGH, AuthoringEnvironmentManager AEM, BiFunction<Image, String, AbstractSpriteObject> getSpriteType){
		this(SGH);
		
		myAEM = AEM;
	}
	
	public SpriteCreatorGridManager(AuthoringEnvironmentManager AEM, BiFunction<Image, String, AbstractSpriteObject> getSpriteType){
		this();
		getSpriteTypeFunction = getSpriteType;
		myAEM = AEM;
	}

	protected SpriteCreatorGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	protected SpriteCreatorGridManager(int rows, int columns, SpriteGridHandler SGH, SpriteNameManager SNM) {
		super(rows, columns, SGH);
		
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new SpriteCreatorLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		
	}
	
	@Override
	public void setCanFillBackground(){
		canFillBackground = true;
	}
	
	public void setSpriteToCoverEntireGrid(AbstractSpriteObject ASO){
		nameField.setText(ASO.getName());
		categoryField.setText("General");
		updateNewSpriteAndFillEntireGrid(()-> getMapLayer().setBackgroundImage(()->ASO));
	}
	
	@Override 
	public void getOnBackgroundChangeFunctionality(File file){
		Image image = myAEM.getGameDataHandler().getImage(file);
		String fileName = file.getName();
		nameField.setText(fileName.substring(0, fileName.indexOf(".")));
		categoryField.setText("General");
		updateNewSpriteAndFillEntireGrid(()-> getMapLayer().setBackgroundImage(()-> getSpriteTypeFunction.apply(image, fileName)));
	}
	
	private void updateNewSpriteAndFillEntireGrid(Supplier<AbstractSpriteObject> fillGridSupplier){
		newSprite = fillGridSupplier.get();
		this.populateCell(newSprite, new Integer[]{0,0});
		this.mySpriteGridHandler.setActiveDisplayPanelSprite(newSprite);
	}
	
	private AbstractSpriteObject getNewSprite(){
		System.out.println("Getting sprite now");
		return newSprite;
	}
	
	public List<Node> getExtraUI(){
		List<Node> ret = new ArrayList<Node>();
		ret.add(addButtons());
		ret.add(addNameCategoryBox());
		return ret;
	}
	
	private VBox addNameCategoryBox() {
		Label enterName = new Label();
		enterName.textProperty().bind(DisplayLanguage.createStringBinding("NameField"));
		nameField = new TextField();
		Label enterCategory = new Label();
		enterName.textProperty().bind(DisplayLanguage.createStringBinding("CategoryField"));
		categoryField = new TextField();
		VBox nameCategoryBox = new VBox(10);
		nameCategoryBox.getChildren().addAll(enterName, nameField, enterCategory, categoryField);
		return nameCategoryBox;
	}
	
	private VBox addButtons() {
//		Button loadImageButton = new Button(spriteCreatorResources.getString("LoadImageButton"));
//		loadImageButton.setOnAction(e -> {
//			try {
//				openImage();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});

		Button createImageButton = new Button();
		createImageButton.textProperty().bind(DisplayLanguage.createStringBinding(CREATE_IMAGE_BUTTON));
		createImageButton.setOnAction(e -> {
			Stage newStage = new Stage();
			ImageCanvasPane paint = new ImageCanvasPane(500, 500, s -> {
				final String fileName = ("UniqueSprite"+Math.random()).replaceAll("\\.", "");
				System.out.println("fileName: "+fileName);
				newSprite = getMapLayer().setBackgroundImage(()-> getSpriteTypeFunction.apply(s, fileName));
				saveTo(s, fileName);
			});
			
			Scene paintScene = new Scene(paint);
			newStage.setScene(paintScene);
			newStage.show();
		});

		Button createSpriteButton = new Button();
		createSpriteButton.textProperty().bind(DisplayLanguage.createStringBinding(CREATE_SPRITE_BUTTON));

		createSpriteButton.setOnAction(e -> {
			AbstractSpriteObject dummySprite = getNewSprite();
			String spriteName = new String(nameField.getText());
			if (myAEM.getSpriteNameManager().isNameValidTemplate(spriteName)) {
				try {
					dummySprite.setName(spriteName);
					if (categoryField.getText() == null || categoryField.getText().replaceAll("\\s+", "").length() == 0) {
						if (dummySprite instanceof SpriteObject){
							myAEM.addUserSprite((SpriteObject)dummySprite);
						} else if (dummySprite instanceof InventoryObject){
							myAEM.addInventorySprite((InventoryObject)dummySprite);
						}
						
					} else {
						if (dummySprite instanceof SpriteObject){
							myAEM.addUserSprite(categoryField.getText(), (SpriteObject)dummySprite);
						} else if (dummySprite instanceof InventoryObject){
							myAEM.addInventorySprite(categoryField.getText(), (InventoryObject)dummySprite);
						}
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				myAEM.getSpriteNameManager().addTemplateName(spriteName);

				nameField.clear();
				categoryField.clear();
				this.resetActiveCells();
				this.mySpriteGridHandler.deactivateActiveSprites();
				((AuthoringMapStackPane)dummySprite.getParent()).removeChild();
//				myImageGrid.getImageStack().getChildren().remove(0);
//				mySM.setActiveSprite(null);
//				myImageGrid.setCurrentSprite(null);

//				mySC.updateSpriteTabs();
				// this.getChildren().removeAll(newSprite, nameBox);
				// newSprite = new SpriteObject();

				// this.getChildren().addAll(newSprite, nameBox);

				// this.getChildren().remove(1);
				// this.getChildren().add(addSpriteTabs());
				newSprite = this.getSpriteTypeFunction.apply(null, null);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Name already exists");
				alert.setContentText("Change to a unique name");
				alert.showAndWait();
			}

		});
		VBox buttonBox = new VBox(10);
		buttonBox.getChildren().addAll(createSpriteButton, createImageButton);
		return buttonBox;
	}
	
//	public String getSpriteName() {
//		return nameField.getText();
//	}
//
//	public String getSpriteCategory() {
//		return categoryField.getText();
//	}
//
//	public void setSpriteName(String s) {
//		nameField.setText(s);
//	}
//
//	public void setSpriteCategory(String s) {
//		categoryField.setText(s);
//	}
	
	
	private void saveTo(Image image, String location) {
//		System.out.println("Location abs path; "+location.getAbsolutePath());
		File loc = new File(location);
		if (location == null || image == null)
			return;
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", loc);
		} catch (IOException e) {
			throw new VoogaException("IllegalFile", loc.getAbsolutePath());
		}
	}


}
