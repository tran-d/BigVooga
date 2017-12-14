package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.SpritePanels.GameElementSelector;
import authoring.SpritePanels.SpritePanels;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.DisplayLanguage;

public class MapManager extends TabPane {
	public static final int VIEW_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	public static final int VIEW_HEIGHT = WelcomeScreen.HEIGHT - 35;
	protected String TAB_TAG;
	protected static final String ADD_TAB = "+";
	protected String MANAGERNAME;

	protected Stage stage;
	protected Scene scene;
	protected SingleSelectionModel<Tab> mySelectModel;
	protected Tab addTab;
	protected ObjectProperty<Boolean> gridIsShowing;
	// private AuthoringMapEnvironment authMap;

	private ViewSideBar sideBar;
	private GameElementSelector mySprites;
	protected AuthoringEnvironmentManager myAEM;
	private int myTabCount = 1;
	private Tab startTab;
	private boolean oldProject;
	private String projectName = "TestProject";
	private GameDataHandler myGDH;
	private List<DraggableGrid> allWorlds = new ArrayList<DraggableGrid>();
	private Pane mapEditor = new Pane();
	private SpritePanels spritePanels;
	private SpriteGridHandler mySpriteGridHandler;
	protected String myType;
	
	public MapManager(AuthoringEnvironmentManager AEM, Scene currentScene, String type) {
		myType = type;
		setTabTag();
		setManagerName();
		gridIsShowing = new SimpleObjectProperty<Boolean>();
		gridIsShowing.addListener((change, oldValue, newValue) -> {
			;
			this.mySpriteGridHandler.setGridIsShown(newValue);
		});
		myAEM = AEM;
		myGDH = myAEM.getGameDataHandler();
		scene = currentScene;
		mapEditor.getChildren().add(this);
		mySelectModel = this.getSelectionModel();
		this.setPrefWidth(VIEW_WIDTH);
		this.setPrefHeight(VIEW_HEIGHT);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);

		List<DraggableGrid> DGs = getListOfDraggableGrids();
		createAddTab();
		if (DGs.size() > 0) {
			for (DraggableGrid w : DGs) {
				createTab(w);
			}
		} else {
			;
			createTab(makeDraggableGrid());
		}
		this.mySelectModel.select(startTab);
	}

	public MapManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		this(AEM, currentScene, "");
		
	}

	protected void setManagerName() {
		MANAGERNAME = "MapManager";
	}

	protected List<DraggableGrid> getListOfDraggableGrids() {
		List<DraggableGrid> DGs = myGDH.loadWorldsFromWorldDirectory();
		;
		
		return DGs;
	}

	protected String getManagerName() {
		return MANAGERNAME;
	}
	
	public void addImportedWorlds(List<DraggableGrid> importedWorlds) {
		for (DraggableGrid w : importedWorlds) {
			createTab(w);
		}
	}

	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler) {
		return new SpritePanels(mySpriteGridHandler, myAEM);
	}

	protected DraggableGrid makeDraggableGrid() {
		return new DraggableGrid();
	}

	public void gridIsShowing() {
		gridIsShowing.set(true);
	}

	public void gridIsNotShowing() {
		gridIsShowing.set(false);
	}

	public void setGridIsShowing(boolean showing) {
		gridIsShowing.set(showing);
	}

	public boolean isGridShowing() {
		return gridIsShowing.get();
	}
	
	private void createAddTab(){
		this.setSide(Side.TOP);
		addTab = new Tab();
		
		Button button = new Button();
		button.setText(ADD_TAB);
		button.setOnAction(e->{
			createTab(makeDraggableGrid());
		});
		addTab.setGraphic(button);
		this.getTabs().add(addTab);
	}


	private HBox setupScene(DraggableGrid w) {
		return setupFEAuthClasses(w);
	}

	private HBox setupFEAuthClasses(DraggableGrid w) {
		allWorlds.add(w);
		mySpriteGridHandler = new SpriteGridHandler(myTabCount, w);
		w.construct(mySpriteGridHandler);
		mySpriteGridHandler.addKeyPress(scene);
		spritePanels = makeSpritePanels(mySpriteGridHandler);
		mySpriteGridHandler.setGridDisplayPanel(spritePanels.getDisplayPanel());
		mySpriteGridHandler.setElementSelectorDisplayPanel(spritePanels.getElementSelectorDisplayPanel());
		AuthoringMapEnvironment authMap = makeAuthoringMapEnvironment(spritePanels, w);
		return authMap;
	}

	protected AuthoringMapEnvironment makeAuthoringMapEnvironment(SpritePanels spritePanels, DraggableGrid dg) {
		return new AuthoringMapEnvironment(spritePanels, dg);
	}

	private void createTab(DraggableGrid w) {
		Tab newtab = createEditableTab();
		if (w.getName()==null){
			String newName = "World "+this.getTabs().size();
			((Label)newtab.getGraphic()).setText(newName);
			w.setName(newName);
		} else {
			((Label)newtab.getGraphic()).setText(w.getName());
		}
		newtab.setOnClosed(e -> this.removeWorld(w));
		newtab.setContent(setupScene(w));
		((Label)newtab.getGraphic()).textProperty().addListener((change, oldValue, newValue)->{
			w.setName(newValue);
		});
		if (this.getTabs().size()==1){
			startTab = newtab;
		}
		this.getTabs().add(this.getTabs().size() - 1, newtab);
		myTabCount++;
		this.mySelectModel.select(newtab);
		;
	}

	protected void setTabTag() {
		TAB_TAG = "Map";
	}

	private void removeWorld(DraggableGrid w) {
		allWorlds.remove(w);
		System.out.println("JUST REMOVED A WORLD, CURRENT SIZE IS: " + allWorlds.size());
		myTabCount--;
	}

	private List<AuthoringMapEnvironment> getAllMapEnvironments() {
		List<AuthoringMapEnvironment> allMaps = new ArrayList<AuthoringMapEnvironment>();
		for (Tab t : this.getTabs()) {
			if (!t.getText().equals(ADD_TAB)) {
				AuthoringMapEnvironment AME = (AuthoringMapEnvironment) t.getContent();
				allMaps.add(AME);
			}
		}
		return allMaps;
	}

	public Pane getPane() {
		return mapEditor;
	}

	public Tab getDialoguesTab() {
		return spritePanels.getDialoguesTab();
	}

	public List<DraggableGrid> getAllWorlds() {
		System.out.println("SIZE OF ALL WORLDS: " + allWorlds.size()); // 3 even after I delete.
		return allWorlds;
	}

	private Tab createEditableTab() {
		StringProperty tabMap = new SimpleStringProperty();
		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG))
				.concat(" " + Integer.toString(myTabCount)));
		//
		final Label label = new Label(tabMap.get());
		// cannot bind editable tab label!!

		final Tab tab = new Tab();
		tab.setGraphic(label);
		final TextField textField = new TextField();
		label.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (((MouseEvent) event).getClickCount() == 2) {
					textField.setText(label.getText());
					tab.setGraphic(textField);
					textField.selectAll();
					textField.requestFocus();
				}
			}
		});

		textField.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				label.setText(textField.getText());
				tab.setGraphic(label);
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					label.setText(textField.getText());
					tab.setGraphic(label);
				}
			}
		});
		return tab;
	}
}