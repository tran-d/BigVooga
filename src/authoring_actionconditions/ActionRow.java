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

	public ActionRow(int ID, String label, String selectorLabel,String selectedAction, ActionVBox<ActionRow> ACVBox) {
		super(ID, label, selectorLabel, selectedAction, ACVBox);

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
		// ActionProcessor p = new ActionProcessor(actionTreeView, categoryAction,
		// actionAction);
		actionName.extract();
//		try {
//
//			for (String s : actionParameterTypes) {
//				System.out.println(s);
//			}
//
//			for (OperationNameTreeItem opItem : opItemList)
//				System.out.println("selected op: " + opItem.getSelectedOperation());
//
//		} catch (Exception e) {
//			showError(e.getMessage(), "blah");
//		}

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

	private TreeItem<HBox> makeActionNameTreeItem(String category) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action: "), makeActionNameChoiceBox(category));
		actionAction = new TreeItem<HBox>(hb);
		actionAction.setExpanded(true);
		return actionAction;
	}

	private ChoiceBox<String> makeActionNameChoiceBox(String category) {
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
		hb.getChildren().add(new Label("Choose Action Parameter(s)/Operation(s): "));

		TreeItem<HBox> parameterAction = new TreeItem<HBox>(hb);
		makeActionParameterChildren(action, parameterAction, hb);
		parameterAction.setExpanded(true);
		return parameterAction;
	}

	private void makeActionParameterChildren(String action, TreeItem<HBox> parameterAction, HBox hb) {
		ObservableList<String> parameters = FXCollections.observableList(actionFactory.getParameters(action));
		actionParameterTypes = parameters;
		System.out.println("Params: " + parameters);

		hb.getChildren().add(new Label("[ "));

		for (String param : parameters) {
			hb.getChildren().add(new Label(param + " "));

			OperationNameTreeItem opItem = new OperationNameTreeItem(param);
			opItemList.add(opItem);

			parameterAction.getChildren().add(opItem);

		}

		hb.getChildren().add(new Label("]"));
	}

	/****************************** OPERATIONS ******************************/

	private TreeItem<HBox> makeOperationNameTreeItem(String actionParameter) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Operation: "));
		TreeItem<HBox> categoryOperation = new TreeItem<HBox>(hb);

		hb.getChildren().add(makeOperationCategoryChoiceBox(actionParameter, categoryOperation));

		categoryOperation.setExpanded(true);
		return categoryOperation;
	}

	private ChoiceBox<String> makeOperationCategoryChoiceBox(String actionParameter, TreeItem<HBox> categoryOperation) {
		ObservableList<String> operations = FXCollections
				.observableList(operationFactory.getOperations(actionParameter));
		ChoiceBox<String> cb = new ChoiceBox<>(operations);

		if (actionParameter.equals("Double"))
			operations.add(0, INPUT_A_DOUBLE);
		else if (actionParameter.equals("String"))
			operations.add(0, (INPUT_A_STRING));

		System.out.println("ops: " + operations);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				System.out.println("Selected: " + operations.get(cb.getSelectionModel().getSelectedIndex()));
				categoryOperation.getChildren().clear();
				categoryOperation.getChildren()
						.add(new OperationParameterTreeItem(operations.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});

		return cb;
	}

	private TreeItem<HBox> makeParameterOperationTreeItem(String selectedOperation) {

		HBox hb = new HBox();
		hb.getChildren().add(new Label("Choose Operation Parameter(s): "));

		TreeItem<HBox> parameterOperation = new TreeItem<HBox>(hb);
		makeOperationParameterChildren(selectedOperation, parameterOperation, hb);
		parameterOperation.setExpanded(true);
		return parameterOperation;
	}

	private void makeOperationParameterChildren(String selectedOperation, TreeItem<HBox> parameterOperation, HBox hb) {

		if (selectedOperation.equals(INPUT_A_DOUBLE))
			addDoubleTextField(parameterOperation);
		else if (selectedOperation.equals(INPUT_A_STRING))
			addStringTextField(parameterOperation);

		else {
			ObservableList<String> parameters = FXCollections
					.observableList(operationFactory.getParameters(selectedOperation));

			System.out.println("Op Params: " + parameters);

			// hb.getChildren().add(new Label("[ "));
			//
			// for (String param : parameters) {
			// hb.getChildren().add(new Label(param + " "));
			// parameterOperation.getChildren().add(makeOperationCategoryTreeItem(param));
			// }
			//
			// hb.getChildren().add(new Label("]"));

			hb.getChildren().add(new Label("[ "));

			for (String param : parameters) {
				hb.getChildren().add(new Label(param + " "));

				TreeItem<HBox> paramTV = new OperationNameTreeItem(param);

				// if (param.equals("Double")) {
				// TextField tf = new TextField();
				// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
				// Double: "), tf));
				// parameterOperation.getChildren().add(tfTreeItem);
				// tf.setOnKeyReleased(e -> {
				// checkDoubleInput(tf);
				// checkEmptyInput(tf, parameterOperation, paramTV,
				// parameterOperation.getChildren().indexOf(tfTreeItem));
				// });
				// } else if (param.equals("String")) {
				// TextField tf = new TextField();
				// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
				// String: "), tf));
				// parameterOperation.getChildren().add(tfTreeItem);
				// tf.setOnKeyReleased(e -> {
				// checkEmptyInput(tf, parameterOperation, paramTV,
				// parameterOperation.getChildren().indexOf(tfTreeItem));
				// });
				// }

				parameterOperation.getChildren().add(paramTV);

			}

			hb.getChildren().add(new Label("]"));
		}

	}

	private ChoiceBox<String> makeParameterOperationChoiceBox(String operation, TreeItem<HBox> parameterOperation) {
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

	private void checkEmptyInput(TextField tf, TreeItem<HBox> parameterAction, TreeItem<HBox> paramTV,
			int tfTreeViewIndex) {
		try {
			if (!tf.getText().equals(""))
				parameterAction.getChildren().remove(paramTV);
			else {
				if (!parameterAction.getChildren().contains(paramTV))
					System.out.println(tfTreeViewIndex);
				parameterAction.getChildren().add(tfTreeViewIndex + 1, paramTV);
			}
		} catch (Exception e) {

		}
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}
}
