package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OverviewButton extends Button {
	
	private HBox myNode;
	private OverviewManager myOM;
	
	protected OverviewButton(HBox hb, String s, OverviewManager om) {
		this.setText(s);
		myNode = hb;
		myOM = om;
		this.setOnAction(e -> {
			myOM.changeDisplayNode(myNode);
		});
	}
}
