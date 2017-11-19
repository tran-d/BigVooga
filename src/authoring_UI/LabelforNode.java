package authoring_UI;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabelforNode {
	
	protected static VBox addVBoxwithLabel(String string,Node node) {
		VBox vBox = new VBox();
		Label label = new Label(string);
		vBox.getChildren().addAll(label,node);
		return vBox;
	}
}
