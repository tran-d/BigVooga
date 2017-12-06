package ActionConditionClasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import authoring.AbstractSpriteObject;
import authoring.SpriteObject;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ActionVBox;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ConditionVBox;
import authoring_actionconditions.TopToolBar;
import javafx.collections.ObservableList;

public class ApplyButtonController implements ApplyButtonControllerI {
	
	private ConditionTab<ConditionRow> conditionTab;
	private ActionTab<ActionRow> actionTab;
	private AbstractSpriteObject spriteObject;
	
	private static final int LABEL_INDEX = 0;
	private static final int SELECTOR_LABEL_INDEX = 1;
	private static final int SELECTOR_VALUE_INDEX = 2;
	
	public ApplyButtonController(ConditionTab<ConditionRow> conditionTab,ActionTab<ActionRow> actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
	}

	@Override
	public void updateActionConditionTabs(SpriteObject SO) {
		spriteObject = SO;
		HashMap<List<String>,List<Integer>> conditions = spriteObject.getConditionRows();
		List<List<String>> actions = spriteObject.getActionRows();
		ObservableList<Integer> allConditions = spriteObject.getAllConditions();
		ObservableList<Integer> allActions = spriteObject.getAllActions();
		TopToolBar topToolBarConditions = new TopToolBar(ResourceBundleUtil.getTabTitle("ConditionsTabTitle"),allConditions);
		TopToolBar topToolBarActions = new TopToolBar(ResourceBundleUtil.getTabTitle("ActionsTabTitle"),allActions);
		int rowCond = 0;
		List<ConditionRow> conditionRows = new LinkedList<ConditionRow>();
		ConditionVBox<ConditionRow> conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSelectorLabel(),conditionRows);
		for(List<String> labels : conditions.keySet()) {
			ConditionRow conditionRow = new ConditionRow(rowCond,labels.get(LABEL_INDEX),labels.get(SELECTOR_LABEL_INDEX),
					labels.get(SELECTOR_VALUE_INDEX),allConditions,conditions.get(labels),conditionVBox);
			conditionRows.add(conditionRow);
			rowCond++;
		}
		List<ActionRow> actionRows = new LinkedList<ActionRow>();
		ActionVBox<ActionRow> actionVBox = new ActionVBox<ActionRow>(actionTab.getSelectorLabel(),actionRows);
		int rowAct = 0;
		for(List<String> labels : actions) {
			ActionRow actionRow = new ActionRow(rowAct,labels.get(LABEL_INDEX),labels.get(SELECTOR_LABEL_INDEX),
					labels.get(SELECTOR_VALUE_INDEX),actionVBox);
			actionRows.add(actionRow);
			rowAct++;
		}
		System.out.println("Controller should be updating too");
		conditionTab = new ConditionTab<ConditionRow>(ResourceBundleUtil.getTabTitle("ConditionsTabTitle"),conditionVBox,topToolBarConditions);
		actionTab = new ActionTab<ActionRow>(ResourceBundleUtil.getTabTitle("ActionsTabTitle"),actionVBox,topToolBarActions);
	}

	@Override
	public void updateSpriteObject() {
		System.out.println("Spriteobject should be updating");
		spriteObject.setAllConditions(conditionTab.getTopToolBar().getRemoveRowVBoxOptions());
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
			actionLabels.addAll(Arrays.asList(((ActionRow) row).getLabel().getText(),((ActionRow) row).getImplementationSelectorLabel().getText(),
					((ActionRow) row).getImplementationSelectorVBoxValue()));
			actions.add(actionLabels);
		});
		spriteObject.setCondidtionRows(conditions);
		spriteObject.setActionRows(actions);
	}

}
