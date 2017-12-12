package ActionConditionClasses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import authoring.Sprite.AbstractSpriteObject;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ActionTreeView;
import authoring_actionconditions.ActionVBox;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ConditionTreeView;
import authoring_actionconditions.ConditionVBox;
import authoring_actionconditions.ActionConditionHBox;
import javafx.collections.ObservableList;

public class ApplyButtonController {

	public void updateActionConditionTabs(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		HashMap<ConditionTreeView, List<Integer>> conditions = spriteObject.getConditionRows();
		List<ActionTreeView> actions = spriteObject.getActionRows();
		ObservableList<Integer> allConditions = spriteObject.getAllConditions();
		ObservableList<Integer> allActions = spriteObject.getAllActions();
		ActionConditionHBox topToolBarConditions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ConditionsTabTitle"), allConditions);
		ActionConditionHBox topToolBarActions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ActionsTabTitle"), allActions);
		int rowCond = 1;
		List<ConditionRow> conditionRows = new LinkedList<ConditionRow>();
		ConditionVBox<ConditionRow> conditionVBox = new ConditionVBox<ConditionRow>();
		for (ConditionTreeView conditionTreeView : conditions.keySet()) {
			ConditionRow conditionRow = new ConditionRow(rowCond, allActions,conditions.get(conditionTreeView), conditionVBox,conditionTreeView);
			conditionRows.add(conditionRow);
			rowCond++;
		}
		conditionVBox = new ConditionVBox<ConditionRow>(conditionRows);
		List<ActionRow> actionRows = new LinkedList<ActionRow>();
		ActionVBox<ActionRow> actionVBox = new ActionVBox<ActionRow>();
		int rowAct = 1;
		for (ActionTreeView actionTreeView : actions) {
			// System.out.println("rowAct " + rowAct);
			// System.out.println("Label " + labels);
			ActionRow actionRow = new ActionRow(rowAct, actionVBox,actionTreeView);
			actionRows.add(actionRow);
			rowAct++;
		}
		actionVBox = new ActionVBox<ActionRow>(actionRows);
		conditionTab.setTopToolBar(topToolBarConditions);
		conditionTab.setNoReturnActionConditionVBox(conditionVBox);
		actionTab.setTopToolBar(topToolBarActions);
		actionTab.setNoReturnActionConditionVBox(actionVBox);
	}

	public void updateSpriteObject(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		try {
			HashMap<ConditionTreeView, List<Integer>> conditions = new HashMap<ConditionTreeView, List<Integer>>();
			conditionTab.getActionConditionVBox().getRows().forEach(row -> {
				conditions.put(row.getTreeView(), (List<Integer>) row.getSelectedActions());
			});
			List<ActionTreeView> actions = new LinkedList<ActionTreeView>();
			actionTab.getActionConditionVBox().getRows().forEach(row -> {
				actions.add(row.getTreeView());
			});
			spriteObject.setAllConditions(conditionTab.getTopToolBar().getRemoveRowVBoxOptions());
			spriteObject.setAllActions(actionTab.getTopToolBar().getRemoveRowVBoxOptions());
			spriteObject.setConditions(conditions);
			spriteObject.setActions(actions);
		}
		catch(NullPointerException e) {
			conditionTab.displayInvalidSelectedActionsMessage(e.getMessage());
		}
	}

}
