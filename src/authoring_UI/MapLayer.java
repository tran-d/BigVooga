package authoring_UI;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class MapLayer extends GridPane {

	private int myLayerNumber;
	private int myRows;
	private int myColumns;
	private SpriteGridHandler mySGH;
	private Color defaultColor;
	private String myName;
	static final int CELL_SIZE = 50;

	MapLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super();
		defaultColor = c;
		myRows = rows;
		myColumns = columns;
		myLayerNumber = layerNum;
		mySGH = SGH;
		setup();

	}
	
	public String getName(){
		return myName;
	}
	
	protected void setName(String name)
	{
		myName = name;
	}
	public int getLayerNumber() {
		return myLayerNumber;
	}

	protected void setDefaultColor(Color c) {
		defaultColor = c;
	}

	protected Color getDefaultColor() {
		return defaultColor;
	}
	
	public void removeSpritesAtPositions(ArrayList<Integer[]>locs){
		locs.forEach((pos)->{
			this.getChildAtPosition(pos[0], pos[1]).removeChild();
		});
		
	}
	
	protected AuthoringMapStackPane getChildAtPosition(int row, int col){
		AuthoringMapStackPane result = null;
		ObservableList<Node> childrens = this.getChildren();

	    for (Node node : childrens) {
	        if(this.getRowIndex(node) == row && this.getColumnIndex(node) == col) {
	            result = (AuthoringMapStackPane) node;
	            break;
	        }
	    }
	    return result;
	}
	
	public int getCellSize(){
		return CELL_SIZE;
	}
	
	protected boolean hasChildAtPosition(int row, int column){
		AuthoringMapStackPane child = getChildAtPosition(row, column);
//		if (!(child instanceof AuthoringMapStackPane)){
//	    	return true;
//	    } else {
//	    	AuthoringMapStackPane childStackPane = (AuthoringMapStackPane) child;
	    	return child.hasChild();
//	    }
	}

	protected void setup() {
		for (int i = 0; i < myColumns; i++) {
			for (int j = 0; j < myRows; j++) {
				AuthoringMapStackPane sp = new AuthoringMapStackPane(this);
				
				
				sp.setMinWidth(CELL_SIZE);
				sp.setMaxWidth(CELL_SIZE);
				sp.setPrefWidth(CELL_SIZE);
				sp.setMinHeight(CELL_SIZE);
				sp.setPrefHeight(CELL_SIZE);
				sp.setMaxHeight(CELL_SIZE);
				
				// "-fx-background-color: rgba(0, 100, 100, 0.5);
				sp.setBackground(
						new Background(new BackgroundFill(getDefaultColor(), CornerRadii.EMPTY, Insets.EMPTY)));
				// sp.setStyle();
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
//				GridPane.setColumnSpan(sp, 1);
//				GridPane.setRowSpan(sp, 1);
				GridPane.setHgrow(sp, Priority.NEVER);
				GridPane.setVgrow(sp, Priority.NEVER);
				this.add(sp, i, j);
				sp.setColSpan(1);
				sp.setRowSpan(1);
//				System.out.println(this.getRowSpan(sp));
				mySGH.addDropHandling(sp);
				mySGH.addGridMouseClick(sp);
				mySGH.addGridMouseDrag(sp);
			}
		}
	}

}
