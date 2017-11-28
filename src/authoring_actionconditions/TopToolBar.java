package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import ActionConditionClasses.ChoiceBoxVBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

public class TopToolBar extends ToolBar implements TopToolBarI {
	
	private ResourceBundle tabResources;
	private Button addButton;
	private ChoiceBoxVBox<String> selectorVBox;
	private Button removeButton;
	private RemoveChoiceBoxVBox removeRowVBox;
	
	public TopToolBar(ResourceBundle resourceBundle,String addButtonTitle,String optionsTitle,String selectorLabel,String remove) {
		super();
		tabResources = resourceBundle;
		addButton = new Button(tabResources.getString(addButtonTitle));
		List<String> additionOptions = ActionConditionTabUtil.convertToList(tabResources.getString(optionsTitle));
		selectorVBox = new ChoiceBoxVBox<String>(tabResources.getString(selectorLabel), additionOptions);
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		removeButton = new Button(tabResources.getString(remove));
		removeRowVBox = new RemoveChoiceBoxVBox(tabResources.getString("RemoverLabel"),new LinkedList<Integer>());
		getItems().addAll(addButton,selectorVBox,separator,removeButton,removeRowVBox);
	}

	@Override
	public Integer getRemoveValue() {
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
