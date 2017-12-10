package authoring_UI.Menu;

import java.util.ArrayList;
import java.util.List;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.DisplayLanguage;

public class MenuManager extends MapManager{
	
	private SpriteObjectGridManager MenuGridBE;

	public MenuManager(AuthoringEnvironmentManager AEM, Stage currentStage) {
		super(AEM, currentStage);
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		DraggableGrid ret = new DraggableGrid();
		MenuGridBE = new MenuGridManager();
		ret.setAllGrids(MenuGridBE);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGERNAME = "MenuManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="MenuCreator";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new MenuSpritePanels(mySpriteGridHandler, myAEM);
	}
	
	
	

}