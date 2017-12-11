package authoring_actionconditions;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BuildActionView {
	private static final double WIDTH = 700;
	private static final double HEIGHT = 400;

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

		if (ACRow.getAction() != null) {

			ACRow.getRootTreeItem().setExpanded(false);
			ACRow.changeRowTVSize();

			System.out.println(ACRow.getPrefHeight());

			if (ACVBox.getChildren().size() >= ACRow.getRowID())
				ACVBox.getChildren().remove(ACRow.getRowID() - 1);
			ACVBox.getChildren().add(ACRow.getRowID() - 1, ACRow);

			stage.close();

		} else {
			event.consume();
		}
	}

	public void createParameterChoiceBox() {

	}
}
