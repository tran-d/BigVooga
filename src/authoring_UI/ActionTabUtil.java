package authoring_UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class ActionTabUtil {
	
	private static final String SPLITTER = ",";
	
	protected static VBox addVBoxwithLabel(String string,Node node) {
		VBox vBox = new VBox();
		Label label = new Label(string);
		vBox.getChildren().addAll(label,node);
		return vBox;
	}
	
	protected static Separator makeVerticalSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	protected static ObservableList<String> convertToObservableList(String options) {
		String[] optionsSplit = options.split(SPLITTER);
		ObservableList<String> listOptions = FXCollections.observableArrayList();
		for(String option : optionsSplit) {
			listOptions.add(option);
		}
		return listOptions;
	}
}
