package ActionConditionClasses;

import java.util.List;

public interface ChoiceBoxVBoxI<T> {
	
	public void setNewOptions(List<T> newOptions);
	public void setValue(T newValue);
	public void addOption(T newOption);
	public int getOptionsSize();

}
