package authoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ActionConditionClasses.ResourceBundleUtil;
import authoring_actionconditions.ActionConditionRow;
import authoring_actionconditions.ActionConditionTab;
import authoring_actionconditions.TopToolBar;
import javafx.collections.ObservableList;

public class ApplyButtonController implements ApplyButtonControllerI {
	
	private SpriteObject spriteObject;
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	private static final int LABEL_INDEX = 0;
	private static final int SELECTOR_LABEL_INDEX = 1;
	private static final int SELECTOR_VALUE_INDEX = 2;
	
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
		TopToolBar topToolBarConditions = new TopToolBar(ResourceBundleUtil.getTabTitle("ConditionsTabTitle"),allActions);
		TopToolBar topToolBarActions = new TopToolBar(ResourceBundleUtil.getTabTitle("ActionsTabTitle"));
		int rowCond = 0;
		List<ActionConditionRow> conditionRows = new LinkedList<ActionConditionRow>();
		for(List<String> labels : conditions.keySet()) {
			ActionConditionRow conditionRow = new ActionConditionRow(rowCond,labels.get(LABEL_INDEX),labels.get(SELECTOR_LABEL_INDEX),true,
					labels.get(SELECTOR_VALUE_INDEX),allActions,conditions.get(labels));
			conditionRows.add(conditionRow);
			rowCond++;
		}
		List<ActionConditionRow> actionRows = new LinkedList<ActionConditionRow>();
		int rowAct = 0;
		for(List<String> labels : actions) {
			ActionConditionRow actionRow = new ActionConditionRow(rowAct,labels.get(LABEL_INDEX),labels.get(SELECTOR_LABEL_INDEX),true,
					labels.get(SELECTOR_VALUE_INDEX),allActions);
			actionRows.add(actionRow);
			rowAct++;
		}
	}

	@Override
	public void updateSpriteObject() {
		spriteObject.setAllActions(actionTab.getTopToolBar().getRemoveRowVBoxOptions());
		HashMap<List<String>,List<Integer>> conditions = new HashMap<List<String>,List<Integer>>();
		conditionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> conditionLabels = new LinkedList<String>();
			conditionLabels.addAll(Arrays.asList(row.getLabel().getText(),row.getImplementationSelectorLabel().getText(),
					row.getImplementationSelectorVBoxValue()));
			conditions.put(conditionLabels, (List<Integer>) row.getSelectedActions());
		});
		List<List<String>> actions = new LinkedList<List<String>>();
		actionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> actionLabels = new LinkedList<String>();
			actionLabels.addAll(Arrays.asList(row.getLabel().getText(),row.getImplementationSelectorLabel().getText(),
					row.getImplementationSelectorVBoxValue()));
			actions.add(actionLabels);
		});
		spriteObject.setCondidtionRows(conditions);
		spriteObject.setActionRows(actions);
	}

}
