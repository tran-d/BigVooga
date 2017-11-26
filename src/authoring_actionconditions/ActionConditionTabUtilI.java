package authoring_actionconditions;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public interface ActionConditionTabUtilI {
	
	public VBox addVBoxwithLabel(String string,Node node);
	public Separator makeVerticalSeparator();
	public ObservableList<String> convertToObservableList(String options);
}
