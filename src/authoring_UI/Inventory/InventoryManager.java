package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.DialogSprite.DialogSequence;
import authoring_UI.MapManager;
import authoring_UI.displayable.DisplayableManager;
import engine.utilities.data.GameDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents the pane containing all inventory authoring components
 * 
 * @author DavidTran
 *
 */
public class InventoryManager extends DisplayableManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";
	private static final String DELETE_BUTTON_PROMPT = "Delete";

	private HBox hb;
	private InventoryEditor currentEditor;
	private InventoryTabPane iView;
	private List<InventoryEditor> editorList;
	private int currentEditorIndex = 0;
	private InventoryExtractor iExtractor;
	private InventoryListView listView;

	private Tab mapInventoriesTab;
	private GameDataHandler GDH;
	private AuthoringEnvironmentManager AEM;

	public InventoryManager(AuthoringEnvironmentManager currentAEM) {
		AEM = currentAEM;
		GDH = AEM.getGameDataHandler();
		iView = new InventoryTabPane();
		editorList = new ArrayList<>();
		iExtractor = new InventoryExtractor();
		hb = new HBox(NODE_SPACING);
		hb.setLayoutX(10);
		hb.getChildren().addAll(iView, createSeparator(), createButtonPanel());

		// test
		// addDefaultinventoryButton();
		// addUserinventoryButton("blah", -1);

	}

	@Override
	protected Separator createSeparator() {
		return super.createSeparator();
	}

	@Override
	protected Separator createShortSeparator(int height) {
		return super.createShortSeparator(height);
	}

	/*************************** PUBLIC METHODS **********************************/

	public void addInventoryListener(Tab inventoriesTab) {
		mapInventoriesTab = inventoriesTab;
		System.out.println("YIPPEE");
		updateListView();
	}

	public HBox getPane() {
		return hb;
	}

	static int counter = 0;
	
	@Override
	protected void updateListView() {
		iExtractor.extract(editorList);
		listView = new InventoryListView(iExtractor.getInventoryList());
		counter++;
		System.out.println("Yieks its broken" + counter);
		mapInventoriesTab.setContent(listView);
	}

	@Override
	protected void save() {
		if (currentEditor != null && !currentEditor.getName().trim().equals("")) {
			if (!currentEditor.getBackgroundIsColor()) {
				//InventorySequence inventorySequence = new InventorySequence(currentEditor.getName(), currentEditor.getInventorySequence(), currentEditor.getBackgroundImage());
				//AEM.getDialogSpriteController().addNewDialogSequence(inventorySequence);
			}
			else if (currentEditor.getBackgroundIsColor()) {
				//DialogSequence dialogSequence = new DialogSequence(currentEditor.getName(), currentEditor.getInventorySequence(), currentEditor.getBackgroundColor());
				//AEM.getDialogSpriteController().addNewDialogSequence(dialogSequence);
			}
			
			if (editorList.contains(currentEditor)) {
				editorList.remove(currentEditor);
			}
			editorList.add(currentEditorIndex, currentEditor);

			// editorList.add(currentEditor);
			addUserInventoryButton(currentEditor.getName());
			// currentEditor = null;
		}

		updateListView();
	}

	protected void newEditor() {
		currentEditor = new InventoryEditor(e -> save(), GDH);
		currentEditorIndex = editorList.size();

		loadEditor(currentEditorIndex);
	}

	protected void loadEditor(int index) {

		;
		
		if (hb.getChildren().size() >= 4) {
			hb.getChildren().remove(5 - 1);
			hb.getChildren().remove(4 - 1);

		}

		if (editorList.size() <= index) {
			hb.getChildren().addAll(createShortSeparator(300), currentEditor.getParent());
		} else {
			hb.getChildren().addAll(createShortSeparator(300), editorList.get(index).getParent());
			currentEditor = editorList.get(index);

		}

		currentEditorIndex = index;
	}

	protected VBox createButtonPanel() {
		VBox vb = new VBox(NODE_SPACING);
		vb.getChildren().addAll(createButton(ADD_BUTTON_PROMPT, e -> newEditor()),
				createButton(SAVE_BUTTON_PROMPT, e -> save()), createButton(DELETE_BUTTON_PROMPT, e -> delete()));
		return vb;
	}

	protected void prev() {
		if (currentEditorIndex > 0) {
			currentEditorIndex -= 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
	}

	protected void next() {
		if (currentEditorIndex < editorList.size() - 1) {
			currentEditorIndex += 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
	}

	private void addDefaultInventoryButton() {
		Button btn = new Button("Default inventory #1");
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// btn.setOnAction(e -> loadEditor(currentEditor));
		// change number
		iView.addDefaultInventoryButton(0, btn);

	}

	private void addUserInventoryButton(String name) {
		
		Button btn = new Button(name);
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		iView.addUserInventoryButton(currentEditorIndex, btn);
		System.out.println(iView);
		btn.setOnAction(e -> loadEditor(iView.getButtonIndex(btn)));
	}

	protected void delete() {

		for(int i = 0; i < editorList.size(); i++) {
			
		}


		if (!editorList.isEmpty()) {
			
			removeUserinventoryButton();
			
			if (editorList.size() > 1) {
				;
				;

				if (currentEditorIndex == editorList.size() - 1) {
					prev();
					editorList.remove(currentEditorIndex + 1);

				} else {
					next();
					editorList.remove(currentEditorIndex - 1);
					currentEditorIndex -= 1;
				}
				
				currentEditor = editorList.get(currentEditorIndex);
			}

			else {
				hb.getChildren().remove(4);
				hb.getChildren().remove(3);
				editorList.remove(currentEditorIndex);
				currentEditorIndex -= 1;
			}
		}

		;
		;

		for(int i = 0; i < editorList.size(); i++) {
			;
		}

	}

	private void removeUserinventoryButton() {
		int id = currentEditorIndex;
		iView.removeUserInventoryButton(id);
	}

}
