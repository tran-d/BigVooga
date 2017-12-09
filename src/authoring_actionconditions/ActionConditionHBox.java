package authoring_actionconditions;

import java.util.LinkedList;
import java.util.ResourceBundle;

import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public class ActionConditionHBox extends HBox implements TopToolBarI {

	private ResourceBundle tabResources;
	private Button addButton;
	private ChoiceBoxVBox<String> selectorVBox;
	private Button removeButton;
	private RemoveChoiceBoxVBox removeRowVBox;
	private EditChoiceBoxVBox editRowVBox;
	private Button editButton;

	public ActionConditionHBox(ResourceBundle resourceBundle, String addButtonTitle, String optionsTitle, String selectorLabel,
			String edit, String remove) {
		super();
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(5, 0, 5, 5));
		this.setSpacing(5);
		this.setPrefWidth(560);
		tabResources = resourceBundle;
		addButton = new Button(tabResources.getString(addButtonTitle));
		ObservableList<String> additionOptions = ActionConditionTabUtil
				.convertToObservableList(tabResources.getString(optionsTitle));
		
		selectorVBox = new ChoiceBoxVBox<String>(tabResources.getString(selectorLabel), additionOptions);
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		
		removeButton = new Button(tabResources.getString(remove));
		removeRowVBox = new RemoveChoiceBoxVBox(tabResources.getString("RemoverLabel"),
				FXCollections.observableList(new LinkedList<Integer>()));
		
//		editButton = new Button(tabResources.getString(edit));
//		editRowVBox = new EditChoiceBoxVBox(tabResources.getString("EditLabel"),
//				FXCollections.observableList(new LinkedList<Integer>()));
		
		this.getChildren().addAll(addButton, selectorVBox, separator, removeButton, removeRowVBox);
	}

	protected ObservableList<Integer> getRemoveRowVBoxOptions() {
		return removeRowVBox.getOptions();
	}

	protected void addRemoveRowVBoxListener(ListChangeListener<Integer> listChangeListener) {
		removeRowVBox.addListChangeListener(listChangeListener);
	}

	@Override
	public Integer getRemoveValue() {
		System.out.println("current value " + removeRowVBox.getCurrentValue());
		return (Integer) removeRowVBox.getCurrentValue();
	}

	@Override
	public String getOptionsValue() {
		return (String) selectorVBox.getCurrentValue();
	}

	@Override
	public void addButtonListener(EventHandler<ActionEvent> e) {
		addButton.setOnAction(e);
	}

	@Override
	public void addRemoveListener(EventHandler<ActionEvent> e) {
		removeButton.setOnAction(e);
	}

	@Override
	public void addRemoveOption() {
		removeRowVBox.addRow();
	}

	@Override
	public void removeRemoveOption(int row) {
		removeRowVBox.removeRow();
	}

}
