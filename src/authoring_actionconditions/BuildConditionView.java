package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuildConditionView {
	private static final double WIDTH = 800;
	private static final double HEIGHT = 500;

	private static final double COLLAPSED_ROW_HEIGHT = 50;

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

		stage.setOnCloseRequest(e -> transportActionRow());

		root.getChildren().add(this.conditionRow);
	}

	private void transportActionRow() {

		conditionRow.getRootTreeItem().setExpanded(false);
		conditionRow.changeRowTVSize();

		System.out.println(conditionRow.getPrefHeight());

		if (ACVBox.getChildren().size() >= conditionRow.getRowID())
			ACVBox.getChildren().remove(conditionRow.getRowID() - 1);
		ACVBox.getChildren().add(conditionRow.getRowID() - 1, conditionRow);

		stage.close();

		// test
		conditionRow.getCondition();

	}

	public void createParameterChoiceBox() {

	}
}
