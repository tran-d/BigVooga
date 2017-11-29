package authoring_UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OverviewManager implements Observer {

	private VBox temp;
	private HBox mapNode;
	private HBox spriteNode;
	private HBox inventNode;
	private ArrayList<HBox> myNodeList;
	private ArrayList<String> myElementList;

	protected OverviewManager() {
		temp = new VBox();
		mapNode = new HBox();
		spriteNode = new HBox();
		inventNode = new HBox();
		
		myElementList = new ArrayList<String>();

		String s1 = "map node";
		String s2 = "sprite node";
		String s3 = "invent node";
		
		mapNode.getChildren().add(new Text(s1));
		spriteNode.getChildren().add(new Text(s2));
		inventNode.getChildren().add(new Text(s3));

		myNodeList = new ArrayList<HBox>();
		myNodeList.add(mapNode);
		myNodeList.add(spriteNode);
		myNodeList.add(inventNode);
		
		myElementList.add("map node");
		myElementList.add("sprite node");
		myElementList.add("invent node");
		

		temp.getChildren().add(mapNode);

	}

	public void makeOverviewButtons(GridPane grid) {
		for (int i = 0; i < myNodeList.size(); i++) {
			OverviewButton ob = new OverviewButton(myNodeList.get(i), myElementList.get(i), this);
			grid.add(ob, 0, i);
		}
	}

	public VBox getNode() {
		return temp;
	}

	public void changeDisplayNode(HBox newNode) {
		temp.getChildren().remove(0);
		temp.getChildren().add(newNode);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
