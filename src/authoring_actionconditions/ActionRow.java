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

	private ActionFactory actionFactory = new ActionFactory();

	private TreeView<HBox> actionTreeView;
	private ActionCategoryTreeItem categoryAction;

	private ActionNameTreeItem actionName;

	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox) {
		super(ID, ACVBox);

		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize());
		actionTreeView = new TreeView<HBox>(categoryAction);
		actionTreeView.setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		this.getItems().addAll(actionTreeView);
	}
	
	public ActionRow(int ID, String label, String selectorLabel, String selectedAction, ActionVBox<ActionRow> ACVBox, TreeView<HBox> tv) {
		super(ID, ACVBox);
		this.getItems().addAll(tv);
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
