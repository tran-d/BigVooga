package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import authoring.ActionNameTreeItem;
import engine.Actions.ActionFactory;
import engine.operations.OperationFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

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

	public ActionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {

		super(ID, label, selectorLabel, newActionOptions, ACVBox);

		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		actionFactory = new ActionFactory();

		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize());
		actionTreeView = new TreeView<HBox>(categoryAction);
		actionTreeView.setPrefWidth(TREE_VIEW_WIDTH);
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
			this.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			this.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	public void extract() {
		categoryAction.extract();

	}

	/***************************** ACTIONS ******************************/

//	private TreeView<HBox> makeTreeView() {
//		categoryAction = makeActionCategoryTreeItem();
//		TreeView<HBox> tv = new TreeView<HBox>(categoryAction);
//		return tv;
//	}
//
//	private TreeItem<HBox> makeActionCategoryTreeItem() {
//		HBox hb = new HBox();
//		hb.getChildren().addAll(new Label("Choose Action Category: "), makeActionCategoryChoiceBox());
//		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
//		ti.setExpanded(true);
//		ti.expandedProperty().addListener(e -> changeRowTVSize());
//		return ti;
//	}
//
//	private ChoiceBox<String> makeActionCategoryChoiceBox() {
//		ObservableList<String> categories = FXCollections.observableList(actionFactory.getCategories());
//		ChoiceBox<String> cb = new ChoiceBox<>(categories);
//		System.out.println("cats: " + categories);
//
//		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//
//				// System.out.println(actions.get(newValue.intValue()));
//				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
//				categoryAction.getChildren().clear();
//				actionName = new ActionNameTreeItem(categories.get(cb.getSelectionModel().getSelectedIndex()));
//				categoryAction.getChildren().add(actionName);
//			}
//		});
//		return cb;
//	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}
}
