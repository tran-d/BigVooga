package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BuildActionView {
	private static final double WIDTH = 750;
	private static final double HEIGHT = 450;

	private Stage stage;
	private Scene scene;
	private Group root;
	ActionConditionVBox<?> ACVBox;
	private ActionRow ACRow;

	public BuildActionView(ActionConditionVBox<?> ACVBox, ActionRow ACRow) {
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		this.ACRow = ACRow;
		this.ACVBox = ACVBox;

		stage.setOnCloseRequest(event -> transportActionRow(event));

		root.getChildren().add(this.ACRow);
	}

	private void transportActionRow(WindowEvent event) {

//		 for testing
		try {
			ACRow.getTreeView().getAction();
			ACRow.reduceTreeView();

			System.out.println(ACRow.getPrefHeight());

			if (ACVBox.getChildren().size() >= ACRow.getRowID())
				ACVBox.getChildren().remove(ACRow.getRowID() - 1);
			ACVBox.getChildren().add(ACRow.getRowID() - 1, ACRow);
			stage.close();
		} catch (NullPointerException | NumberFormatException e) {
			ConditionTreeView.showError(e.getMessage());
			event.consume();
		}
	}

	public void createParameterChoiceBox() {

	}
}
