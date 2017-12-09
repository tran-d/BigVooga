package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import engine.operations.OperationFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	private TextField doubleParameterTF;
	private TextField stringParameterTF;
	private OperationNameTreeItem operationNameTreeItem;
	private ObservableList<String> operationParameters;
	private String selectedOperation;
	private List<OperationNameTreeItem> listOfOperations = new ArrayList<>();

	private OperationFactory operationFactory = new OperationFactory();

	public OperationParameterTreeItem(String selectedOperation) {
		this.selectedOperation = selectedOperation;
		this.makeParameterOperationTreeItem(selectedOperation);
	}

	public String getParameter() {

		if (doubleParameterTF != null) {
			System.out.println("Double was inputted: " + doubleParameterTF.getText());
			return doubleParameterTF.getText();
		} else if (stringParameterTF != null) {
			System.out.println("String was inputted: " + stringParameterTF.getText());
			return stringParameterTF.getText();
		} else {
			System.out.println(selectedOperation);
			return selectedOperation;
		}

	}

	public String makeOperation() {
		List<String> listOfStringParams = new ArrayList<>();

		for (OperationNameTreeItem op : listOfOperations) {

			listOfStringParams.add((String) op.makeOperation());
		}
		
		for (String param : listOfStringParams) {
			System.out.println("Selected operation w/ param: " + selectedOperation + " " + param);
		}

//		operationFactory.makeOperation(selectedOperation, listOfStringParams);

		return selectedOperation;
	}

	public int getNumberOfParameters() {
		return listOfOperations.size();
	}

	private TreeItem<HBox> makeParameterOperationTreeItem(String selectedOperation) {

		HBox hb = new HBox();
		this.setValue(hb);
		makeOperationParameterChildren(selectedOperation, this, hb);
		this.setExpanded(true);
		return this;
	}

	private void makeOperationParameterChildren(String selectedOperation, TreeItem<HBox> operationParameter, HBox hb) {

		if (selectedOperation.equals(INPUT_A_DOUBLE)) {
			doubleParameterTF = createDoubleTextField(operationParameter);
			hb.getChildren().addAll(doubleParameterTF);

		} else if (selectedOperation.equals(INPUT_A_STRING)) {
			stringParameterTF = createStringTextField(operationParameter);
			hb.getChildren().addAll(stringParameterTF);
		}

		else {
			operationParameters = FXCollections.observableList(operationFactory.getParameters(selectedOperation));
			System.out.println("Op Params: " + operationParameters);

			listOfOperations = new ArrayList<>();

			if (!operationParameters.isEmpty()) {

				hb.getChildren().add(new Label("Choose Operation Parameter(s): "));

				hb.getChildren().add(new Label("[ "));

				for (String opParam : operationParameters) {

					hb.getChildren().add(new Label(opParam + " "));

					operationNameTreeItem = new OperationNameTreeItem(opParam);
					listOfOperations.add(operationNameTreeItem);
					operationParameter.getChildren().add(operationNameTreeItem);
				}

				hb.getChildren().add(new Label("]"));
			}

		}
	}

	private TextField createDoubleTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		tf.setOnKeyReleased(e -> {
			checkDoubleInput(tf);
		});

		return tf;

	}

	private TextField createStringTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		tf.setOnKeyReleased(e -> { // do nothing
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
