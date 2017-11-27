package authoring_actionconditions;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

public class ActionConditionTabUtil {
	
	private static final String SPLITTER = ",";
	
	protected static Separator makeVerticalSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	protected static List<String> convertToList(String resourceString) {
		String[] optionsSplit = resourceString.split(SPLITTER);
		return Arrays.asList(optionsSplit);
	}
}
