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
	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";

	private static String EMPTY_CHOICEBOX = "EmptyChoiceBox";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";

	private ActionFactory actionFactory;
	private OperationFactory operationFactory;

	private TreeView<HBox> actionTreeView;
	private TreeItem<HBox> categoryAction = new TreeItem<HBox>();
	private TreeItem<HBox> actionAction = new TreeItem<HBox>();

	private ActionNameTreeItem actionName;
	private List<OperationNameTreeItem> opItemList = new ArrayList<>();

	private List<String> actionParameterTypes;

	// private TreeItem<HBox> parameterAction = new TreeItem<HBox>();
	// private TreeItem<HBox> categoryOperation = new TreeItem<HBox>();
	// private TreeItem<HBox> actionOperation = new TreeItem<HBox>();
	// private TreeItem<HBox> parameterOperation = new TreeItem<HBox>();

	public ActionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {

		super(ID, label, selectorLabel, newActionOptions, ACVBox);

		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		actionFactory = new ActionFactory();
		operationFactory = new OperationFactory();

		actionTreeView = makeTreeView();
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

	public void extract() {
		try {
			actionName.extract();
		} catch (NullPointerException e) {
			showError(INVALID_INPUT_MESSAGE, EMPTY_CHOICEBOX);
		}

	}

	/***************************** ACTIONS ******************************/

	private TreeView<HBox> makeTreeView() {
		categoryAction = makeActionCategoryTreeItem();
		TreeView<HBox> tv = new TreeView<HBox>(categoryAction);
		return tv;
	}

	private TreeItem<HBox> makeActionCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action Category: "), makeActionCategoryChoiceBox());
		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
		ti.setExpanded(true);
		ti.expandedProperty().addListener(e -> changeRowTVSize());
		return ti;
	}

	private ChoiceBox<String> makeActionCategoryChoiceBox() {
		ObservableList<String> categories = FXCollections.observableList(actionFactory.getCategories());
		ChoiceBox<String> cb = new ChoiceBox<>(categories);
		System.out.println("cats: " + categories);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				categoryAction.getChildren().clear();
				actionName = new ActionNameTreeItem(categories.get(cb.getSelectionModel().getSelectedIndex()));
				categoryAction.getChildren().add(actionName);
			}
		});
		return cb;
	}

	private void addDoubleTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert Double: "), tf));
		treeItem.getChildren().add(tfTreeItem);
		tf.setOnKeyReleased(e -> {
			checkDoubleInput(tf);
			// checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});
	}

	private void addStringTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert String: "), tf));
		treeItem.getChildren().add(tfTreeItem);
		tf.setOnKeyReleased(e -> {
			// checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});
	}

	private void checkDoubleInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				Double.parseDouble(tf.getText());
		} catch (NumberFormatException e) {
			showError(INVALID_INPUT_MESSAGE, DOUBLE_INPUT_MESSAGE);
			tf.clear();
		}
	}

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
