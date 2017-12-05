package authoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import authoring_actionconditions.ActionConditionTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ApplyButtonController implements ApplyButtonControllerI {
	
	private SpriteObject spriteObject;
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ApplyButtonController(SpriteObject spriteObject,ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.spriteObject = spriteObject;
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
	}

	@Override
	public void updateActionConditionTabs() {
		HashMap<List<String>,List<Integer>> conditions = spriteObject.getConditionRows();
		List<List<String>> actions = spriteObject.getActionRows();
		ObservableList<Integer> allActions = spriteObject.getAllActions();
	}

	@Override
	public void updateSpriteObject() {
		spriteObject.setAllActions(actionTab.getTopToolBar().getRemoveRowVBoxOptions());
		HashMap<List<String>,List<Integer>> conditions = new HashMap<List<String>,List<Integer>>();
		conditionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> conditionLabels = new LinkedList<String>();
			conditionLabels.addAll(Arrays.asList(row.getLabel().getText(),row.getImplementationSelectorLabel().getText()));
			conditions.put(conditionLabels, (List<Integer>) row.getSelectedActions());
		});
		List<List<String>> actions = new LinkedList<List<String>>();
		actionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> rowLabels = new LinkedList<String>();
			rowLabels.addAll(Arrays.asList(row.getLabel().getText(),row.getImplementationSelectorLabel().getText()));
			actions.add(rowLabels);
		});
		spriteObject.setCondidtionRows(conditions);
		spriteObject.setActionRows(actions);
	}

}
