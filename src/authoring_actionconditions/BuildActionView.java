package authoring_actionconditions;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BuildActionView {
	private double WIDTH = 500;
	private double HEIGHT = 500;

	private Stage stage;
	private Scene scene;
	private Group root;
	ActionConditionVBox ACVBox;
	private ActionConditionRow ACRow;

	public BuildActionView(ActionConditionVBox ACVBox, ActionConditionRow ACRow) {
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		this.ACRow = ACRow;
		this.ACVBox = ACVBox;

		stage.setOnCloseRequest(e -> transportActionRow());

		root.getChildren().add(this.ACRow);
	}

	private void transportActionRow() {
		if (ACVBox.getChildren().size() >= ACRow.getRowID())
			ACVBox.getChildren().remove(ACRow.getRowID() - 1);
		ACVBox.getChildren().add(ACRow.getRowID() - 1, ACRow);
		
		stage.close();
	}

	public void createParameterChoiceBox() {

	}
}
