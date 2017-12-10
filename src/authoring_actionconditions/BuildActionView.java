package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuildActionView {
	private static final double WIDTH = 800;
	private static final double HEIGHT = 500;

	private static final double COLLAPSED_ROW_HEIGHT = 50;

	private Stage stage;
	private Scene scene;
	private Group root;
	ActionConditionVBox ACVBox;
	private ActionRow ACRow;

	public BuildActionView(ActionConditionVBox ACVBox, ActionRow ACRow) {
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

		ACRow.getRootTreeItem().setExpanded(false);
		ACRow.changeRowTVSize();

		System.out.println(ACRow.getPrefHeight());
		
		if (ACVBox.getChildren().size() >= ACRow.getRowID())
			ACVBox.getChildren().remove(ACRow.getRowID() - 1);
		ACVBox.getChildren().add(ACRow.getRowID() - 1, ACRow);

		stage.close();

		// test
		ACRow.getAction();
		
		
	}

	public void createParameterChoiceBox() {

	}
}
