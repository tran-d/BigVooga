package ActionConditionClasses;

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
import authoring_actionconditions.ActionConditionHBox;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

public class ApplyButtonController {

	public void updateActionConditionTabs(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		HashMap<TreeView<HBox>, List<Integer>> conditions = spriteObject.getConditionRows();
		List<TreeView<HBox>> actions = spriteObject.getActionRows();
		ObservableList<Integer> allConditions = spriteObject.getAllConditions();
		ObservableList<Integer> allActions = spriteObject.getAllActions();
		ActionConditionHBox topToolBarConditions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ConditionsTabTitle"), allConditions);
		ActionConditionHBox topToolBarActions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ActionsTabTitle"), allActions);
		int rowCond = 1;
		List<ConditionRow> conditionRows = new LinkedList<ConditionRow>();
		ConditionVBox<ConditionRow> conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSelectorLabel());
		for (TreeView<HBox> conditionTreeView : conditions.keySet()) {
			ConditionRow conditionRow = new ConditionRow(rowCond, allConditions,conditions.get(conditionTreeView), conditionVBox,conditionTreeView);
			conditionRows.add(conditionRow);
			rowCond++;
		}
		conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSelectorLabel(), conditionRows);
		List<ActionRow> actionRows = new LinkedList<ActionRow>();
		ActionVBox<ActionRow> actionVBox = new ActionVBox<ActionRow>(actionTab.getSelectorLabel());
		int rowAct = 1;
		for (TreeView<HBox> actionTreeView : actions) {
			// System.out.println("rowAct " + rowAct);
			// System.out.println("Label " + labels);
			ActionRow actionRow = new ActionRow(rowAct, actionVBox,actionTreeView);
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
		HashMap<TreeView<HBox>, List<Integer>> conditions = new HashMap<TreeView<HBox>, List<Integer>>();
		conditionTab.getActionConditionVBox().getRows().forEach(row -> {
			conditions.put(row.getTreeView(), (List<Integer>) row.getSelectedActions());
		});
		List<TreeView<HBox>> actions = new LinkedList<TreeView<HBox>>();
		actionTab.getActionConditionVBox().getRows().forEach(row -> {
			actions.add(row.getTreeView());
		});
		spriteObject.setCondidtions(conditions);
		spriteObject.setActions(actions);
	}

}
