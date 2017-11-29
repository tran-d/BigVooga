package authoring_UI;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Class where users can see all saved maps, sprite objects, and inventory
 * objects
 * 
 * @author taekwhunchung
 *
 */

public class OverviewWindow {

	private Stage myStage;
	private Scene myScene;
	private GridPane myGrid;
	private CheckBox myCB;
	private ArrayList<String> mySelection;
	private OverviewManager myOM;

	public static final double SCENE_WIDTH = 500;
	public static final double SCENE_HEIGHT = 700;

	protected OverviewWindow() {
		myStage = new Stage();
		myGrid = new GridPane();

		mySelection = new ArrayList<String>();
		mySelection.add("maps");
		mySelection.add("sprite objects");
		mySelection.add("inventory objects");

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(50);

		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(33);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(33);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(34);

		myGrid.getColumnConstraints().addAll(col1, col2);
		myGrid.getRowConstraints().addAll(row1, row2, row3);

		// myGrid.setGridLinesVisible(true);
		myOM = new OverviewManager();

		myOM.makeOverviewButtons(myGrid);

		myGrid.add(myOM.getNode(), 1, 1);

		myScene = new Scene(myGrid, SCENE_WIDTH, SCENE_HEIGHT);
		myStage.setScene(myScene);

	}

	private void makeButtons() {
		for (int i = 0; i < mySelection.size(); i++) {
			myGrid.add(newButton(mySelection.get(i)), 0, i);
		}
	}

	private Node newButton(String s) {
		Button button = new Button(s);
		button.setOnAction(e -> {
			
		});
		return button;
	}

	private void makeCheckBoxes() {
		for (int i = 0; i < mySelection.size(); i++) {
			myGrid.add(newCheckBox(mySelection.get(i)), 0, i);
		}
	}

	private Node newCheckBox(String s) {
		CheckBox cb = new CheckBox(s);
		return cb;
	}

	public Stage getStage() {
		return myStage;
	}

}
