package ActionConditionClasses;

import java.util.List;

public interface ChoiceBoxVBoxI<T> {
	
	public void changeLabel(String newLabel);
	public Object getCurrentValue();
	public void setNewOptions(List<T> newOptions);
	public void setValue(T newValue);
	public int getOptionsSize();

}
