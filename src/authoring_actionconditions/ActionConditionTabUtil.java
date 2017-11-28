package authoring_actionconditions;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

public class ActionConditionTabUtil {
	
	private static final String SPLITTER = ",";
	
	protected static Separator makeVerticalSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	protected static ObservableList<String> convertToObservableList(String resourceString) {
		String[] optionsSplit = resourceString.split(SPLITTER);
		return FXCollections.observableList(Arrays.asList(optionsSplit));
	}
}
