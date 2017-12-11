package ActionConditionClasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ActionVBox;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ConditionVBox;
import engine.Action;
import engine.Condition;
import authoring_actionconditions.ActionConditionHBox;
import javafx.collections.ObservableList;

public class ApplyButtonController {

	private static final int LABEL_INDEX = 0;
	private static final int SELECTOR_LABEL_INDEX = 1;
	private static final int SELECTOR_VALUE_INDEX = 2;

	public void updateActionConditionTabs(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		HashMap<Condition, List<Integer>> conditions = spriteObject.getConditionRows();
		List<Action> actions = spriteObject.getActionRows();
		ObservableList<Integer> allConditions = spriteObject.getAllConditions();
		ObservableList<Integer> allActions = spriteObject.getAllActions();
		ActionConditionHBox topToolBarConditions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ConditionsTabTitle"), allConditions);
		ActionConditionHBox topToolBarActions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ActionsTabTitle"), allActions);
		int rowCond = 1;
		List<ConditionRow> conditionRows = new LinkedList<ConditionRow>();
		ConditionVBox<ConditionRow> conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSelectorLabel());
		for (Condition condition : conditions.keySet()) {
			ConditionRow conditionRow = new ConditionRow(rowCond, allConditions,conditionVBox);
			conditionRows.add(conditionRow);
			rowCond++;
		}
		conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSelectorLabel(), conditionRows);
		List<ActionRow> actionRows = new LinkedList<ActionRow>();
		ActionVBox<ActionRow> actionVBox = new ActionVBox<ActionRow>(actionTab.getSelectorLabel());
		int rowAct = 1;
		for (Action action : actions) {
			// System.out.println("rowAct " + rowAct);
			// System.out.println("Label " + labels);
			ActionRow actionRow = new ActionRow(rowAct, actionVBox);
			actionRows.add(actionRow);
			rowAct++;
		}
		actionVBox = new ActionVBox<ActionRow>(actionTab.getSelectorLabel(), actionRows);
		conditionTab.setTopToolBar(topToolBarConditions);
		conditionTab.setNoReturnActionConditionVBox(conditionVBox);
		actionTab.setTopToolBar(topToolBarActions);
		actionTab.setNoReturnActionConditionVBox(actionVBox);
	}

	public void updateSpriteObject(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		spriteObject.setAllConditions(conditionTab.getTopToolBar().getRemoveRowVBoxOptions());
		spriteObject.setAllActions(actionTab.getTopToolBar().getRemoveRowVBoxOptions());
		HashMap<Condition, List<Integer>> conditions = new HashMap<Condition, List<Integer>>();
		conditionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> conditionLabels = new LinkedList<String>();
			conditionLabels.addAll(Arrays.asList(row.getLabel().getText(),
					row.getImplementationSelectorLabel().getText(), row.getImplementationSelectorVBoxValue()));
			conditions.put(conditionLabels, (List<Integer>) row.getSelectedActions());
		});
		List<Action> actions = new LinkedList<Action>();
		actionTab.getActionConditionVBox().getRows().forEach(row -> {
			List<String> actionLabels = new LinkedList<String>();
			actionLabels.addAll(Arrays.asList(((ActionRow) row).getLabel().getText(),
					((ActionRow) row).getImplementationSelectorLabel().getText(),
					((ActionRow) row).getImplementationSelectorVBoxValue()));
			actions.add(actionLabels);
		});
		spriteObject.setCondidtionRows(conditions);
		spriteObject.setActionRows(actions);
	}

}
