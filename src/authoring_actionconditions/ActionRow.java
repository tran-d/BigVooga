package authoring_actionconditions;

import engine.Actions.ActionFactory;
import engine.operations.OperationFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

	private static final double TREE_VIEW_WIDTH = 400;
	private ActionFactory actionFactory;
	private TreeView<HBox> actionTree;

	private TreeItem<HBox> categoryAction = new TreeItem<HBox>();
	private TreeItem<HBox> actionAction = new TreeItem<HBox>();
	private TreeItem<HBox> parameterAction = new TreeItem<HBox>();

	private TreeItem<HBox> categoryOperation = new TreeItem<HBox>();
	// private TreeItem<HBox> actionOperation = new TreeItem<HBox>();
	private TreeItem<HBox> parameterOperation = new TreeItem<HBox>();

	private TreeView<HBox> actionTreeView;
	private TreeView<HBox> operationTreeView;
	private OperationFactory operationFactory;

	public ActionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {

		super(ID, label, selectorLabel, newActionOptions, ACVBox);

		// addBuildActionButton(e -> openBuildWindow());

		actionFactory = new ActionFactory();
		operationFactory = new OperationFactory();

		// this.getItems().add(makeActionChoiceBox("Movement"));
		// actionTree = makeTreeView(makeActionChoiceBox("Movement"));
		// this.getItems().add(actionTree);
		actionTreeView = makeActionTreeView();
		actionTreeView.setPrefWidth(TREE_VIEW_WIDTH);
		// operationTreeView = makeOperationTreeView();

		this.getItems().addAll(actionTreeView);
	}

	/*********************************
	 * ACTIONS
	 *************************************/

	private TreeView<HBox> makeActionTreeView() {
		categoryAction = makeActionCategoryTreeItem();
		TreeView<HBox> tv = new TreeView<HBox>(categoryAction);
		return tv;
	}

	private TreeItem<HBox> makeActionCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action Category: "), makeActionCategoryChoiceBox());
		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
		ti.setExpanded(true);
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
				categoryAction.getChildren()
						.add(makeActionTreeItem(categories.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionTreeItem(String category) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action: "), makeActionChoiceBox(category));
		actionAction = new TreeItem<HBox>(hb);
		actionAction.setExpanded(true);
		return actionAction;
	}

	private ChoiceBox<String> makeActionChoiceBox(String category) {
		ObservableList<String> actions = FXCollections.observableList(actionFactory.getActions(category));
		ChoiceBox<String> cb = new ChoiceBox<>(actions);
		System.out.println("Acts: " + actions);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				actionAction.getChildren().clear();
				actionAction.getChildren()
						.add(makeActionParameterTreeItem(actions.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionParameterTreeItem(String action) {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("Choose Action Parameter(s): "));

		parameterAction = new TreeItem<HBox>(hb);
		makeActionParameterChildren(action);
		parameterAction.setExpanded(true);
		return parameterAction;
	}

	private void makeActionParameterChildren(String action) {
		ObservableList<String> parameters = FXCollections.observableList(actionFactory.getParameters(action));
		System.out.println("Params: " + parameters);

		for (String param : parameters) {
			parameterAction.getChildren().add(makeOperationCategoryTreeItem(param));
		}

		// ChoiceBox<String> cb = new ChoiceBox<>(parameters);

		// cb.getSelectionModel().selectedIndexProperty().addListener(new
		// ChangeListener<Number>() {
		//
		// @Override
		// public void changed(ObservableValue<? extends Number> observable, Number
		// oldValue, Number newValue) {
		//
		// // System.out.println(actions.get(newValue.intValue()));
		// // getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
		// parameterAction.getChildren().clear();
		// parameterAction.getChildren()
		// .add(makeOperationCategoryTreeItem(parameters.get(cb.getSelectionModel().getSelectedIndex())));
		// }
		// });
		// return cb;
	}

	/****************************** OPERATIONS ******************************/

	// private TreeView<HBox> makeOperationTreeView() {
	// categoryOperation = makeOperationCategoryTreeItem();
	// TreeView<HBox> tv = new TreeView<HBox>(categoryOperation);
	// return tv;
	// }

	private TreeItem<HBox> makeOperationCategoryTreeItem(String actionParameter) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Operation: "), makeOperationCategoryChoiceBox(actionParameter));
		categoryOperation = new TreeItem<HBox>(hb);
		categoryOperation.setExpanded(true);
		return categoryOperation;
	}

	private ChoiceBox<String> makeOperationCategoryChoiceBox(String actionParameter) {
		// TODO change "Boolean" to actionParameter
		ObservableList<String> operations = FXCollections.observableList(operationFactory.getOperations("Boolean"));
		ChoiceBox<String> cb = new ChoiceBox<>(operations);
		System.out.println("ops: " + operations);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				System.out.println("Selected: " + operations.get(cb.getSelectionModel().getSelectedIndex()));
				categoryOperation.getChildren().clear();
				categoryOperation.getChildren()
						.add(makeParameterOperationTreeItem(operations.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});

		return cb;
	}

	private TreeItem<HBox> makeParameterOperationTreeItem(String operation) {

		HBox hb = new HBox();
		hb.getChildren().add(new Label("Choose Operation Parameter(s): "));

		parameterOperation = new TreeItem<HBox>(hb);
		makeOperationParameterChildren(operation);
		parameterOperation.setExpanded(true);
		return parameterOperation;
	}

	private void makeOperationParameterChildren(String operation) {
		ObservableList<String> parameters = FXCollections.observableList(operationFactory.getParameters(operation));
		System.out.println("Op Params: " + parameters);

		for (String param : parameters) {
			parameterOperation.getChildren().add(makeOperationCategoryTreeItem(param));
		}

	}

	private ChoiceBox<String> makeParameterOperationChoiceBox(String operation) {
		ObservableList<String> operations = FXCollections.observableList(operationFactory.getParameters(operation));
		ChoiceBox<String> cb = new ChoiceBox<>(operations);
		System.out.println(operations);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				parameterOperation.getChildren().clear();
				// parameterOperation.getChildren()
				// .add(makeActionOperationTreeItem(operations.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}

	private void openBuildWindow() {
		// if (view == null && actionOptions.getSelected() != null)
		// view = new BuildActionView(ACVBox, (ActionConditionRow)
		// ACVBox.getChildren().get(labelInt - 1));
	}

}
