package authoring_actionconditions;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class TopToolBar extends ToolBar implements TopToolBarI {
	
	private ResourceBundle tabResources;
	private Button addButton;
	private ChoiceBox<String> options;
	private VBox selectorVBox;
	private Button removeButton;
	private RemoveChoiceBox removeRow;
	private VBox removeRowVBox;
	
	public TopToolBar(ResourceBundle resourceBundle,String addButtonTitle,String optionsTitle,String selectorLabel,String remove) {
		super();
		tabResources = resourceBundle;
		addButton = new Button(tabResources.getString(addButtonTitle));
		options = new ChoiceBox<String>(ActionConditionTabUtil.convertToObservableList(tabResources.getString(optionsTitle)));
		selectorVBox = ActionConditionTabUtil.addVBoxwithLabel(tabResources.getString(selectorLabel), options);
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		removeButton = new Button(tabResources.getString(remove));
		removeRow = new RemoveChoiceBox();
		removeRowVBox = ActionConditionTabUtil.addVBoxwithLabel(tabResources.getString("RemoverLabel"),removeRow);
		getItems().addAll(addButton,selectorVBox,separator,removeButton,removeRowVBox);
	}

	@Override
	public Integer getRemoveValue() {
		return removeRow.getValue();
	}

	@Override
	public String getOptionsValue() {
		return options.getValue();
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
		removeRow.addRow();
	}

	@Override
	public void removeRemoveOption(int row) {
		removeRow.removeRow(row);
	}
	
}
