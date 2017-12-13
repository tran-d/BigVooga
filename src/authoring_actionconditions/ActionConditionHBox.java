package authoring_actionconditions;

import java.util.LinkedList;
import java.util.ResourceBundle;
import ActionConditionClasses.ResourceBundleUtil;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

public class ActionConditionHBox extends HBox implements ActionConditionHBoxI {

	private ResourceBundle tabResources;
	private Button addButton;
	private Button removeButton;
	private RemoveChoiceBoxVBox removeRowVBox;

	public ActionConditionHBox(String tabType) {
		super();
		
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.setPadding(new Insets(5, 0, 5, 5));
		this.setSpacing(5);
		this.setPrefWidth(530);
				
//		editButton = new Button(tabResources.getString(edit));
//		editRowVBox = new EditChoiceBoxVBox(tabResources.getString("EditLabel"),
//				FXCollections.observableList(new LinkedList<Integer>()));

	
		tabResources = ResourceBundleUtil.getResourceBundle(tabType);
		addButton = new Button(tabResources.getString("AddButtonLabel"));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		removeButton = new Button(tabResources.getString("RemoveButtonLabel"));
		removeRowVBox = new RemoveChoiceBoxVBox(tabResources.getString("RemoverLabel"),
				FXCollections.observableList(new LinkedList<Integer>()));
		getChildren().addAll(addButton, separator, removeRowVBox, removeButton);
	}

	public ActionConditionHBox(String tabType, ObservableList<Integer> actions) {
		this(tabType);
		removeRowVBox.setNewOptions(actions);
	}

	@Override
	public ObservableList<Integer> getRemoveRowVBoxOptions() {
		return removeRowVBox.getOptions();
	}

	protected void addRemoveRowVBoxListener(ListChangeListener<Integer> listChangeListener) {
		removeRowVBox.addListChangeListener(listChangeListener);
	}

	@Override
	public Integer getRemoveValue() {
		return (Integer) removeRowVBox.getCurrentValue();
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
