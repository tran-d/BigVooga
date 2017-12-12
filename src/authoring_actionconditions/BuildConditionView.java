package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BuildConditionView {
	private static final double WIDTH = 750;
	private static final double HEIGHT = 500;

	private Stage stage;
	private Scene scene;
	private Group root;
	ActionConditionVBox<?> ACVBox;
	private ConditionRow conditionRow;

	public BuildConditionView(ActionConditionVBox<?> ACVBox, ConditionRow conditionRow) {
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		this.conditionRow = conditionRow;
		this.ACVBox = ACVBox;

		stage.setOnCloseRequest(event -> transportActionRow(event));

		root.getChildren().add(this.conditionRow);
	}

	private void transportActionRow(WindowEvent event) {

		try {
			conditionRow.getCondition();
			conditionRow.reduceTreeView();

			if (ACVBox.getChildren().size() >= conditionRow.getRowID())
				ACVBox.getChildren().remove(conditionRow.getRowID() - 1);
			ACVBox.getChildren().add(conditionRow.getRowID() - 1, conditionRow);

			stage.close();

		} catch (Exception e) {
			ConditionTreeView.showError(e.getMessage());
			event.consume();

		}

		// KEEP THIS CODE
		// if (conditionRow.getCondition() != null) {
		//
		// conditionRow.getRootTreeItem().setExpanded(false);
		// conditionRow.changeRowTVSize();
		//
		// if (ACVBox.getChildren().size() >= conditionRow.getRowID())
		// ACVBox.getChildren().remove(conditionRow.getRowID() - 1);
		// ACVBox.getChildren().add(conditionRow.getRowID() - 1, conditionRow);
		//
		// stage.close();
		//
		// } else {
		// event.consume();
		// }

	}

}
