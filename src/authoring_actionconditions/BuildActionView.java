package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuildActionView {
	private static final double WIDTH = 800;
	private static final double HEIGHT = 700;
	
	private static final double ROW_WIDTH = 700;

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
		this.ACRow.setPrefWidth(ROW_WIDTH);
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

