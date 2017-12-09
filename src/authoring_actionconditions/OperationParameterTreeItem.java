package authoring_actionconditions;

import engine.operations.OperationFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class OperationParameterTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";

	private TextField doubleTF;
	private TextField stringTF;

	private OperationFactory operationFactory = new OperationFactory();

	public OperationParameterTreeItem(String selectedOperation) {

		this.makeParameterOperationTreeItem(selectedOperation);
	}
	
	public Node getItem() {
		return null;
	}

	private TreeItem<HBox> makeParameterOperationTreeItem(String selectedOperation) {

		HBox hb = new HBox();
		this.setValue(hb);
		makeOperationParameterChildren(selectedOperation, this, hb);
		this.setExpanded(true);
		return this;
	}

	private void makeOperationParameterChildren(String selectedOperation, TreeItem<HBox> parameterOperation, HBox hb) {

		if (selectedOperation.equals(INPUT_A_DOUBLE)) {
			doubleTF = createDoubleTextField(parameterOperation);
			hb.getChildren().addAll(doubleTF);

		} else if (selectedOperation.equals(INPUT_A_STRING)) {
			stringTF = createStringTextField(parameterOperation);
			hb.getChildren().addAll(stringTF);
		}

		else {
			hb.getChildren().add(new Label("Choose Operation Parameter(s): "));

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

	private TextField createDoubleTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(tf));
		// treeItem.getChildren().add(tfTreeItem);
		tf.setOnKeyReleased(e -> {
			checkDoubleInput(tf);
			// checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});

		return tf;

	}

	private TextField createStringTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(tf));
		// treeItem.getChildren().add(tfTreeItem);
		tf.setOnKeyReleased(e -> {
			// checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});

		return tf;

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
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
