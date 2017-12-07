package ActionConditionClasses;

import authoring.AbstractSpriteObject;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;

public interface ApplyButtonControllerI {
	
	public void updateActionConditionTabs(ConditionTab<ConditionRow> conditionTab,ActionTab<ActionRow> actionTab,AbstractSpriteObject spriteObject);
	public void updateSpriteObject(ConditionTab<ConditionRow> conditionTab,ActionTab<ActionRow> actionTab,AbstractSpriteObject spriteObject);
	public ConditionTab<ConditionRow> getUpdatedConditionTab();
	public ActionTab<ActionRow> getUpdatedActionTab();

}
