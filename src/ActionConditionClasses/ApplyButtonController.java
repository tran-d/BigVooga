package ActionConditionClasses;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import authoring.Sprite.AbstractSpriteObject;
import authoring_actionconditions.ActionConditionHBox;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ActionTreeView;
import authoring_actionconditions.ActionVBox;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ConditionTreeView;
import authoring_actionconditions.ConditionVBox;
import engine.Action;
import engine.Condition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ApplyButtonController {
	
	public static AbstractSpriteObject selectedSpriteObject;

	public void updateActionConditionTabs(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject selectedSpriteObject) {
		

		ApplyButtonController.selectedSpriteObject = selectedSpriteObject;
		//gets everything from selected sprite to load onto tabs
		HashMap<ConditionTreeView, List<Integer>> conditions = selectedSpriteObject.getConditionTreeviews();
		List<ActionTreeView> actions = selectedSpriteObject.getActionTreeViews();
		ObservableList<Integer> allConditions = selectedSpriteObject.getAllConditions();
		ObservableList<Integer> allActions = selectedSpriteObject.getAllActions();
		List<String> selectedConditionOperations = selectedSpriteObject.getSelectedConditionOperations();
		List<List<String>> selectedActionOperations = selectedSpriteObject.getSelectedActionOperations();
		Map<Condition, List<Integer>> spriteConditions = selectedSpriteObject.getConditionRows();
		List<Action> spriteActions = selectedSpriteObject.getActionRows();
		//creates top toolbars
		ActionConditionHBox topToolBarConditions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ConditionsTabTitle"),
				createObservableIntegerList(selectedConditionOperations.size()));
		ActionConditionHBox topToolBarActions = new ActionConditionHBox(
				ResourceBundleUtil.getTabTitle("ActionsTabTitle"),
				createObservableIntegerList(selectedActionOperations.size()));
		int rowCond = 1;
		// creating or recreating condition rows
		List<ConditionRow> conditionRows = new LinkedList<ConditionRow>();
		ConditionVBox<ConditionRow> conditionVBox = new ConditionVBox<ConditionRow>(conditionTab.getSupplier());
		// if loading from xml
		System.out.println("condition treeview list is null");
		Iterator<Condition> it = spriteConditions.keySet().iterator();
		ObservableList<Integer> actionOperations = createObservableIntegerList(spriteActions.size());
		while ((it.hasNext() && (rowCond <= selectedSpriteObject.getConditionDummyTreeViewSize()))) {
			Condition selectedCondition = it.next();
			ConditionRow conditionRow = new ConditionRow(rowCond, actionOperations, spriteConditions.get(selectedCondition),
					conditionVBox, selectedConditionOperations.get(rowCond - 1), selectedCondition,conditionTab.getSupplier());
			conditionRows.add(conditionRow);
			rowCond++;
		} 
		for (ConditionTreeView conditionTreeView : conditions.keySet()) {
			ConditionRow conditionRow = new ConditionRow(rowCond,
					createObservableIntegerList(selectedActionOperations.size()), conditions.get(conditionTreeView),
					conditionVBox, conditionTreeView,conditionTab.getSupplier());
			conditionRows.add(conditionRow);
			rowCond++;
		}
		//creating conditionvbox
		conditionVBox = new ConditionVBox<ConditionRow>(conditionRows,conditionTab.getSupplier());
		List<ActionRow> actionRows = new LinkedList<ActionRow>();
		ActionVBox<ActionRow> actionVBox = new ActionVBox<ActionRow>(actionTab.getSupplier());
		// if loading from xml
		for(int rowAct = 1; rowAct <= selectedSpriteObject.getActionDummyTreeViewSize(); rowAct++) {
			ActionRow actionRow = new ActionRow(rowAct, actionVBox, selectedActionOperations.get(rowAct - 1),
					spriteActions.get(rowAct - 1),actionTab.getSupplier());
			actionRows.add(actionRow);
		};
		int rowAction = selectedSpriteObject.getActionDummyTreeViewSize() + 1;
		for (ActionTreeView actionTreeView : actions) {
			ActionRow actionRow = new ActionRow(rowAction, actionVBox, actionTreeView,actionTab.getSupplier());
			actionRows.add(actionRow);
			rowAction++;
		}
		actionVBox = new ActionVBox<ActionRow>(actionRows,actionTab.getSupplier());
		conditionTab.setTopToolBar(topToolBarConditions);
		conditionTab.setNoReturnActionConditionVBox(conditionVBox);
		actionTab.setTopToolBar(topToolBarActions);
		actionTab.setNoReturnActionConditionVBox(actionVBox);
	}

	public void updateSpriteObject(ConditionTab<ConditionRow> conditionTab, ActionTab<ActionRow> actionTab,
			AbstractSpriteObject spriteObject) {
		try {
			HashMap<ConditionTreeView, List<Integer>> userCreatedConditionsTreeViews = new HashMap<ConditionTreeView, List<Integer>>();
			List<String> selectedConditionOperations = new LinkedList<String>();
			//save dummy condition trees
			for(int i = 0; i < spriteObject.getConditionDummyTreeViewSize(); i++) {
				ConditionRow row = conditionTab.getActionConditionVBox().getRows().get(i);
				selectedConditionOperations.add(row.getTreeView().getSelectedOperation());
			}
			//save real condition trees
			for(int i = spriteObject.getConditionDummyTreeViewSize(); i < conditionTab.getActionConditionVBox().getRows().size(); i++) {
				ConditionRow row = conditionTab.getActionConditionVBox().getRows().get(i);
				userCreatedConditionsTreeViews.put(row.getTreeView(), row.getSelectedActions());
			}
			List<ActionTreeView> userCreatedActionsTreeViews = new LinkedList<ActionTreeView>();
			List<List<String>> selectedActionOperations = new LinkedList<List<String>>();
			for(int i = spriteObject.getActionDummyTreeViewSize(); i < actionTab.getActionConditionVBox().getRows().size(); i++) {
				ActionRow row = actionTab.getActionConditionVBox().getRows().get(i - 1);
				List<String> treeViewParams = new LinkedList<String>();
				treeViewParams.add(row.getTreeView().getCategoryName());
				treeViewParams.add(row.getTreeView().getActionName());
				selectedActionOperations.add(treeViewParams);
			}
			//save dummy action trees
			for(int i = 0; i < spriteObject.getActionDummyTreeViewSize(); i++) {
				ActionRow row = actionTab.getActionConditionVBox().getRows().get(i);
				List<String> treeViewParams = new LinkedList<String>();
				treeViewParams.add(row.getTreeView().getCategoryName());
				treeViewParams.add(row.getTreeView().getActionName());
				selectedActionOperations.add(treeViewParams);
			}
			//save real action trees
			for(int i = spriteObject.getActionDummyTreeViewSize(); i < actionTab.getActionConditionVBox().getRows().size(); i++) {
				ActionRow row = actionTab.getActionConditionVBox().getRows().get(i);
				userCreatedActionsTreeViews.add(row.getTreeView());
			}
			spriteObject.setAllConditions(conditionTab.getTopToolBar().getRemoveRowVBoxOptions());
			spriteObject.setAllActions(actionTab.getTopToolBar().getRemoveRowVBoxOptions());
			spriteObject.setSelectedConditionOperations(selectedConditionOperations);
			spriteObject.setSelectedConditionOperations(selectedConditionOperations);
			spriteObject.setSelectedActionOperations(selectedActionOperations);
			spriteObject.setConditions(userCreatedConditionsTreeViews);
			spriteObject.setActions(userCreatedActionsTreeViews);
		} catch (NullPointerException | NumberFormatException e) {
			conditionTab.displayRowExceptionMessage(e.getMessage());
		}
	}

	private ObservableList<Integer> createObservableIntegerList(int size) {
		ObservableList<Integer> ret = FXCollections.observableArrayList();
		for (int i = 1; i <= size; i++) {
			ret.add(i);
		}
		return ret;
	}
	
	public static AbstractSpriteObject getSelectedSpriteObject() {
		return selectedSpriteObject;
	}

}
