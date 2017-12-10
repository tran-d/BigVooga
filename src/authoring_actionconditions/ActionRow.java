package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import authoring.ActionNameTreeItem;
import engine.Action;
import engine.Actions.ActionFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 * Class representing an action row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ActionRow extends ActionConditionRow {

	private static final double ROW_WIDTH = 800;
	private static final double TREE_VIEW_WIDTH = 600;
	private static final double EXPANDED_HEIGHT = 500;
	private static final double COLLAPSED_HEIGHT = 25;

	private static String EMPTY_CHOICEBOX = "EmptyChoiceBox";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";

	private ActionFactory actionFactory;

	private TreeView<HBox> actionTreeView;
	private ActionCategoryTreeItem categoryAction;

	private ActionNameTreeItem actionName;

	public ActionRow(int ID, String label, String selectorLabel,String selectedAction, ActionVBox<ActionRow> ACVBox) {
		super(ID, label, selectorLabel, selectedAction, ACVBox);

		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		actionFactory = new ActionFactory();

		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize());
		actionTreeView = new TreeView<HBox>(categoryAction);
		actionTreeView.setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		this.getItems().addAll(actionTreeView);
	}

	/********************** PUBLIC METHODS ***********************/

	public TreeItem<HBox> getRootTreeItem() {
		return categoryAction;
	}

	public TreeView<HBox> getTreeView() {
		return actionTreeView;
	}

	public void changeRowTVSize() {
		if (categoryAction.isExpanded()) {
			this.setPrefHeight(EXPANDED_HEIGHT);
			actionTreeView.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			actionTreeView.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	public Action getAction() {
		return categoryAction.extract();

	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}
}
