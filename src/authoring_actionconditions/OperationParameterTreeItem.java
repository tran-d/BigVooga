package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import engine.operations.Operation;
import engine.operations.OperationFactory;
import engine.operations.VoogaParameter;
import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class OperationParameterTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";
	private static final String INPUT_A_BOOLEAN = "Input a Boolean";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";
	private static final String BOOLEAN_INPUT_MESSAGE = "EnterBoolean";

	private OperationFactory operationFactory = new OperationFactory();
	private TextField doubleParameterTF;
	private TextField stringParameterTF;
	private TextField booleanParameterTF;
	private OperationNameTreeItem operationNameTreeItem;
	private ObservableList<String> operationParameters;
	private String selectedOperation;
	private List<OperationNameTreeItem> listOfOperations = new ArrayList<>();
	private ObservableList<VoogaParameter> voogaParameters;
	private static List<VoogaType> voogaTypesForExistingItems = new ArrayList<>();
	private ChoiceBox<String> existingItemsChoiceBox;

	public OperationParameterTreeItem(String selectedOperation) {
		this.selectedOperation = selectedOperation;

		voogaTypesForExistingItems = new ArrayList<>();
		voogaTypesForExistingItems.add(VoogaType.ANIMATIONNAME);
		voogaTypesForExistingItems.add(VoogaType.BOOLEANNAME);
		voogaTypesForExistingItems.add(VoogaType.DIALOGNAME);
		voogaTypesForExistingItems.add(VoogaType.DOUBLENAME);
		voogaTypesForExistingItems.add(VoogaType.KEY);
		voogaTypesForExistingItems.add(VoogaType.OBJECTNAME);
		voogaTypesForExistingItems.add(VoogaType.STRINGNAME);
		voogaTypesForExistingItems.add(VoogaType.TAG);
		voogaTypesForExistingItems.add(VoogaType.WORLDNAME);

		this.makeParameterOperationTreeItem(selectedOperation);
	}

	public Object getParameter() {

		if (doubleParameterTF != null) {
			System.out.println("Double was inputted: " + doubleParameterTF.getText());
			return operationFactory.wrap(getDoubleInput(doubleParameterTF));
		} else if (stringParameterTF != null) {
			System.out.println("String was inputted: " + stringParameterTF.getText());
			return operationFactory.wrap(stringParameterTF.getText());
		} else if (booleanParameterTF != null) {
			System.out.println("Boolean was inputted: " + booleanParameterTF.getText());
			return operationFactory.wrap(getBooleanInput(booleanParameterTF));
		} else if (existingItemsChoiceBox != null) {
			System.out.println(existingItemsChoiceBox.getSelectionModel().getSelectedItem().toString());
			return operationFactory.makeOperation(selectedOperation, operationFactory.wrap(existingItemsChoiceBox.getSelectionModel().getSelectedItem()));
			
		} else {
			System.out.println(selectedOperation);
			return operationFactory.makeOperation(selectedOperation, new Object[0]);
		}

	}

	public Operation<?> makeOperation() {
		List<Object> listOfStringParams = new ArrayList<>();

		for (OperationNameTreeItem op : listOfOperations) {

			listOfStringParams.add(op.makeOperation());
		}

		for (Object param : listOfStringParams) {
			System.out.println("Selected operation w/ param: " + selectedOperation + " " + param.toString());
		}

		System.out.println("Making Operation...");
		return operationFactory.makeOperation(selectedOperation, listOfStringParams.toArray());

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
		System.out.println("SELECTED OPERATIONS: " + selectedOperation);
		if (selectedOperation.equals(INPUT_A_DOUBLE)) {
			doubleParameterTF = createDoubleTextField(operationParameter);
			hb.getChildren().addAll(doubleParameterTF);

		} else if (selectedOperation.equals(INPUT_A_STRING)) {
			stringParameterTF = createStringTextField(operationParameter);
			hb.getChildren().addAll(stringParameterTF);
		}

		else if (selectedOperation.equals(INPUT_A_BOOLEAN)) {
			booleanParameterTF = createBooleanTextField(operationParameter);
			hb.getChildren().addAll(booleanParameterTF);
		}

		else {
			operationParameters = FXCollections.observableList(operationFactory.getParameters(selectedOperation));
			voogaParameters = FXCollections.observableList(operationFactory.getParametersWithNames(selectedOperation));
			System.out.println("Op Params: " + operationParameters);
			listOfOperations = new ArrayList<>();

			if (!operationParameters.isEmpty()) {

				hb.getChildren().add(new Label("Choose Operation Parameter(s): "));
				hb.getChildren().add(new Label("[ "));

				for (int i = 0; i < operationParameters.size(); i++) {
					hb.getChildren().add(new Label(operationParameters.get(i) + " "));

					if (this.checkVoogaType(voogaParameters.get(i).getType())) {

						System.out.println("SPECIAL VOOGATYPE");
						existingItemsChoiceBox = new ExistingItemsChoiceBox(voogaParameters.get(i).getType())
								.getChoiceBox();
						operationParameter.getChildren().add(new TreeItem<HBox>(new HBox(existingItemsChoiceBox)));

					} else {

						operationNameTreeItem = new OperationNameTreeItem(operationParameters.get(i), voogaParameters.get(i).getName(),
								voogaParameters.get(i).getType());
						listOfOperations.add(operationNameTreeItem);
						operationParameter.getChildren().add(operationNameTreeItem);
					}
				}
				hb.getChildren().add(new Label("]"));
			}

		}
	}

	private boolean checkVoogaType(VoogaType type) {
		for (VoogaType voogaType : voogaTypesForExistingItems) {
			if (type == voogaType)
				return true;
		}
		return false;

	}

	private TextField createDoubleTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
		// Double: "), tf));
		// tf.setOnKeyReleased(e -> {
		// checkDoubleInput(tf);
		// checkEmptyInput(tf, parameterAction, paramTV,
		// parameterAction.getChildren().indexOf(tfTreeItem));
		// });

		return tf;
	}

	private TextField createStringTextField(TreeItem<HBox> treeItem) {
		TextField tf = new TextField();
		// TreeItem<HBox> tfTreeItem = new TreeItem<HBox>(new HBox(new Label("Insert
		// String: "), tf));
		tf.setOnKeyReleased(e -> { // checkEmptyInput(tf, parameterAction, paramTV,
			// parameterAction.getChildren().indexOf(tfTreeItem));
		});

		return tf;
	}

	private TextField createBooleanTextField(TreeItem<HBox> operationParameter) {
		TextField tf = new TextField();
		return tf;
	}

	private Double getDoubleInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				return Double.parseDouble(tf.getText());
			else
				return null;
		} catch (NumberFormatException e) {
			 showError(INVALID_INPUT_MESSAGE, DOUBLE_INPUT_MESSAGE);
			tf.clear();
			return null;
		}
	}

	private Boolean getBooleanInput(TextField tf) {

		if (tf.getText().toLowerCase().equals("true") || tf.getText().toLowerCase().equals("false"))
			return Boolean.parseBoolean(tf.getText().toLowerCase());
		else {
			showError(INVALID_INPUT_MESSAGE, BOOLEAN_INPUT_MESSAGE);
			// tf.clear();
			return null;
		}
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
